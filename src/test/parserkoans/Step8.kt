package parserkoans

import dev.minutest.ContextBuilder
import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext

class `Step 8 - plus-minus parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<Expression>> {
        fixture {
            object : Parser<Expression> {

                val number = number().map { IntLiteral(it.toInt()) }

                val plusOrMinus = inOrder(
                    number,
                    repeat(inOrder(oneOf(string(" + "), string(" - ")), number))
                ).map { (first, rest) ->
                    rest.fold(first as Expression) { left, (op, right) ->
                        when (op) {
                            " - " -> Minus(left, right)
                            " + " -> Plus(left, right)
                            else  -> error("")
                        }
                    }
                }

                val expression: Parser<Expression> = oneOf(plusOrMinus, number)

                override fun parse(input: Input) = expression.parse(input)
            }
        }

        `minus parser tests`()
        `plus parser tests`()
    }
}

fun ContextBuilder<Parser<Expression>>.`plus parser tests`() {
    test("add two numbers") {
        parse(Input("1 + 2"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        parse(Input("12 + 34"))?.payload shouldEqual Plus(IntLiteral(12), IntLiteral(34))
    }

    test("add three numbers (left associative)") {
        parse(Input("1 + 2 + 3"))?.payload.let {
            it.toStringExpression() shouldEqual "((1 + 2) + 3)"
            it.evaluate() shouldEqual 6
        }
    }

    test("add and subtract") {
        parse(Input("1 - 2 + 3 - 4"))?.payload.let {
            it.toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
            it.evaluate() shouldEqual -2
        }
    }
}
