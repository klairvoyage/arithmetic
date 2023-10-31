package arithmetic

enum ArithmeticExpression:
  case Num(foo: Int)
  case Minus(bar: ArithmeticExpression)
  case Plus(augend: ArithmeticExpression, addend: ArithmeticExpression)
  case Mult(multiplicand: ArithmeticExpression, multiplier: ArithmeticExpression)
  case Div(dividend: ArithmeticExpression, divisor: ArithmeticExpression)
