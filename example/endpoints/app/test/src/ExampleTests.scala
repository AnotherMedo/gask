package app
import io.undertow.Undertow

import gears.async.default.given
import gears.async.*
import utest._

object ExampleTests extends TestSuite{
  def withServer[T](example: gask.main.Main)(f: String => T): T = {
    Async.blocking:
      val server = Undertow.builder
        .addHttpListener(8081, "localhost")
        .setHandler(example.defaultHandler)
        .build
      server.start()


      val res =
        try f("http://localhost:8081")
        finally server.stop()
      res
  }

  val tests = Tests{
    test("Endpoints") - withServer(Endpoints){ host =>
      requests.get(s"$host/echo/200").text() ==> "Echo 200"
      requests.get(s"$host/echo/200").statusCode ==> 200
      requests.get(s"$host/echo/400", check = false).text() ==> "Echo 400"
      requests.get(s"$host/echo/400", check = false).statusCode ==> 400
    }
  }
}
