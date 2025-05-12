package app
object MinimalApplication extends gask.MainRoutes{
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
