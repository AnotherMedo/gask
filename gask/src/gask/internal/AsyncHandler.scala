package gask.internal

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
          /* AHAAAA this is a quote from the gears doc for Async.group:
          *    "Runs body inside a spawnable context where it is allowed to spawn concurrently runnable Futures. When the body returns, 
          *     all spawned futures are cancelled and waited for."
          * So if I understand correctly, the Future is cancelled by Async.group once the handleRequest function (of mainHandler -> defaultHandler) is called and executed
          * This is normal. the handler's function is simply to route the request and then return once that is done. It does not live to provide the context that allows
          * us to call AsyncOperations.sleep() 
          * The Async context created in the main function is indeed carried through to the handlers, so no problem on that end. The real problem is the handler exiting too early
          * We can possibly prevent the main handler from exiting as long as some key is not pressed or something
          * 
          * What I tried:
          *   1. Simply removing the call to future -> IT WORKED OMFG
          *   2. Tried to do some cleanup: removed the Async.group
          *   3. Since Async.current was not working I used summon[Async] as the doc mentioned the two were equivalent
          */
          ex.putAttachment(AsyncAttachmentKey, summon[Async])
          handler.handleRequest(ex)
        }
      }
    )
  }
}
