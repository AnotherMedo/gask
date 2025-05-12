package app
object FormJsonPost extends gask.MainRoutes{
  @gask.postJson("/json")
  def jsonEndpoint(value1: ujson.Value, value2: Seq[Int]) = {
    "OK " + value1 + " " + value2
  }

  @gask.postJson("/json-obj")
  def jsonEndpointObj(value1: ujson.Value, value2: Seq[Int]) = {
    ujson.Obj(
      "value1" -> value1,
      "value2" -> value2
    )
  }

  @gask.postForm("/form")
  def formEndpoint(value1: gask.FormValue, value2: Seq[Int]) = {
    "OK " + value1 + " " + value2
  }

  @gask.postForm("/form-obj")
  def formEndpointObj(value1: gask.FormValue, value2: Seq[Int]) = {
    ujson.Obj(
      "value1" -> value1.value,
      "value2" -> value2
    )
  }

  @gask.postForm("/upload")
  def uploadFile(image: gask.FormFile) = {
    image.fileName
  }


  @gask.postJson("/json-extra")
  def jsonEndpointExtra(value1: ujson.Value,
                        value2: Seq[Int],
                        params: gask.QueryParams,
                        segments: gask.RemainingPathSegments) = {
    "OK " + value1 + " " + value2 + " " + params.value + " " + segments.value
  }

  @gask.postJsonCached("/json-obj-cached")
  def jsonEndpointObjCached(value1: ujson.Value, value2: Seq[Int], request: gask.Request) = {
    ujson.Obj(
      "value1" -> value1,
      "value2" -> value2,
      // `postJsonCached` buffers up the body of the request in memory before parsing,
      // giving you access to the request body data if you want to use it yourself
      "body" -> request.text()
    )
  }

  @gask.postForm("/form-extra")
  def formEndpointExtra(value1: gask.FormValue, 
                        value2: Seq[Int],
                        params: gask.QueryParams,
                        segments: gask.RemainingPathSegments) = {
    "OK " + value1 + " " + value2 + " " + params.value + " " + segments.value
  }
  
  initialize()
}
