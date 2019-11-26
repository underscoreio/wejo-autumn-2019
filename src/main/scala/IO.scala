import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

object IOExample extends App {
  import cats.effect.IO
  import cats.implicits._

  val currentThread = IO(Thread.currentThread().getName())

  val threadPool = Executors.newFixedThreadPool(1)
  val ec1 = scala.concurrent.ExecutionContext.global
  val ec2 = scala.concurrent.ExecutionContext.fromExecutor(threadPool)

  def threadNameFromEc(ec: ExecutionContext): IO[String] =
      IO.shift(ec).flatMap(_ => currentThread)

  val threadName1: IO[String] = threadNameFromEc(ec1)
  val threadName2: IO[String] = threadNameFromEc(ec2)

  implicit val cs = IO.contextShift(ec1)
  val names: IO[List[String]] = (threadName1, threadName2).parMapN((n1, n2) => List(n1, n2))
    .map(_ => throw new Exception("Oh noes!!!!"))
    .handleErrorWith((ex: Throwable) =>
      IO(List("There was an error", ex.getMessage()))
    )

  println(currentThread.unsafeRunSync())
  println(names.unsafeRunSync())
  threadPool.shutdown()
}
