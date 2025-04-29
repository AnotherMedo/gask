package app

import gears.async.*

object Compress extends cask.MainRoutes{

  @cask.decorators.compress
  @cask.get("/")
  def hello()(using Async) = {
    "Hello World! Hello World! Hello World!"
  }

  initialize()
}
