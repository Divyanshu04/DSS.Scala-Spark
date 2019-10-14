import org.scalatest.FunSuite

class Testresult extends FunSuite {
  test("Testcube.cube") {
    assert(Testcube.cube(3) === 27)
  }
}