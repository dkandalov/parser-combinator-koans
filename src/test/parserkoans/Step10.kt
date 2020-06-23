package parserkoans

import org.junit.Ignore
import org.junit.Test
import parserkoans.util.shouldEqual

class `Step 10 - plus-minus-multiply with parens parser` {
    private val expression: Parser<Expression> = TODO()

    @Ignore
    @Test fun `1 - number with parens`() {
        expression.parse(Input("(123)"))?.payload shouldEqual IntLiteral(123)
        expression.parse(Input("((123))"))?.payload shouldEqual IntLiteral(123)
    }

    @Ignore
    @Test fun `2 - binary operations with parens`() {
        expression.parse(Input("(1 + 2)"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("(1 - 2)"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("(1 * 2)"))?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))

        expression.parse(Input("(1) + (2)"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("(1) - (2)"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("(1) * (2)"))?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))
    }

    @Ignore
    @Test fun `3 - change associativity with parens`() {
        expression.parse(Input("1 - (2 + (3 - 4))"))?.payload
            .toStringExpression() shouldEqual "(1 - (2 + (3 - 4)))"
    }

    @Ignore
    @Test fun `4 - change precedence with parens`() {
        expression.parse(Input("(1 - 2) + (3 - 4)"))?.payload
            .toStringExpression() shouldEqual "((1 - 2) + (3 - 4))"

        expression.parse(Input("(1 + 2) * 3 * (4 - 5)"))?.payload
            .toStringExpression() shouldEqual "(((1 + 2) * 3) * (4 - 5))"
    }
}
