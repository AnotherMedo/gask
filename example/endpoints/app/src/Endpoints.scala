package app

class custom(val path: String, val methods: Seq[String])
  extends gask.HttpEndpoint[Int, Seq[String]]{
  def wrapFunction(ctx: gask.Request, delegate: Delegate) = {
    delegate(ctx, Map()).map{num =>
      gask.Response("Echo " + num, statusCode = num)
    }
  }

  def wrapPathSegment(s: String) = Seq(s)

  type InputParser[T] = gask.endpoints.QueryParamReader[T]
}

object Endpoints extends gask.MainRoutes{


  @custom("/echo/:status", methods = Seq("get"))
  def echoStatus(status: String) = {
    status.toInt
  }

  initialize()
}
