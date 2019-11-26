import cats._
import cats.implicits._

object Validation {
  type Message = List[String]

  final case class Name(value: String)
  final case class Password(value: String)
  final case class Email(value: String)

  final case class User(name: Name, password: Password, email: Email)

  def validate(name: String, password: String, email: String): Either[Message, User] =
    (validateName(name), validatePassword(password), validateEmail(email)).mapN(User.apply _)

  def validateName(name: String): Either[Message, Name] =
    if(name.length > 3) Right(Name(name))
    else Left(List(s"Name $name is too short. It must have 4 or more characters."))

  def validatePassword(password: String): Either[Message, Password] =
    if(password.length > 3) Right(Password(password))
    else Left(List(s"Password $password is too short. It must have 4 or more characters."))

  def validateEmail(email: String): Either[Message, Email] =
    if(email.length > 3) Right(Email(email))
    else Left(List(s"Email $email is too short. It must have 4 or more characters."))
}
