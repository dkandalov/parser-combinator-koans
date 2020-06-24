package parserkoans

import org.junit.Test

class `Step 9 - plus-minus-multiply parser` {
    private val number = number().map { IntLiteral(it.toInt()) }

    private val plusOrMinus =
        inOrder(ref { expression1 }, repeat(inOrder(oneOf(string(" + "), string(" - ")), ref { expression1 })))
            .map { (first, rest) ->
                rest.fold(first) { left, (op, right) ->
                    when (op) {
                        " - " -> Minus(left, right)
                        " + " -> Plus(left, right)
                        else -> error("")
                    }
                }
            }

    private val multiply = inOrder(number, repeat(inOrder(string(" * "), number)))
        .map { (first, rest) ->
            rest.fold(first as Expression) { left, (_, right) ->
                Multiply(left, right)
            }
        }

    private val expression1 = oneOf(multiply, number)
    private val expression: Parser<Expression> = oneOf(plusOrMinus, multiply, number)

    @Test fun `1 - add and subtract`() {
        expression.parse(Input("1 - 2 + 3 - 4"))?.payload.let {
            it.toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
            it.evaluate() shouldEqual -2
        }
    }

    @Test fun `2 - multiply three numbers`() {
        expression.parse(Input("2 * 3 * 4"))?.payload.let {
            it.toStringExpression() shouldEqual "((2 * 3) * 4)"
            it.evaluate() shouldEqual 24
        }
    }

    @Test fun `3 - add and multiply`() {
        expression.parse(Input("1 * 2 + 3"))?.payload
            .toStringExpression() shouldEqual "((1 * 2) + 3)"

        expression.parse(Input("1 + 2 * 3"))?.payload
            .toStringExpression() shouldEqual "(1 + (2 * 3))"

        expression.parse(Input("1 + 2 * 3 * 4 - 5 - 6"))?.payload
            .toStringExpression() shouldEqual "(((1 + ((2 * 3) * 4)) - 5) - 6)"
    }
}
