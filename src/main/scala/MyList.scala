//sealed trait MyList[A] {
//  def foldRight[B](zero: B)(f: (A, B) => B): B =
//    this match {
//      case Pair(h, t) => f(h, t.fold(zero)(f))
//      case Empty() => zero
//    }
//
//  def foldLeft[B](zero: B)(f: (B, A) => B): B =
//    this match {
//      case Pair(h, t) => t.foldLeft(f(zero, h))(f)
//      case Empty() => zero
//    }
//
//  def length: Int =
//    // this match {
//    //   case Pair(_, t) => 1 + t.length
//    //   case Empty() => 0
//    // }
//    foldLeft(0)((_, tailL) => 1 + tailL)
//
//  def sum(identity: A, add: (A, A) => A): A =
//    this match {
//      case Pair(h, t) => add(h,  t.sum(identity, add))
//      case Empty() => identity
//    }
//
//  def contains(x: A): Boolean =
//    this match {
//      case Pair(h, t) => (h == x) || t.contains(x)
//      case Empty() => false
//    }
//
//  // Goal: MyList[B]
//  // Available:
//  //   f: A => B
//  //   Pair: (Z, MyList[Z]) => MyList[Z]
//  //   Empty: => MyList[Z]
//  //   h: A
//  //   t: MyList[A]
//  //   tb: MyList[B]
//  //   b: B
//  def map[B](f: A => B): MyList[B] =
//    this match {
//      case Pair(h, t) =>
//        val b = f(h)
//        val tb = t.map(f)
//        Pair(b, tb)
//        // Empty()
//        // t.map(f)
//      case Empty() => Empty()
//    }
//
//  def append(that: MyList[A]): MyList[A] =
//    this match {
//      case Pair(h, t) =>
//        val tail: MyList[A] = t.append(that)
//        Pair(h, tail)
//      case Empty() => that
//    }
//
//  def flatMap[B](f: A => MyList[B]): MyList[B] =
//    this match {
//      case Pair(h, t) =>
//        val tail: MyList[B] = t.flatMap(f)
//        val fh: MyList[B] = f(h)
//        fh.append(tail)
//      case Empty() => Empty()
//    }
//}
//final case class Pair[A](head: A, tail: MyList[A]) extends MyList[A]
//final case class Empty[A]() extends MyList[A]
//
//object MyListExamples extends App {
//  val list1 = Pair(3, Pair(2, Pair(1, Empty())))
//  val list2 = Pair(6, Pair(5, Pair(4, Empty())))
//
//  assert(list1.length == 3)
//  assert(list1.sum(0, (a, b) => a + b) == 6)
//  assert(list1.contains(3))
//  assert(!list1.contains(33))
//
//  println(list1.map(x => x * 2))
//  println(list2.append(list1))
//
//  assert(
//    list1.flatMap(x => Pair(x, Empty())) == list1
//  )
//
//  assert(
//    list1.flatMap(x => Pair(x, Pair(x, Empty()))) ==
//    Pair(3, Pair(3, Pair(2, Pair(2, Pair(1, Pair(1, Empty()))))))
//  )
//  println("Tests done!")
//
//  // val users: List[User]
//  // val lookup: User => List[Journey]
//  // List[Journey]
//  // users.flatMap(lookup)
//}
