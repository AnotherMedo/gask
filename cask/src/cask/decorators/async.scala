package cask.decorators

import cask.model.Request
import cask.router.Result
import cask.model.Response
import cask.endpoints.WebEndpoint
import cask.internal.AsyncAttachmentKey


object async extends cask.RawDecorator{
  def wrapFunction(ctx: Request,
                       delegate: Delegate): Result[Response.Raw] = {
        val maybeAsync = ctx.exchange.getAttachment(AsyncAttachmentKey)
        if maybeAsync == null then
            throw new IllegalStateException("Async context not set on exchange!")
        delegate(ctx, Map("bloop" -> maybeAsync))
  }
}