package arithmetic

import arithmetic.ArithmeticExpression._
import org.scalactic.Equality
import org.scalactic.TolerantNumerics
import org.scalatest.funsuite.AnyFunSuite

class ArithmeticExpressionTests extends AnyFunSuite:
  given dblEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.01)

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
    assert(evaluate(Mult(Div(Plus(Num(42), Minus(Num(69))), Num(9)), Minus(Num(1)))) === 3.0)
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
    assert(pretty(Mult(Div(Plus(Num(42), Minus(Num(69))), Num(9)), Minus(Num(1)))) === "(((42 + (-69)) / 9) * (-1))")
  }
