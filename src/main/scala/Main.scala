import arithmetic.ArithmeticExpression
import arithmetic.ArithmeticExpression._

object Main {
  def main(args: Array[String]): Unit = {
    val list: List[ArithmeticExpression] = List(
      Plus(Num(3), Num(5)),
      Mult(Num(2), Num(3)),
      Div(Num(6), Num(2)),
      Pow(Num(2), Num(3)),
      Pow(Num(124), Minus(Num(1)))
    )

    val evaluatedResults = ArithmeticExpression.evaluate(list)
    // Evaluation Results: List(8.0, 6.0, 3.0, 8.0, 0.008064516129032258)
    println(s"Evaluation Results: $evaluatedResults")

    val showResult = showResults(list)
    //(3 + 5) = 8.0
    //(2 * 3) = 6.0
    //(6 / 2) = 3.0
    //(2 ^ 3) = 8.0
    //(124 ^ (-1)) = 0.008064516129032258
    println(s"$showResult")
    //no trailing newline character - yahoooo
    println("no trailing newline character - yahoooo!!")
  }
}
