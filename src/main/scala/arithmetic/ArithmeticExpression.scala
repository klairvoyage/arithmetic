package arithmetic

enum ArithmeticExpression:
  case Num(foo: Int)
  case Minus(bar: ArithmeticExpression)
  case Plus(augend: ArithmeticExpression, addend: ArithmeticExpression)
  case Mult(multiplicand: ArithmeticExpression, multiplier: ArithmeticExpression)
  case Div(dividend: ArithmeticExpression, divisor: ArithmeticExpression)

object ArithmeticExpression:
  def evaluate(expression: ArithmeticExpression): Double = expression match
    case Num(foo) => foo.toDouble
    case Minus(bar) => -evaluate(bar)
    case Plus(augend, addend) => evaluate(augend) + evaluate(addend)
    case Mult(multiplicand, multiplier) => evaluate(multiplicand) * evaluate(multiplier)
    case Div(dividend, divisor) =>
      if (evaluate(divisor) == 0) Double.NaN
      else evaluate(dividend) / evaluate(divisor)

  def pretty(expression: ArithmeticExpression): String = expression match
    case Num(foo) =>
      if (foo < 0) s"($foo)"
      else s"$foo"
    case Minus(bar) => s"(-${pretty(bar)})"
    case Plus(augend, addend) => s"(${pretty(augend)} + ${pretty(addend)})"
    case Mult(multiplicand, multiplier) => s"(${pretty(multiplicand)} * ${pretty(multiplier)})"
    case Div(dividend, divisor) => s"(${pretty(dividend)} / ${pretty(divisor)})"
