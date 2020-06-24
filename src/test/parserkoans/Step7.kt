package parserkoans

import org.junit.Test

class `Step 7 - minus parser` {
    private val number = number().map { IntLiteral(it.toInt()) }

    private val minus = inOrder(number, repeat(inOrder(string(" - "), number)))
        .map { (first, rest) ->
            rest.fold(first as Expression) { left, (_, right) -> Minus(left, right) }
        }

    private val expression: Parser<Expression> = oneOf(minus, number)

    @Test fun `1 - subtract two numbers`() {
        expression.parse(Input("1 - 2"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("2 - 1"))?.payload shouldEqual Minus(IntLiteral(2), IntLiteral(1))
    }

    @Test fun `2 - subtract three numbers (left associative)`() {
        expression.parse(Input("1 - 2 - 3"))?.payload.let {
            it shouldEqual Minus(
                Minus(IntLiteral(1), IntLiteral(2)),
                IntLiteral(3)
            )
            it.toStringExpression() shouldEqual "((1 - 2) - 3)"
            it.evaluate() shouldEqual -4
        }
    }

    @Test fun `3 - subtract five numbers (left associative)`() {
        expression.parse(Input("1 - 2 - 3 - 4 - 5"))?.payload.let {
            it.toStringExpression() shouldEqual "((((1 - 2) - 3) - 4) - 5)"
            it.evaluate() shouldEqual -4
        }
    }
}
