package app
object RedirectAbort extends gask.MainRoutes{
  @gask.get("/")
  def index() = {
    gask.Redirect("/login")
  }

  @gask.get("/login")
  def login() = {
    gask.Abort(401)
  }

  initialize()
}
