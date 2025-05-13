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

  val tests = Tests {
    test("Scalatags") - withServer(Scalatags) { host =>
      val body = requests.get(host).text()

      assert(
        body.contains("<h1>Hello World</h1>"),
        body.contains("<p>I am cow</p>"),
      )
    }
  }
}
