package app
object Decorated extends gask.MainRoutes {
  class User {
    override def toString = "[haoyi]"
  }
  class loggedIn extends gask.RawDecorator {
    def wrapFunction(ctx: gask.Request, delegate: Delegate) = {
      delegate(ctx, Map("user" -> new User()))
    }
  }
  class withExtra extends gask.RawDecorator {
    def wrapFunction(ctx: gask.Request, delegate: Delegate) = {
      delegate(ctx, Map("extra" -> 31337))
    }
  }

  class withCustomHeader extends gask.RawDecorator {
    def wrapFunction(request: gask.Request, delegate: Delegate) = {
      request.headers.get("x-custom-header").map(_.head) match {
        case Some(header) => delegate(request, Map("customHeader" -> header))
        case None =>
          gask.router.Result.Success(
            gask.model.Response(
              s"Request is missing required header: 'X-CUSTOM-HEADER'",
              400
            )
          )
      }
    }
  }

  @withExtra()
  @gask.get("/hello/:world")
  def hello(world: String)(extra: Int) = {
    world + extra
  }

  @loggedIn()
  @gask.get("/internal/:world")
  def internal(world: String)(user: User) = {
    world + user
  }

  @withCustomHeader()
  @gask.get("/echo")
  def echoHeader(request: gask.Request)(customHeader: String) = {
    customHeader
  }

  @withExtra()
  @loggedIn()
  @gask.get("/internal-extra/:world")
  def internalExtra(world: String)(user: User)(extra: Int) = {
    world + user + extra
  }

  @withExtra()
  @loggedIn()
  @gask.get("/ignore-extra/:world")
  def ignoreExtra(world: String)(user: User) = {
    world + user
  }

  @loggedIn()
  @gask.get("/hello-default")
  def defaults(world: String = "world")(user: User) = {
    world + user
  }
  initialize()
}
