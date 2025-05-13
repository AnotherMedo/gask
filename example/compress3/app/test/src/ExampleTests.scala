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
    test("Compress3Main") - withServer(Compress3Main){ host =>
      val expected = "Hello World! Hello World! Hello World!"
      requests.get(s"$host").text() ==> expected
      val compressed = requests.get(s"$host", autoDecompress = false).text()
      assert(
        compressed.length < expected.length
      )

    }
  }
}
