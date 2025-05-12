package app

object MultipartFormSubmission extends gask.MainRoutes {

  @gask.get("/")
  def index() =
    gask.model.Response(
      """
    <!DOCTYPE html>
    <html lang="en">
    <head></head>
    <body>
        <form action="/post" method="post" enctype="multipart/form-data">
            <input type="file" id="somefile" name="somefile">
            <button type="submit">Submit</button>
        </form>
    </body>
    </html>
    """, 200, Seq(("Content-Type", "text/html")))

  @gask.postForm("/post")
  def post(somefile: gask.FormFile) =
    s"filename: ${somefile.fileName}"

  initialize()
}
