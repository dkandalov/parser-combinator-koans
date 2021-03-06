package parserkoans

import org.junit.Test

/*
 * To complete this koan, assign to `CalculatorGrammar.expression` a parser which produces
 * `IntLiteral`, `Plus`, `Minus` or `Multiply` and can handle parenthesis by processing expressions
 * inside parenthesis first. (It's ok to copy and paste some code from the previous koans.)
 */

object CalculatorGrammar {

    private val expression: Parser<ASTNode> = TODO()

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 10 - plus-minus-multiply with parenthesis parser` {
    @Test fun `1 - number with parenthesis`() {
        CalculatorGrammar.parse("(123)")?.payload shouldEqual IntLiteral(123)
        CalculatorGrammar.parse("((123))")?.payload shouldEqual IntLiteral(123)
    }

    @Test fun `2 - binary operations with parenthesis`() {
        CalculatorGrammar.parse("(1 + 2)")?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1 - 2)")?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1 * 2)")?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))

        CalculatorGrammar.parse("(1) + (2)")?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1) - (2)")?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1) * (2)")?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))
    }

    @Test fun `3 - change associativity with parenthesis`() {
        CalculatorGrammar.parse("1 - (2 + (3 - 4))")?.payload
            .toStringExpression() shouldEqual "(1 - (2 + (3 - 4)))"
    }

    @Test fun `4 - change precedence with parenthesis`() {
        CalculatorGrammar.parse("(1 - 2) + (3 - 4)")?.payload
            .toStringExpression() shouldEqual "((1 - 2) + (3 - 4))"

        CalculatorGrammar.parse("(1 + 2) * 3 * (4 - 5)")?.payload
            .toStringExpression() shouldEqual "(((1 + 2) * 3) * (4 - 5))"
    }
}
