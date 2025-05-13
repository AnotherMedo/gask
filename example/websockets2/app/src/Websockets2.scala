package app

import io.undertow.websockets.WebSocketConnectionCallback
import io.undertow.websockets.core.{AbstractReceiveListener, BufferedTextMessage, WebSocketChannel, WebSockets}
import io.undertow.websockets.spi.WebSocketHttpExchange

object Websockets2 extends gask.MainRoutes{
  @gask.websocket("/connect/:userName")
  def showUserProfile(userName: String): gask.WebsocketResult = {
    if (userName != "haoyi") gask.Response("", statusCode = 403)
    else new WebSocketConnectionCallback() {
      override def onConnect(exchange: WebSocketHttpExchange, channel: WebSocketChannel): Unit = {
        channel.getReceiveSetter.set(
          new AbstractReceiveListener() {
            override def onFullTextMessage(channel: WebSocketChannel, message: BufferedTextMessage) = {
              message.getData match{
                case "" => channel.close()
                case data => WebSockets.sendTextBlocking(userName + " " + data, channel)
              }
            }
          }
        )
        channel.resumeReceives()
      }
    }
  }

  initialize()
}
