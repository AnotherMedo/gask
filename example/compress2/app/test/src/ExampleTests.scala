package app
import io.undertow.Undertow

import utest._

import gears.async.*
import gears.async.default.given

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
    test("Compress2Main") - withServer(Compress2Main) { host =>
      val expected = "Hello World! Hello World! Hello World!"
      requests.get(s"$host").text() ==> expected
      assert(
        requests.get(s"$host", autoDecompress = false).text().length < expected.length
      )
    }
  }
}
