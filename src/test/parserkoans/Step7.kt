package parserkoans

import dev.minutest.ContextBuilder
import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext

class `Step 7 - minus parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<Expression>> {
        fixture {
            object : Parser<Expression> {

                val number = number().map { IntLiteral(it.toInt()) }

                val plus = inOrder(
                    number,
                    repeat(inOrder(string(" - "), number))
                ).map { (first, rest) ->
                    rest.fold(first as Expression) { left, (_, right) ->
                        Minus(left, right)
                    }
                }

                val expression: Parser<Expression> = oneOf(plus, number)

                override fun parse(input: Input) = expression.parse(input)
            }
        }

        `minus parser tests`()
    }
}

fun ContextBuilder<Parser<Expression>>.`minus parser tests`() {
    test("subtract two numbers") {
        parse(Input("1 - 2"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        parse(Input("2 - 1"))?.payload shouldEqual Minus(IntLiteral(2), IntLiteral(1))
    }

    test("subtract three numbers (left associative)") {
        parse(Input("1 - 2 - 3"))?.payload.let {
            it shouldEqual Minus(
                Minus(IntLiteral(1), IntLiteral(2)),
                IntLiteral(3)
            )
            it.toStringExpression() shouldEqual "((1 - 2) - 3)"
            it.evaluate() shouldEqual -4
        }
    }

    test("subtract five numbers (left associative)") {
        parse(Input("1 - 2 - 3 - 4 - 5"))?.payload.let {
            it.toStringExpression() shouldEqual "((((1 - 2) - 3) - 4) - 5)"
            it.evaluate() shouldEqual -13
        }
    }
}
