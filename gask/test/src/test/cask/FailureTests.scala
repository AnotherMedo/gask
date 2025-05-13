package test.gask

import gask.model.Request
import utest._

object FailureTests extends TestSuite {
  class myDecorator extends gask.RawDecorator {
    def wrapFunction(ctx: Request, delegate: Delegate) = {
      delegate(ctx, Map("extra" -> 31337))
    }
  }

  val tests = Tests{
    "mismatchedDecorators" - {
      val m = utest.compileError("""
        object Decorated extends gask.MainRoutes{
          @myDecorator
          @gask.websocket("/hello/:world")
          def hello(world: String)(extra: Int) = ???
          initialize()
        }
      """).msg
      assert(m.contains("required: gask.router.Decorator[_, gask.endpoints.WebsocketResult, _, _]"))
    }

    "noEndpoint" - {
      utest.compileError("""
        object Decorated extends gask.MainRoutes{
          @gask.get("/hello/:world")
          @myDecorator()
          def hello(world: String)(extra: Int)= world
          initialize()
        }
      """).msg ==>
        "Last annotation applied to a function must be an instance of Endpoint, not test.gask.FailureTests.myDecorator"
      }

    "tooManyEndpoint" - {
      utest.compileError("""
        object Decorated extends gask.MainRoutes{
          @gask.get("/hello/:world")
          @gask.get("/hello/:world")
          def hello(world: String)(extra: Int)= world
          initialize()
        }
      """).msg ==>
        "You can only apply one Endpoint annotation to a function, not 2 in gask.endpoints.get, gask.endpoints.get"
    }
  }
}
