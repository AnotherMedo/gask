package app
object Decorated2 extends gask.MainRoutes{
  class User{
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

  override def decorators = Seq(new withExtra())

  @gask.get("/hello/:world")
  def hello(world: String)(extra: Int) = {
    world + extra
  }

  @loggedIn()
  @gask.get("/internal-extra/:world")
  def internalExtra(world: String)(user: User)(extra: Int) = {
    world + user + extra
  }

  @loggedIn()
  @gask.get("/ignore-extra/:world")
  def ignoreExtra(world: String)(user: User)(extra: Int)  = {
    world + user
  }

  initialize()
}
