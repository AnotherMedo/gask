package app

case class Compress3()(implicit cc: castor.Context,
                       log: gask.Logger) extends gask.Routes{

  @gask.get("/")
  def hello() = {
    
    "Hello World! Hello World! Hello World!"
  }

  initialize()
}

object Compress3Main extends gask.Main{
  override def mainDecorators = Seq(new gask.decorators.compress())
  val allRoutes = Seq(Compress3())
}