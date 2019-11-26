import org.scalacheck._
import org.scalacheck.Prop._

object ExprSpec extends Properties("Expr properties") {
  property("Basic operations are correct") =
    forAll{ (l: Double, r: Double) =>
      ((Expr.literal(l) - Expr.literal(r)).eval =? l - r) &&
        ((Expr.literal(l) + Expr.literal(r)).eval =? l + r) &&
        ((Expr.literal(l) / Expr.literal(r)).eval =? l / r) &&
        ((Expr.literal(l) * Expr.literal(r)).eval =? l * r)
    }
}
