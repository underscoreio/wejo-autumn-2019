sealed trait Json
final case class JObject(values: Map[String, Json]) extends Json
final case class JString(value: String) extends Json
final case class JNumber(value: Double) extends Json
final case class JArray(elements: List[Json]) extends Json
final case class JNull() extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}
object JsonWriter {
  implicit val intWriter: JsonWriter[Int] =
    new JsonWriter[Int] {
      def write(value: Int): Json =
        JNumber(value.toDouble)
    }

  implicit val doubleWriter: JsonWriter[Double] =
    new JsonWriter[Double] {
      def write(value: Double): Json =
        JNumber(value)
    }

  implicit object stringWriter extends JsonWriter[String] {
    def write(value: String): Json =
      JString(value)
  }

  implicit def optionWriter[A](implicit w: JsonWriter[A]): JsonWriter[Option[A]] =
    new JsonWriter[Option[A]] {
      def write(value: Option[A]): Json =
        value match {
          case Some(v) => w.write(v)
          case None    => JNull()
        }
    }
}

object Serialize {
  def serialize[A](value: A)(implicit writer: JsonWriter[A]) =
    writer.write(value)
}

object SerializeExample {
  Serialize.serialize(1)
  Serialize.serialize("Hi!")
  Serialize.serialize(2.0)
  Serialize.serialize(Option(1))
  Serialize.serialize(Option("wejo"))
  Serialize.serialize(Option(42.0))
}
