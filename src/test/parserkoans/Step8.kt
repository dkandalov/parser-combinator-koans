package parserkoans

import org.junit.Test
import parserkoans.util.*

class `Step 8 - plus-minus parser` {
    private val number = number().map { IntLiteral(it.toInt()) }

    private val plusOrMinus = inOrder(number, repeat(inOrder(oneOf(string(" + "), string(" - ")), number)))
        .map { (first, rest) ->
            rest.fold(first as Expression) { left, (op, right) ->
                when (op) {
                    " - " -> Minus(left, right)
                    " + " -> Plus(left, right)
                    else -> error("")
                }
            }
        }

    private val expression: Parser<Expression> = oneOf(plusOrMinus, number)

    @Test fun `1 - subtract two numbers`() {
        expression.parse(Input("1 - 2"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("2 - 1"))?.payload shouldEqual Minus(IntLiteral(2), IntLiteral(1))
    }

    @Test fun `2 - add two numbers`() {
        expression.parse(Input("1 + 2"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("12 + 34"))?.payload shouldEqual Plus(IntLiteral(12), IntLiteral(34))
    }

    @Test fun `3 - subtract three numbers (left associative)`() {
        expression.parse(Input("1 - 2 - 3"))?.payload.let {
            it.toStringExpression() shouldEqual "((1 - 2) - 3)"
            it.evaluate() shouldEqual -4
        }
    }

    @Test fun `4 - add three numbers (left associative)`() {
        expression.parse(Input("1 + 2 + 3"))?.payload.let {
            it.toStringExpression() shouldEqual "((1 + 2) + 3)"
            it.evaluate() shouldEqual 6
        }
    }

    @Test fun `5 - add and subtract`() {
        expression.parse(Input("1 - 2 + 3 - 4"))?.payload.let {
            it.toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
            it.evaluate() shouldEqual -2
        }
    }
}
