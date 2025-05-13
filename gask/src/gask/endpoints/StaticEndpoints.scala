package gask.endpoints

import gask.router.{HttpEndpoint, Result}
import gask.model.Request
object StaticUtil{
  def makePathAndContentType(t: String, ctx: Request) = {
    val leadingSlash = if (t.startsWith("/")) "/" else ""
    val path = leadingSlash + (gask.internal.Util.splitPath(t) ++ ctx.remainingPathSegments.flatMap(gask.internal.Util.splitPath))
      .filter(s => s != "." && s != "..")
      .mkString("/")
    val contentType = java.nio.file.Files.probeContentType(java.nio.file.Paths.get(path))

    (path, Option(contentType))
  }
}

class staticFiles(val path: String, headers: Seq[(String, String)] = Nil) extends HttpEndpoint[String, Seq[String]]{
  val methods = Seq("get")
  type InputParser[T] = QueryParamReader[T]
  override def subpath = true
  def wrapFunction(ctx: Request, delegate: Delegate) = {
    delegate(ctx, Map()).map{t =>
      val (path, contentTypeOpt) = StaticUtil.makePathAndContentType(t, ctx)
      gask.model.StaticFile(path, headers ++ contentTypeOpt.map("Content-Type" -> _))
    }
  }

  def wrapPathSegment(s: String): Seq[String] = Seq(s)
}

class staticResources(val path: String,
                      resourceRoot: ClassLoader = classOf[staticResources].getClassLoader,
                      headers: Seq[(String, String)] = Nil)
  extends HttpEndpoint[String, Seq[String]]{
  val methods = Seq("get")
  type InputParser[T] = QueryParamReader[T]
  override def subpath = true
  def wrapFunction(ctx: Request, delegate: Delegate) = {
    delegate(ctx, Map()).map { t =>
      val (path, contentTypeOpt) = StaticUtil.makePathAndContentType(t, ctx)
      gask.model.StaticResource(path, resourceRoot, headers ++ contentTypeOpt.map("Content-Type" -> _))
    }
  }


  def wrapPathSegment(s: String): Seq[String] = Seq(s)
}
