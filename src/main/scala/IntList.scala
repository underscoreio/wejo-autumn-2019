sealed trait IntList {
  def length: Int =
    this match {
      case IntPair(_, t) => 1 + t.length
      case IntEmpty() => 0
    }

  def sum: Int =
    this match {
      case IntPair(h, t) => h + t.sum
      case IntEmpty() => 0
    }

  def contains(x: Int): Boolean =
    this match {
      case IntPair(h, t) => (h == x) || t.contains(x)
      case IntEmpty() => false
    }
}
final case class IntPair(head: Int, tail: IntList) extends IntList
final case class IntEmpty() extends IntList

object IntListExamples extends App {
  val list1 = IntPair(3, IntPair(2, IntPair(1, IntEmpty())))

  assert(list1.length == 3)
  assert(list1.sum == 6)
  assert(list1.contains(3))
  assert(!list1.contains(33))
  println("All done!")
}
