package test.gask

import utest._

object UtilTests extends TestSuite {
  val tests = Tests{
    "splitPath" - {
      gask.internal.Util.splitPath("") ==> Seq()
      gask.internal.Util.splitPath("/") ==> Seq()
      gask.internal.Util.splitPath("////") ==> Seq()

      gask.internal.Util.splitPath("abc") ==> Seq("abc")
      gask.internal.Util.splitPath("/abc/") ==> Seq("abc")
      gask.internal.Util.splitPath("//abc") ==> Seq("abc")
      gask.internal.Util.splitPath("abc//") ==> Seq("abc")

      gask.internal.Util.splitPath("abc//def") ==> Seq("abc", "def")
      gask.internal.Util.splitPath("//abc//def//") ==> Seq("abc", "def")
    }
  }
}

