package parserkoans

import dev.minutest.ContextBuilder
import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext

class `Step 9 - plus-minus-multiply parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<Expression>> {
        fixture {
            object : Parser<Expression> {

                val number = number().map { IntLiteral(it.toInt()) }

                val plusOrMinus = inOrder(
                    ref { expression_ },
                    repeat(inOrder(oneOf(string(" - "), string(" + ")), ref { expression_ }))
                ).map { (first, rest) ->
                    rest.fold(first as Expression) { left, (op, right) ->
                        when (op) {
                            " - " -> Minus(left, right)
                            " + " -> Plus(left, right)
                            else -> error("")
                        }
                    }
                }

                val multiply = inOrder(
                    number,
                    repeat(inOrder(oneOf(string(" * ")), number))
                ).map { (first, rest) ->
                    rest.fold(first as Expression) { left, (_, right) ->
                        Multiply(left, right)
                    }
                }

                val expression_: Parser<Expression> = oneOf(multiply, number)
                val expression: Parser<Expression> = oneOf(plusOrMinus, multiply, number)

                override fun parse(input: Input) = expression.parse(input)
            }
        }

        `minus parser tests`()

        `plus parser tests`()

        `multiply parser tests`()
    }
}

fun ContextBuilder<Parser<Expression>>.`multiply parser tests`() {
    test("multiply three numbers") {
        parse(Input("2 * 3 * 4"))?.payload.let {
            it.toStringExpression() shouldEqual "((2 * 3) * 4)"
            it.evaluate() shouldEqual 24
        }
    }

    test("add and multiply") {
        parse(Input("1 * 2 + 3"))?.payload
            .toStringExpression() shouldEqual "((1 * 2) + 3)"

        parse(Input("1 + 2 * 3"))?.payload
            .toStringExpression() shouldEqual "(1 + (2 * 3))"

        parse(Input("1 + 2 * 3 * 4 - 5 - 6"))?.payload
            .toStringExpression() shouldEqual "(((1 + ((2 * 3) * 4)) - 5) - 6)"
    }
}
