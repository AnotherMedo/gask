package app
object QueryParams extends gask.MainRoutes{

  @gask.get("/article/:articleId") // Mandatory query param, e.g. HOST/article/foo?param=bar
  def getArticle(articleId: Int, param: String) = {
    s"Article $articleId $param"
  }

  @gask.get("/article2/:articleId") // Optional query param
  def getArticleOptional(articleId: Int, param: Option[String] = None) = {
    s"Article $articleId $param"
  }

  @gask.get("/article3/:articleId") // Optional query param with default
  def getArticleDefault(articleId: Int, param: String = "DEFAULT VALUE") = {
    s"Article $articleId $param"
  }

  @gask.get("/article4/:articleId") // 1-or-more param, e.g. HOST/article/foo?param=bar&param=qux
  def getArticleSeq(articleId: Int, param: Seq[String]) = {
    s"Article $articleId $param"
  }

  @gask.get("/article5/:articleId") // 0-or-more query param
  def getArticleOptionalSeq(articleId: Int, param: Seq[String] = Nil) = {
    s"Article $articleId $param"
  }

  @gask.get("/user2/:userName") // allow unknown params, e.g. HOST/article/foo?foo=bar&qux=baz
  def getUserProfileAllowUnknown(userName: String, params: gask.QueryParams) = {
    s"User $userName " + params.value
  }

  @gask.get("/statics/foo")
  def getStatic() = {
    "static route takes precedence"
  }

  @gask.get("/statics/:foo")
  def getDynamics(foo: String) = {
    s"dynamic route $foo"
  }

  @gask.get("/statics/bar")
  def getStatic2() = {
    "another static route"
  }

  initialize()
}
