package app
object StaticFiles extends gask.MainRoutes{
  @gask.get("/")
  def index() = {
    "Hello!"
  }

  @gask.staticFiles("/static/file")
  def staticFileRoutes() = "resources/gask"

  @gask.staticResources("/static/resource")
  def staticResourceRoutes() = "gask"

  @gask.staticResources("/static/resource2")
  def staticResourceRoutes2() = "."

  initialize()
}
