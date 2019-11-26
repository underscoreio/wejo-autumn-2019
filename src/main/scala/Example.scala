import doodle.core._
import doodle.image._
//import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import scala.concurrent.duration._

// To use this example:
//
// 1. run `sbt`
// 2. run the `run` command within `sbt`
object Example {
  val image =
    Image
      .circle(10)
      .fillColor(Color.red)
      .on(Image.circle(20).fillColor(Color.aquamarine))
      .on(Image.circle(30).fillColor(Color.steelBlue))

  val animation =
    Reactor
      .linearRamp(-200, 200, 1)
      .tickRate(20.millis)
      .render{x =>
        val y = x.degrees.sin * 200
        val planet = Image.circle(20.0).noStroke.fillColor(Color.seaGreen)
        val moon = Image.circle(5.0).noStroke.fillColor(Color.slateGray).at((x * 10).degrees.cos * 50, (x * 10).degrees.sin * 50)

        moon.on(planet).at(x, y)
      }

  val stars =
    Reactor.init(3)
      .onTick(nPoints => nPoints + 2)
      .render(nPoints => Image.star(nPoints, 300, 150, 0.degrees))

  sealed trait State {
    def dead: Boolean = this.lifespan <= 0
    def lifespan: Int
    def next: State
    def older: State
  }
  final case class Circle(lifespan: Int) extends State {
    def next: State =
      Star(300)

    def older: State =
      this.copy(lifespan = lifespan - 10)
  }
  final case class Star(lifespan: Int) extends State {
    def next: State =
      Pentagon(300)

    def older: State =
      this.copy(lifespan = lifespan - 10)
  }
  final case class Pentagon(lifespan: Int) extends State {
    def next: State =
      Circle(300)

    def older: State =
      this.copy(lifespan = lifespan - 10)
  }

  val initialState: State = Circle(300)
  val stateOnTick = (state: State) =>
    if(state.dead) state.next else state.older
  val stateRender = (state: State) => {
    state match {
      case Circle(lifespan) =>
        Image.circle(lifespan * 2.0)
          .on(Image.circle((lifespan + 10) * 2.0))
          .on(Image.circle((lifespan + 20) * 2.0))
          .strokeColor(Color.royalBlue)
          .strokeWidth(5.0)
      case Star(lifespan) =>
        Image.star(5, lifespan.toDouble, lifespan / 2.0, lifespan.degrees)
          .on(Image.star(5, (lifespan + 20).toDouble, (lifespan + 20) / 2.0, lifespan.degrees))
          .on(Image.star(5, (lifespan + 40).toDouble, (lifespan + 40) / 2.0, lifespan.degrees))
          .strokeColor(Color.crimson)
          .strokeWidth(5.0)
      case Pentagon(lifespan) =>
        Image.regularPolygon(5, lifespan.toDouble, lifespan.degrees)
          .on(Image.regularPolygon(5, (lifespan + 10).toDouble, lifespan.degrees))
          .on(Image.regularPolygon(5, (lifespan + 20).toDouble, lifespan.degrees))
          .strokeColor(Color.lightSeaGreen)
          .strokeWidth(5.0)
    }
  }

  val cycles = Reactor.init(initialState).onTick(stateOnTick).render(stateRender)

  val frame = Frame.size(600, 600).background(Color.darkSlateGray)


  def main(args: Array[String]): Unit = {
    //val result: Unit = image.draw()
    //result
    // Comment out the above and uncomment the below to display the animation
    // animation.run(frame)
    cycles.run(frame)
  }
}
