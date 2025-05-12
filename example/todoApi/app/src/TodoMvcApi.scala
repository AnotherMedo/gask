package app
object TodoMvcApi extends gask.MainRoutes{
  case class Todo(checked: Boolean, text: String)
  object Todo{
    implicit def todoRW: upickle.default.ReadWriter[Todo] = upickle.default.macroRW[Todo]
  }
  var todos = Seq(
    Todo(true, "Get started with Gask"),
    Todo(false, "Profit!")
  )

  @gask.get("/list/:state")
  def list(state: String) = {
    val filteredTodos = state match{
      case "all" => todos
      case "active" => todos.filter(!_.checked)
      case "completed" => todos.filter(_.checked)
    }
    upickle.default.write(filteredTodos)
  }

  @gask.post("/add")
  def add(request: gask.Request) = {
    todos = Seq(Todo(false, request.text())) ++ todos
  }

  @gask.post("/toggle/:index")
  def toggle(index: Int) = {
    todos = todos.updated(index, todos(index).copy(checked = !todos(index).checked))
  }

  @gask.post("/delete/:index")
  def delete(index: Int) = {
    todos = todos.patch(index, Nil, 1)
  }

  initialize()
}
