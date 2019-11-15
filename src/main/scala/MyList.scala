sealed trait MyList[A] {
  def length: Int =
    this match {
      case Pair(_, t) => 1 + t.length
      case Empty() => 0
    }

  def sum(identity: A, add: (A, A) => A): A =
    this match {
      case Pair(h, t) => add(h,  t.sum)
      case Empty() => identity
    }

  def contains(x: A): Boolean =
    this match {
      case Pair(h, t) => (h == x) || t.contains(x)
      case Empty() => false
    }

  def map[B](f: A => B): MyList[B] =
    ???

  def flatMap[B](f: A => MyList[B]): MyList[B] =
    ???
}
final case class Pair[A](head: A, tail: MyList[A]) extends MyList[A]
final case class Empty[A]() extends MyList[A]

object MyListExamples extends App {
  val list1 = Pair(3, Pair(2, Pair(1, Empty())))

  assert(list1.length == 3)
  assert(list1.sum == 6)
  assert(list1.contains(3))
  assert(!list1.contains(33))
  println("All done!")

  println(list1.map(x => x * 2))
}
