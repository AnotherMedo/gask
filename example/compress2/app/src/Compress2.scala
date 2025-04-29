package app

import gears.async.*
import gears.async.default.given

case class Compress2()(implicit cc: castor.Context,
                       log: cask.Logger) extends cask.Routes{
  override def decorators = Seq(new cask.decorators.compress())

  @cask.get("/")
  def hello()(using bloop: Async) =  {
    "Hello World! Hello World! Hello World!"
  }

  initialize()
}

object Compress2Main extends cask.Main{
  val allRoutes = Seq(Compress2())
}
