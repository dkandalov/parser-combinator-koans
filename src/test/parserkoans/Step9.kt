package parserkoans

import org.junit.Test

object PlusMinusMultGrammar {
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
            number,
            onceOrMore(inOrder(string(" * "), number))
        ).map { (first, rest) ->
            rest.fold(first as ASTNode) { left, (_, right) ->
                Multiply(left, right)
            }
        }

    private val expression1 = oneOf(multiply, number)
    private val expression: Parser<ASTNode> = oneOf(plusOrMinus, multiply, number)

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 9 - plus-minus-multiply parser` {
    @Test fun `1 - add and subtract`() {
        PlusMinusMultGrammar.parse("1 - 2 + 3 - 4")?.payload
            .toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
    }

    @Test fun `2 - multiply three numbers`() {
        PlusMinusMultGrammar.parse("2 * 3 * 4")?.payload
            .toStringExpression() shouldEqual "((2 * 3) * 4)"
    }

    @Test fun `3 - add and multiply`() {
        PlusMinusMultGrammar.parse("1 * 2 + 3")?.payload
            .toStringExpression() shouldEqual "((1 * 2) + 3)"

        PlusMinusMultGrammar.parse("1 + 2 * 3")?.payload
            .toStringExpression() shouldEqual "(1 + (2 * 3))"

        PlusMinusMultGrammar.parse("1 + 2 * 3 * 4 - 5 - 6")?.payload
            .toStringExpression() shouldEqual "(((1 + ((2 * 3) * 4)) - 5) - 6)"
    }
}
