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
    test("MinimalApplication2") - withServer(MinimalRoutesMain){ host =>
      val success = requests.get(host)

      success.text() ==> "Hello World!"
      success.statusCode ==> 200

      requests.get(s"$host/doesnt-exist", check = false).statusCode ==> 404

      requests.post(s"$host/do-thing", data = "hello").text() ==> "olleh"

      requests.delete(s"$host/do-thing", check = false).statusCode ==> 405
    }
  }
}
