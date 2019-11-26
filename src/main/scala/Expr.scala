sealed trait Expr {
  import Expr._

  def +(e: Expr): Expr =
    Add(this, e)

  def -(e: Expr): Expr =
    Subtract(this, e)

  def *(e: Expr): Expr =
    Multiply(this, e)

  def /(e: Expr): Expr =
    Divide(this, e)

  def eval: Double =
    this match {
      case Literal(v) => v
      case Add(l, r) => l.eval + r.eval
      case Subtract(l, r) => l.eval - r.eval
      case Multiply(l, r) => l.eval * r.eval
      case Divide(l, r) => l.eval / r.eval
    }

  def prettyPrint: String =
    this match {
      case Literal(v) => v.toString
      case Add(l, r) => s"(${l.prettyPrint} + ${r.prettyPrint})"
      case Subtract(l, r) => s"(${l.prettyPrint} - ${r.prettyPrint})"
      case Multiply(l, r) => s"(${l.prettyPrint} * ${r.prettyPrint})"
      case Divide(l, r) => s"(${l.prettyPrint} / ${r.prettyPrint})"
    }
}
object Expr {
  final case class Literal(v: Double) extends Expr
  final case class Add(l: Expr, r: Expr) extends Expr
  final case class Subtract(l: Expr, r: Expr) extends Expr
  final case class Multiply(l: Expr, r: Expr) extends Expr
  final case class Divide(l: Expr, r: Expr) extends Expr

  def literal(v: Double): Expr =
    Literal(v)

  val example = literal(2) - literal(1)
}
