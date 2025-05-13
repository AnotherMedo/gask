package app

case class MinimalRoutes()(implicit cc: castor.Context,
                           log: gask.Logger) extends gask.Routes{
  @gask.get("/")
  def hello() = {
    "Hello World!"
  }

  @gask.post("/do-thing")
  def doThing(request: gask.Request) = {
    request.text().reverse
  }

  initialize()
}
object MinimalRoutesMain extends gask.Main{
  val allRoutes = Seq(MinimalRoutes())
}