sealed trait Json
final case class JObject(values: Map[String, Json]) extends Json
final case class JString(value: String) extends Json
final case class JNumber(value: Double) extends Json
final case class JArray(elements: List[Json]) extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}
object JsonWriter {
  implicit val intWriter: JsonWriter[Int] = new JsonWriter[Int] {
    def write(value: Int): Json =
      JNumber(value.toDouble)
  }

  implicit object stringWriter extends JsonWriter[String] {
    def writer(value: String): Json =
      JString(value)
  }
}

object Serialize {
  def serialize[A](value: A)(implicit writer: JsonWriter[A]) =
    writer.write(value)
}

object SerializeExample {
  Serialize.serialize(1)
  Serialize.serialize("Hi!")
  // Serialize.serialize(2.0)
}
