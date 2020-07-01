package parserkoans.`2 - Calculator parser`

import org.junit.Test
import parserkoans.*

fun ASTNode.evaluate(): Int =
    when (this) {
        is IntLiteral -> value
        is Plus       -> left.evaluate() + right.evaluate()
        is Minus      -> left.evaluate() - right.evaluate()
        is Multiply   -> left.evaluate() * right.evaluate()
    }

class `Step 11 - evaluate expression` {
    @Test fun `1 - number with parens`() {
        CalculatorGrammar.parse("(123)")?.payload?.evaluate() shouldEqual 123
        CalculatorGrammar.parse("((123))")?.payload?.evaluate() shouldEqual 123
    }

    @Test fun `2 - binary operations with parens`() {
        CalculatorGrammar.parse("(1 + 2)")?.payload?.evaluate() shouldEqual 3
        CalculatorGrammar.parse("(1 - 2)")?.payload?.evaluate() shouldEqual -1
        CalculatorGrammar.parse("(1 * 2)")?.payload?.evaluate() shouldEqual 2

        CalculatorGrammar.parse("(1) + (2)")?.payload?.evaluate() shouldEqual 3
        CalculatorGrammar.parse("(1) - (2)")?.payload?.evaluate() shouldEqual -1
        CalculatorGrammar.parse("(1) * (2)")?.payload?.evaluate() shouldEqual 2
    }

    @Test fun `3 - change associativity with parens`() {
        CalculatorGrammar.parse("1 - (2 + (3 - 4))")?.payload?.evaluate() shouldEqual 0
    }

    @Test fun `4 - change precedence with parens`() {
        CalculatorGrammar.parse("(1 - 2) + (3 - 4)")?.payload?.evaluate() shouldEqual -2
        CalculatorGrammar.parse("(1 + 2) * 3 * (4 - 5)")?.payload?.evaluate() shouldEqual -9
    }
}