package app
object Twirl extends gask.MainRoutes{
  @gask.get("/")
  def hello() = {
    "<!doctype html>" + html.hello("Hello World")
  }

  initialize()
}
