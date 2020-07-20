package app

case class Compress2()(implicit val actorContext: castor.Context,
                       val log: cask.Logger) extends cask.Routes{
  override def decorators = Seq(new cask.decorators.compress())

  @cask.get("/")
  def hello() = {
    "Hello World! Hello World! Hello World!"
  }

  initialize()
}

object Compress2Main extends cask.Main{
  val allRoutes = Seq(Compress2())
}
