package parserkoans

import org.junit.Test


object CalculatorGrammar {
    private val number = number().map { IntLiteral(it.toInt()) }

    private val plusOrMinus =
        inOrder(
            ref { expression1 },
            onceOrMore(inOrder(
                oneOf(string(" + "), string(" - ")),
                ref { expression1 }
            ))
        ).map { (first, rest) ->
            rest.fold(first) { left, (op, right) ->
                when (op) {
                    " - " -> Minus(left, right)
                    " + " -> Plus(left, right)
                    else -> error("")
                }
            }
        }

    private val multiply =
        inOrder(
            ref { term },
            onceOrMore(inOrder(string(" * "), ref { term }))
        ).map { (first, rest) ->
            rest.fold(first) { left, (_, right) -> Multiply(left, right) }
        }

    private val parens =
        inOrder(string("("), ref { expression }, string(")"))
            .map { (_, it, _) -> it }

    private val term = oneOf(parens, number)
    private val expression1 = oneOf(multiply, term)
    private val expression: Parser<ASTNode> = oneOf(plusOrMinus, multiply, term)

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
