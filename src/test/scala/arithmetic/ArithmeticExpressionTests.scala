package arithmetic

import arithmetic.ArithmeticExpression._
import org.scalactic.Equality
import org.scalactic.TolerantNumerics
import org.scalatest.funsuite.AnyFunSuite

class ArithmeticExpressionTests extends AnyFunSuite:
  given dblEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.005)

  // basic tests for evaluate
  test("Num: evaluates whole numbers") {
    assert(evaluate(Num(42)) === 42.0)
  }
  test("Minus: evaluates negative numbers") {
    assert(evaluate(Minus(Num(42))) === -42.0)
  }
  test("Plus: evaluates addition") {
    assert(evaluate(Plus(Num(42), Num(69))) === 111.0)
  }
  test("Mult: evaluates multiplication") {
    assert(evaluate(Mult(Num(4), Num(6))) === 24.0)
  }
  test("Div: evaluates division") {
    assert(evaluate(Div(Num(42), Num(2))) === 21.0)
  }
  test("Pow: evaluates exponentiations") {
    assert(evaluate(Pow(Num(2), Num(10))) === 1024.0)
  }

  // advanced tests for evaluate
  test("Minus: supports negating negative numbers") {
    assert(evaluate(Minus(Minus(Num(-420)))) === -420.0)
  }
  test("Plus: supports negative addends") {
    assert(evaluate(Plus(Num(42), Minus(Num(69)))) === -27.0)
  }
  test("Mult: supports negative multipliers") {
    assert(evaluate(Mult(Minus(Num(1)), Minus(Num(1)))) === 1.0)
  }
  test("Div: supports negative divisors") {
    assert(evaluate(Div(Num(1), Minus(Num(3)))) === -0.33)
  }
  test("Div: supports floating-point division") {
    assert(evaluate(Div(Num(69), Num(42))) === 1.64)
  }
  test("Div: supports dividing by 0") {
    assert(evaluate(Div(Num(420), Num(0))).isNaN)
  }
  test("all: supports nested arithmetic expressions") {
    assert(evaluate(Pow(Mult(Div(Plus(Num(42), Minus(Num(69))), Num(9)), Minus(Num(1))), Num(1))) === 3.0)
  }
  test("Pow: supports 'to the power of 1'") {
    assert(evaluate(Pow(Num(1024), Num(1))) === 1024.0)
  }
  test("Pow: supports 'to the power of 0'") {
    assert(evaluate(Pow(Num(1024), Num(0))) === 1.0)
  }
  test("Pow: supports 'to the power of [negative numbers]'") {
    assert(evaluate(Pow(Num(6), Num(-1))) === 0.17)
  }

  // basic tests for pretty
  test("Num: parses expressions with whole numbers") {
    assert(pretty(Num(42)) === "42")
  }
  test("Minus: parses expressions with negative numbers") {
    assert(pretty(Minus(Num(42))) === "(-42)")
  }
  test("Plus: parses expressions with addition") {
    assert(pretty(Plus(Num(42), Num(69))) === "(42 + 69)")
  }
  test("Mult: parses expressions with multiplication") {
    assert(pretty(Mult(Num(4), Num(6))) === "(4 * 6)")
  }
  test("Div: parses expressions with division") {
    assert(pretty(Div(Num(42), Num(2))) === "(42 / 2)")
  }
  test("Pow: parses expressions with exponentiations") {
    assert(pretty(Pow(Num(2), Num(10))) === "(2 ^ 10)")
  }

  // advanced tests for pretty
  test("Minus: parses expressions negating negative numbers") {
    assert(pretty(Minus(Minus(Num(-420)))) === "(-(-(-420)))")
  }
  test("Plus: parses expressions with negative addends") {
    assert(pretty(Plus(Num(42), Minus(Num(69)))) === "(42 + (-69))")
  }
  test("Mult: parses expressions with negative multipliers") {
    assert(pretty(Mult(Minus(Num(1)), Minus(Num(1)))) === "((-1) * (-1))")
  }
  test("Div: parses expressions with negative divisors") {
    assert(pretty(Div(Num(1), Minus(Num(3)))) === "(1 / (-3))")
  }
  test("all: parses expressions with nested arithmetic expressions") {
    assert(pretty(Pow(Mult(Div(Plus(Num(42), Minus(Num(69))), Num(9)), Minus(Num(1))), Num(1))) === "((((42 + (-69)) / 9) * (-1)) ^ 1)")
  }

  // basic tests for evaluate w/lists
  test("Num: evaluates lists with whole numbers") {
    assert(evaluate(List(Num(42))) === List(42.0))
  }
  test("Minus: evaluates lists with negative numbers") {
    assert(evaluate(List(Minus(Num(42)))) === List(-42.0))
  }
  test("Plus: evaluates lists with addition") {
    assert(evaluate(List(Plus(Num(42), Num(69)))) === List(111.0))
  }
  test("Mult: evaluates lists with multiplication") {
    assert(evaluate(List(Mult(Num(4), Num(6)))) === List(24.0))
  }
  test("Div: evaluates lists with division") {
    assert(evaluate(List(Div(Num(42), Num(2)))) === List(21.0))
  }
  test("Pow: evaluates lists with exponentiations") {
    assert(evaluate(List(Pow(Num(2), Num(10)))) === List(1024.0))
  }

  // advanced tests for evaluate w/lists
  test("Num, Minus, Plus: evaluates lists with different arithmetic operations") {
    assert(evaluate(List(Num(42), Minus(Num(42)), Plus(Num(42), Num(69)))) === List(42.0, -42.0, 111.0))
  }
  test("Mult, Div, Pow: evaluates lists with different arithmetic operations") {
    assert(evaluate(List(Mult(Num(4), Num(6)), Div(Num(42), Num(2)), Pow(Num(2), Num(10)))) === List(24.0, 21.0, 1024.0))
  }
  test("all: evaluates lists with nested arithmetic expressions") {
    assert(evaluate(List(Mult(Num(23), Num(-3)), Pow(Div(Plus(Num(42), Minus(Num(69))), Num(9)), Num(1)))) === List(-69, -3.0))
  }
  test("supports empty lists") {
    assert(evaluate(List()) === List())
  }
  test("Div: evaluates lists with illegal division") {
    val actual = evaluate(List(Div(Num(420), Num(0)), Div(Num(1), Minus(Num(3)))))
    val expected = List(Double.NaN, -0.33)
    assert(actual.length === expected.length)
    for ((actual, expected) <- actual.zip(expected)) (actual, expected) match
      case (Double.NaN, Double.NaN) => assert(true)
      case _ => actual === expected
  }
