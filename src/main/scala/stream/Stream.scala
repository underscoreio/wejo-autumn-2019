package stream

import cats.{Monad, Monoid}

sealed trait Stream[A] {
  import Stream._

  def map[B](f: A => B): Stream[B] =
    Map(this, f)

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    FlatMap(this, f)

  def parCombineAll(implicit m: Monoid[A]): A = {
    this.foldLeft(m.empty)(m.combine _)
  }

  def combineAll(implicit m: Monoid[A]): A =
    this.foldLeft(m.empty)(m.combine _)

  def foldLeft[B](z: B)(f: (B, A) => B): B = {
    def next[C](source: Stream[C]): Option[C] =
      source match {
        case FromIterator(iterator) => if(iterator.hasNext) Some(iterator.next()) else None
        case Map(source, f) => next(source).map(f)//f(next(source))
        case FlatMap(source, f) => //next(f(next(source)))
          next(source).flatMap(opt => next(f(opt)))
      }

    val optA: Option[A] = next(this)
    optA match {
      case Some(value) =>
        val result: B = f(z, value)
        foldLeft(result)(f)
      case None => z
    }
  }
}
object Stream {
  final case class FromIterator[A](iterator: Iterator[A]) extends Stream[A]
  final case class Map[A,B](source: Stream[A], f: A => B) extends Stream[B]
  final case class FlatMap[A,B](source: Stream[A], f: A => Stream[B]) extends Stream[B]

  def apply[A](as: A*): Stream[A] =
    fromIterator(as.toIterator)

  def fromIterator[A](iterator: Iterator[A]): Stream[A] =
    FromIterator(iterator)

  implicit val streamMonadInstance: Monad[Stream] =
    new Monad[Stream] {
      def pure[A](x: A): Stream[A] =
        Stream(x)

      def flatMap[A, B](fa: Stream[A])(f: A => Stream[B]): Stream[B] =
        fa.flatMap(f)

      def tailRecM[A, B](a: A)(f: A => Stream[Either[A, B]]): Stream[B] =
        f(a).flatMap{ either =>
          either match {
            case Left(value) => tailRecM(value)(f)
            case Right(value) => Stream(value)
          }
        }
    }
}
object StreamExample {
  import cats.implicits._

  val stream = Stream(1,2,3,4,5)
//  stream.foldLeft(0)(_ + _)
  stream.combineAll

  1.pure[Stream]

  1 |+| 2
  List(1,2,3,4,5) |+| List(6,7,8,9,10)
  Option(1) |+| Option(2)
  Map("a" -> 1, "b" -> 2, "c" -> 4) |+| Map("d" -> 5, "a" -> 3)

  def validationRule[F[_]](fa: F[Int])(implicit m: Monad[F]): F[String] =
    fa.flatMap((v: Int) => if(v < 10) "too small".pure[F] else "just right".pure[F])
}
