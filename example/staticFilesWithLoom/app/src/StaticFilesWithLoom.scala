package app

import gask.internal.Util

import java.util.concurrent.{ExecutorService, Executors}

object StaticFilesWithLoom extends gask.MainRoutes{
  private val executor = Executors.newFixedThreadPool(4)

  override protected def handlerExecutor(): Option[ExecutorService] = {
    super.handlerExecutor().orElse(Some(executor))
  }
  
  @gask.get("/")
  def index() = {
    "Hello!"
  }

  @gask.staticFiles("/static/file")
  def staticFileRoutes() = "resources/gask"

  @gask.staticResources("/static/resource")
  def staticResourceRoutes() = "gask"

  @gask.staticResources("/static/resource2")
  def staticResourceRoutes2() = "."

  initialize()
}
