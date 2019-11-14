sealed trait IntList {
  def length: Int =
    this match {
      case Pair(_, t) => 1 + t.length
      case Empty() => 0
    }

  def sum: Int =
    this match {
      case Pair(h, t) => h + t.sum
      case Empty() => 0
    }

  def contains(x: Int): Boolean =
    this match {
      case Pair(h, t) => (h == x) || t.contains(x)
      case Empty() => false
    }
}
final case class Pair(head: Int, tail: IntList) extends IntList
final case class Empty() extends IntList

object IntListExamples extends App {
  val list1 = Pair(3, Pair(2, Pair(1, Empty())))

  assert(list1.length == 3)
  assert(list1.sum == 6)
  assert(list1.contains(3))
  assert(!list1.contains(33))
  println("All done!")
}
