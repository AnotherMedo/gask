package app
object HttpMethods extends gask.MainRoutes{
  @gask.route("/login", methods = Seq("get", "post"))
  def login(request: gask.Request) = {
    if (request.exchange.getRequestMethod.equalToString("post")) "do_the_login"
    else "show_the_login_form"
  }

  @gask.route("/session", methods = Seq("delete"))
  def session(request: gask.Request) = {
    "delete_the_session"
  }

  @gask.route("/session", methods = Seq("secretmethod"))
  def admin(request: gask.Request) = {
    "security_by_obscurity"
  }

  @gask.route("/api", methods = Seq("options"))
  def cors(request: gask.Request) = {
    "allow_cors"
  }


  initialize()
}
