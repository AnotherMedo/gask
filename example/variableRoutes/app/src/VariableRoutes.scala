package app
object VariableRoutes extends gask.MainRoutes{
  @gask.get("/user/:userName") // variable path segment, e.g. HOST/user/lihaoyi
  def getUserProfile(userName: String) = {
    s"User $userName"
  }

  @gask.get("/path") // GET allowing arbitrary sub-paths, e.g. HOST/path/foo/bar/baz
  def getSubpath(segments: gask.RemainingPathSegments) = {
    s"Subpath ${segments.value}"
  }

  @gask.post("/path") // POST allowing arbitrary sub-paths, e.g. HOST/path/foo/bar/baz
  def postArticleSubpath(segments: gask.RemainingPathSegments) = {
    s"POST Subpath ${segments.value}"
  }

  initialize()
}
