package parserkoans

import org.junit.Test

fun <T> ref(f: () -> Parser<T>): Parser<T> = object : Parser<T> {
    override fun parse(input: Input) = f().parse(input)
}

class `Step 6 - plus parser` {
    private val number = number().map { IntLiteral(it.toInt()) }

    private val plus = inOrder(number, string(" + "), ref { expression })
        .map { (left, _, right) -> Plus(left, right) }

    private val expression: Parser<Expression> = oneOf(plus, number)

    @Test fun `1 - parse number`() {
        expression.parse(Input("123"))?.payload shouldEqual IntLiteral(123)
    }

    @Test fun `2 - add two numbers`() {
        expression.parse(Input("1 + 2"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        expression.parse(Input("12 + 34"))?.payload shouldEqual Plus(IntLiteral(12), IntLiteral(34))
    }

    @Test fun `3 - add three numbers (right associative)`() {
        expression.parse(Input("1 + 2 + 3"))?.payload.let {
            it shouldEqual Plus(
                IntLiteral(1),
                Plus(IntLiteral(2), IntLiteral(3))
            )
            it.toStringExpression() shouldEqual "(1 + (2 + 3))"
            it.evaluate() shouldEqual 6
        }
    }
}
