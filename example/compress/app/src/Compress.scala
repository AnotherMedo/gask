package app

import gears.async.*

object Compress extends gask.MainRoutes{

  @gask.decorators.compress
  @gask.get("/")
  def hello()(using Async) = {
    "Hello World! Hello World! Hello World!"
  }

  initialize()
}
