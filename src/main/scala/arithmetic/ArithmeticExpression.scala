package arithmetic

enum ArithmeticExpression:
  case Num(foo: Int)
  case Minus(bar: ArithmeticExpression)
  case Plus(augend: ArithmeticExpression, addend: ArithmeticExpression)
  case Mult(multiplicand: ArithmeticExpression, multiplier: ArithmeticExpression)
  case Div(dividend: ArithmeticExpression, divisor: ArithmeticExpression)
  case Pow(base: ArithmeticExpression, exponent: ArithmeticExpression)

object ArithmeticExpression:
  def evaluate(expression: ArithmeticExpression): Double = expression match
    case Num(foo) => foo.toDouble
    case Minus(bar) => -evaluate(bar)
    case Plus(augend, addend) => evaluate(augend) + evaluate(addend)
    case Mult(multiplicand, multiplier) => evaluate(multiplicand) * evaluate(multiplier)
    case Div(dividend, divisor) =>
      if (evaluate(divisor) == 0) Double.NaN
      else evaluate(dividend) / evaluate(divisor)
    case Pow(base, exponent) =>
      val exponentValue = evaluate(exponent).toInt
      if (exponentValue > 0) evaluate(base) * evaluate(Pow(base, Num(exponentValue - 1)))
      else if (exponentValue < 0) (1 / evaluate(base)) * evaluate(Pow(base, Num(exponentValue + 1)))
      else 1.0

  def pretty(expression: ArithmeticExpression): String = expression match
    case Num(foo) =>
      if (foo < 0) s"($foo)"
      else s"$foo"
    case Minus(bar) => s"(-${pretty(bar)})"
    case Plus(augend, addend) => s"(${pretty(augend)} + ${pretty(addend)})"
    case Mult(multiplicand, multiplier) => s"(${pretty(multiplicand)} * ${pretty(multiplier)})"
    case Div(dividend, divisor) => s"(${pretty(dividend)} / ${pretty(divisor)})"
    case Pow(base, exponent) => s"(${pretty(base)} ^ ${pretty(exponent)})"

  def evaluate(expressions: List[ArithmeticExpression]): List[Double] =
    expressions.map(expression => evaluate(expression))

  def showResults(expressions: List[ArithmeticExpression]): String =
    val results: List[String] = expressions.map {
      expression =>
        val prettyExpression = pretty(expression)
        val result = evaluate(expression)
        s"$prettyExpression = $result"
    }
    results.mkString("\n")
