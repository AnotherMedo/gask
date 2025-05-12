package app
object Cookies extends gask.MainRoutes{
  @gask.get("/read-cookie")
  def readCookies(username: gask.Cookie) = {
    username.value
  }

  @gask.get("/store-cookie")
  def storeCookies() = {
    gask.Response(
      "Cookies Set!",
      cookies = Seq(gask.Cookie("username", "the_username"))
    )
  }

  @gask.get("/delete-cookie")
  def deleteCookie() = {
    gask.Response(
      "Cookies Deleted!",
      cookies = Seq(gask.Cookie("username", "", expires = java.time.Instant.EPOCH))
    )
  }

  initialize()
}
