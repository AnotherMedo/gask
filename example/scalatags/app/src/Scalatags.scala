package app
import scalatags.Text.all._
object Scalatags extends gask.MainRoutes{
  @gask.get("/")
  def hello() = {
    doctype("html")(
      html(
        body(
          h1("Hello World"),
          p("I am cow")
        )
      )
    )
  }

  initialize()
}
