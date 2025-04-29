package cask.internal

import io.undertow.server.{HttpHandler, HttpServerExchange}

import java.util.concurrent.Executor

import gears.async.*
import io.undertow.util.AttachmentKey

val AsyncAttachmentKey: AttachmentKey[Async] =
  AttachmentKey.create(classOf[Async])

final class AsyncHandler(executor: Executor, handler: HttpHandler)(using async: Async)
    extends HttpHandler {
  def handleRequest(exchange: HttpServerExchange): Unit = {
    exchange.startBlocking()
    exchange.dispatch(
      executor,
      new HttpHandler {
        override def handleRequest(ex: HttpServerExchange): Unit = {
          Async.group:
            Future:
              ex.putAttachment(AsyncAttachmentKey, Async.current)
              handler.handleRequest(ex)
        }
      }
    )
  }
}
