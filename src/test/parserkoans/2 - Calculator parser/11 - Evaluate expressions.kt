package parserkoans

import org.junit.Test

/**
 * There is no need to write any code in this koan.
 * The main point here is that once the input is transformed into syntax tree,
 * it's easy to write `evaluate()` function for it.
 */

fun ASTNode.evaluate(): Int =
    when (this) {
        is IntLiteral -> value
        is Plus       -> left.evaluate() + right.evaluate()
        is Minus      -> left.evaluate() - right.evaluate()
        is Multiply   -> left.evaluate() * right.evaluate()
    }

class `Step 11 - evaluate expression` {
    @Test fun `1 - number with parenthesis`() {
        CalculatorGrammar.parse("(123)")?.payload?.evaluate() shouldEqual 123
        CalculatorGrammar.parse("((123))")?.payload?.evaluate() shouldEqual 123
    }

    @Test fun `2 - binary operations with parenthesis`() {
        CalculatorGrammar.parse("(1 + 2)")?.payload?.evaluate() shouldEqual 3
        CalculatorGrammar.parse("(1 - 2)")?.payload?.evaluate() shouldEqual -1
        CalculatorGrammar.parse("(1 * 2)")?.payload?.evaluate() shouldEqual 2

        CalculatorGrammar.parse("(1) + (2)")?.payload?.evaluate() shouldEqual 3
        CalculatorGrammar.parse("(1) - (2)")?.payload?.evaluate() shouldEqual -1
        CalculatorGrammar.parse("(1) * (2)")?.payload?.evaluate() shouldEqual 2
    }

    @Test fun `3 - change associativity with parenthesis`() {
        CalculatorGrammar.parse("1 - (2 + (3 - 4))")?.payload?.evaluate() shouldEqual 0
    }

    @Test fun `4 - change precedence with parenthesis`() {
        CalculatorGrammar.parse("(1 - 2) + (3 - 4)")?.payload?.evaluate() shouldEqual -2
        CalculatorGrammar.parse("(1 + 2) * 3 * (4 - 5)")?.payload?.evaluate() shouldEqual -9
    }
}