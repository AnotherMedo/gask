package app

case class Websockets4()(implicit cc: castor.Context,
                         log: gask.Logger) extends gask.Routes{
  // make sure compress decorator passes non-requests through correctly
  override def decorators = Seq(new gask.decorators.compress())
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

object Websockets4Main extends gask.Main{
  val allRoutes = Seq(Websockets4())
}
