package app
object StaticFiles2 extends gask.MainRoutes{
  @gask.get("/")
  def index() = {
    "Hello!"
  }

  @gask.staticFiles("/static/file", headers = Seq("Cache-Control" -> "max-age=31536000"))
  def staticFileRoutes() = "resources/gask"

  @gask.decorators.compress
  @gask.staticResources("/static/resource")
  def staticResourceRoutes() = "gask"

  @gask.staticResources("/static/resource2")
  def staticResourceRoutes2() = "."

  initialize()
}
