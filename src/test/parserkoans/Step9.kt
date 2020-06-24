package parserkoans

import org.junit.Ignore
import org.junit.Test

class `Step 9 - plus-minus-multiply parser` {

    private val expression: Parser<Expression> = TODO("combine parsers")

    @Ignore
    @Test fun `1 - add and subtract`() {
        expression.parse(Input("1 - 2 + 3 - 4"))?.payload.let {
            it.toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
            it.evaluate() shouldEqual -2
        }
    }

    @Ignore
    @Test fun `2 - multiply three numbers`() {
        expression.parse(Input("2 * 3 * 4"))?.payload.let {
            it.toStringExpression() shouldEqual "((2 * 3) * 4)"
            it.evaluate() shouldEqual 24
        }
    }

    @Ignore
    @Test fun `3 - add and multiply`() {
        expression.parse(Input("1 * 2 + 3"))?.payload
            .toStringExpression() shouldEqual "((1 * 2) + 3)"

        expression.parse(Input("1 + 2 * 3"))?.payload
            .toStringExpression() shouldEqual "(1 + (2 * 3))"

        expression.parse(Input("1 + 2 * 3 * 4 - 5 - 6"))?.payload
            .toStringExpression() shouldEqual "(((1 + ((2 * 3) * 4)) - 5) - 6)"
    }
}
