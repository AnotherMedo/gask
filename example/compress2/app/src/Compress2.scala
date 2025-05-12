package app

import gears.async.*
import gears.async.default.given

case class Compress2()(implicit cc: castor.Context,
                       log: gask.Logger) extends gask.Routes{
  override def decorators = Seq(new gask.decorators.compress())

  @gask.get("/")
  def hello()(using Async) =  {
    AsyncOperations.sleep(5000)
    "Hello World! Hello World! Hello World!"
  }

  initialize()
}

object Compress2Main extends gask.Main{
  val allRoutes = Seq(Compress2())
}
