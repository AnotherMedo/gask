package app

case class Websockets3()(implicit cc: castor.Context,
                         log: gask.Logger) extends gask.Routes{
  @gask.websocket("/connect/:userName")
  def showUserProfile(userName: String): gask.WebsocketResult = {
    if (userName != "haoyi") gask.Response("", statusCode = 403)
    else gask.WsHandler { channel =>
      gask.WsActor {
        case gask.Ws.Text("") => channel.send(gask.Ws.Close())
        case gask.Ws.Text(data) =>
          channel.send(gask.Ws.Text(userName + " " + data))
      }
    }
  }

  initialize()
}

object Websockets3Main extends gask.Main{
  val allRoutes = Seq(Websockets3())
}
