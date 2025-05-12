import gask.util.Logger

package object gask {
  // model
  type Response[T] = model.Response[T]
  val Response = model.Response
  val Abort = model.Abort
  val Redirect = model.Redirect
  val StaticFile = model.StaticFile
  val StaticResource = model.StaticResource
  type FormEntry = model.FormEntry
  val FormEntry = model.FormEntry
  type FormValue = model.FormValue
  val FormValue = model.FormValue
  type FormFile = model.FormFile
  val FormFile = model.FormFile
  type Cookie = model.Cookie
  val Cookie = model.Cookie
  type Request = model.Request
  val Request = model.Request
  type QueryParams = model.QueryParams
  val QueryParams = model.QueryParams
  type RemainingPathSegments = model.RemainingPathSegments
  val RemainingPathSegments = model.RemainingPathSegments

  // endpoints
  type websocket = endpoints.websocket
  val WebsocketResult = endpoints.WebsocketResult
  type WebsocketResult = endpoints.WebsocketResult

  type get = endpoints.get
  type post = endpoints.post
  type put = endpoints.put
  type delete = endpoints.delete
  type patch = endpoints.patch
  type route = endpoints.route
  type staticFiles = endpoints.staticFiles
  type staticResources = endpoints.staticResources
  type postJson = endpoints.postJson
  type postJsonCached = endpoints.postJsonCached
  type getJson = endpoints.getJson
  type postForm = endpoints.postForm
  type options = endpoints.options

  // main
  type MainRoutes = main.MainRoutes
  type Routes = main.Routes

  type Main = main.Main
  type RawDecorator = router.RawDecorator
  type HttpEndpoint[InnerReturned, Input] = router.HttpEndpoint[InnerReturned, Input]

  type WsHandler = gask.endpoints.WsHandler
  val WsHandler = gask.endpoints.WsHandler
  type WsActor = gask.endpoints.WsActor
  val WsActor = gask.endpoints.WsActor
  type WsChannelActor = gask.endpoints.WsChannelActor
  type WsClient = gask.util.WsClient
  val WsClient = gask.util.WsClient
  val Ws = gask.util.Ws

  // util
  type Logger = util.Logger
  val Logger = util.Logger
}
