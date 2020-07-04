package parserkoans

import org.junit.Test

/*
 * To complete this koan, assign to `CalculatorGrammar.expression` a parser which produces
 * `IntLiteral`, `Plus`, `Minus` or `Multiply` and can handle parens by processing expressions inside parens first.
 * (It's ok to copy-paste some code from the previous koans.)
 */

object CalculatorGrammar {

    private val int = number().map { IntLiteral(it.toInt()) }

    private val plusOrMinus = inOrder(
        ref { expression2 },
        oneOrMore(inOrder(
            oneOf(string(" - "), string(" + ")),
            ref { expression2 }
        ))
    ).map { (first, rest) ->
        rest.fold(first) { left, (op, right) ->
            when (op) {
                " + " -> Plus(left, right)
                " - " -> Minus(left, right)
                else -> error("")
            }
        }
    }

    private val multiply = inOrder(
        ref { expression3 }, oneOrMore(inOrder(string(" * "), ref { expression3 }))
    ).map { (first, rest) ->
        rest.fold(first) { left, (_, right) ->
            Multiply(left, right)
        }
    }

    private val parens =
        inOrder(string("("), ref { expression }, string(")"))
            .map { (_, it, _) -> it }

    private val expression3: Parser<ASTNode> = oneOf(parens, int)
    private val expression2: Parser<ASTNode> = oneOf(multiply, parens, int)
    private val expression: Parser<ASTNode> = oneOf(plusOrMinus, multiply, parens, int)

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 10 - plus-minus-multiply with parens parser` {
    @Test fun `1 - number with parens`() {
        CalculatorGrammar.parse("(123)")?.payload shouldEqual IntLiteral(123)
        CalculatorGrammar.parse("((123))")?.payload shouldEqual IntLiteral(123)
    }

    @Test fun `2 - binary operations with parens`() {
        CalculatorGrammar.parse("(1 + 2)")?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1 - 2)")?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1 * 2)")?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))

        CalculatorGrammar.parse("(1) + (2)")?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1) - (2)")?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        CalculatorGrammar.parse("(1) * (2)")?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))
    }

    @Test fun `3 - change associativity with parens`() {
        CalculatorGrammar.parse("1 - (2 + (3 - 4))")?.payload
            .toStringExpression() shouldEqual "(1 - (2 + (3 - 4)))"
    }

    @Test fun `4 - change precedence with parens`() {
        CalculatorGrammar.parse("(1 - 2) + (3 - 4)")?.payload
            .toStringExpression() shouldEqual "((1 - 2) + (3 - 4))"

        CalculatorGrammar.parse("(1 + 2) * 3 * (4 - 5)")?.payload
            .toStringExpression() shouldEqual "(((1 + 2) * 3) * (4 - 5))"
    }
}
