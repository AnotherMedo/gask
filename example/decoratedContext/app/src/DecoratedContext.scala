package app

case class Context(
  session: Session
)

case class Session(data: collection.mutable.Map[String, String])

trait CustomParser[T] extends gask.router.ArgReader[Any, T, Context]
object CustomParser:
  given CustomParser[Context] with
    def arity = 0
    def read(ctx: Context, label: String, input: Any): Context = ctx
  given CustomParser[Session] with
    def arity = 0
    def read(ctx: Context, label: String, input: Any): Session = ctx.session
  given literal[Literal]: CustomParser[Literal] with
    def arity = 1
    def read(ctx: Context, label: String, input: Any): Literal = input.asInstanceOf[Literal]

object DecoratedContext extends gask.MainRoutes{

  class custom extends gask.router.Decorator[gask.Response.Raw, gask.Response.Raw, Any, Context]{

    override type InputParser[T] = CustomParser[T]

    def wrapFunction(req: gask.Request, delegate: Delegate) = {
      // Create a custom context out of the request. Custom contexts are useful
      // to group an expensive operation that may be used by multiple
      // parameter readers or that carry state. This example focuses on carrying
      // state.
      val ctx = Context(Session(collection.mutable.Map.empty)) // this would typically be populated from a signed cookie

      delegate(ctx, Map("user" -> 1337)).map{ response =>
        val extraCookies = ctx.session.data.map(
          (k, v) => gask.Cookie(k, v)
        )

        response.copy(
          cookies = response.cookies ++ extraCookies
        )
      }

    }
  }

  @custom()
  @gask.get("/hello/:world")
  def hello(world: String, req: gask.Request)(session: Session, user: Int) = {
    session.data("hello") = "world"
    world + user
  }

  initialize()
}
