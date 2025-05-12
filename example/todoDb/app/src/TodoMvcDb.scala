package app
import scalasql.DbApi.Txn
import scalasql.Sc
import scalasql.SqliteDialect._

object TodoMvcDb extends gask.MainRoutes{
  val tmpDb = java.nio.file.Files.createTempDirectory("todo-gask-sqlite")
  val sqliteDataSource = new org.sqlite.SQLiteDataSource()
  sqliteDataSource.setUrl(s"jdbc:sqlite:$tmpDb/file.db")
  lazy val sqliteClient = new scalasql.DbClient.DataSource(
    sqliteDataSource,
    config = new scalasql.Config {}
  )

  class transactional extends gask.RawDecorator{
    def wrapFunction(pctx: gask.Request, delegate: Delegate) = {
      sqliteClient.transaction { txn =>
        val res = delegate(pctx, Map("txn" -> txn))
        if (res.isInstanceOf[gask.router.Result.Error]) txn.rollback()
        res
      }
    }
  }

  case class Todo[T[_]](id: T[Int], checked: T[Boolean], text: T[String])
  object Todo extends scalasql.Table[Todo]{
    implicit def todoRW = upickle.default.macroRW[Todo[Sc]]
  }

  sqliteClient.getAutoCommitClientConnection.updateRaw(
    """CREATE TABLE todo (
      |  id INTEGER PRIMARY KEY AUTOINCREMENT,
      |  checked BOOLEAN,
      |  text TEXT
      |);
      |
      |INSERT INTO todo (checked, text) VALUES
      |(1, 'Get started with Gask'),
      |(0, 'Profit!');
      |""".stripMargin
  )

  @transactional
  @gask.get("/list/:state")
  def list(state: String)(txn: Txn) = {
    val filteredTodos = state match{
      case "all" => txn.run(Todo.select)
      case "active" => txn.run(Todo.select.filter(!_.checked))
      case "completed" => txn.run(Todo.select.filter(_.checked))
    }
    upickle.default.write(filteredTodos)
  }

  @transactional
  @gask.post("/add")
  def add(request: gask.Request)(txn: Txn) = {
    val body = request.text()
    txn.run(
      Todo
        .insert
        .columns(_.checked := false, _.text := body)
        .returning(_.id)
        .single
    )

    if (body == "FORCE FAILURE") throw new Exception("FORCE FAILURE BODY")
  }

  @transactional
  @gask.post("/toggle/:index")
  def toggle(index: Int)(txn: Txn) = {
    txn.run(Todo.update(_.id === index).set(p => p.checked := !p.checked))
  }

  @transactional
  @gask.post("/delete/:index")
  def delete(index: Int)(txn: Txn) = {
    txn.run(Todo.delete(_.id === index))
  }

  initialize()
}
