package app

import utest._
import gask.Logger.Console.globalLogger
import castor.Context.Simple.global

object ExampleTests extends TestSuite{


  def withServer[T](example: gask.main.Main)(f: String => T): T = {
    val server = io.undertow.Undertow.builder
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
    test("Websockets") - withServer(Websockets3Main){ host =>
      @volatile var out = List.empty[String]
      // 4. open websocket
      val ws = gask.WsClient.connect("ws://localhost:8081/connect/haoyi"){
        case gask.Ws.Text(s) => out = s :: out
      }

      try {
        // 5. send messages
        ws.send(gask.Ws.Text("hello"))
        ws.send(gask.Ws.Text("world"))
        ws.send(gask.Ws.Text(""))
        Thread.sleep(100)
        out ==> List("haoyi world", "haoyi hello")

        val ex = intercept[Exception](
          gask.WsClient.connect("ws://localhost:8081/connect/nobody") {
            case _ => /*do nothing*/
          }
        )
        assert(ex.getMessage.contains("403"))
      }finally ws.send(gask.Ws.Close())
    }
  }
}
