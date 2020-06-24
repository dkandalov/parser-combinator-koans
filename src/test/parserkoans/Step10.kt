package parserkoans

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext

class `Step 10 - plus-minus-multiply with parens parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<Expression>> {
        fixture {
            object : Parser<Expression> {

                val number = number().map { IntLiteral(it.toInt()) }

                val plusOrMinus = inOrder(
                    ref { expression_ },
                    repeat(inOrder(oneOf(string(" - "), string(" + ")), ref { expression_ }))
                ).map { (first, rest) ->
                    rest.fold(first) { left, (op, right) ->
                        when (op) {
                            " - " -> Minus(left, right)
                            " + " -> Plus(left, right)
                            else -> error("")
                        }
                    }
                }

                val multiply = inOrder(ref { expression__ }, repeat(inOrder(oneOf(string(" * ")), ref { expression__ })))
                    .map { (first, rest) ->
                        rest.fold(first) { left, (_, right) ->
                            Multiply(left, right)
                        }
                    }

                val parens = inOrder(string("("), ref { expression }, string(")"))
                    .map { (_, it, _) -> it }

                val expression__: Parser<Expression> = oneOf(parens, number)
                val expression_: Parser<Expression> = oneOf(multiply, parens, number)
                val expression: Parser<Expression> = oneOf(plusOrMinus, multiply, parens, number)

                override fun parse(input: Input) = expression.parse(input)
            }
        }

        `minus parser tests`()
        `plus parser tests`()
        `multiply parser tests`()

        test("number with parens") {
            parse(Input("(123)"))?.payload shouldEqual IntLiteral(123)
            parse(Input("((123))"))?.payload shouldEqual IntLiteral(123)
        }

        test("binary operations with parens") {
            parse(Input("(1 + 2)"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
            parse(Input("(1 - 2)"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
            parse(Input("(1 * 2)"))?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))

            parse(Input("(1) + (2)"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
            parse(Input("(1) - (2)"))?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
            parse(Input("(1) * (2)"))?.payload shouldEqual Multiply(IntLiteral(1), IntLiteral(2))
        }

        test("change associativity with parens") {
            parse(Input("1 - (2 + (3 - 4))"))?.payload
                .toStringExpression() shouldEqual "(1 - (2 + (3 - 4)))"
        }

        test("4 - change precedence with parens") {
            parse(Input("(1 - 2) + (3 - 4)"))?.payload
                .toStringExpression() shouldEqual "((1 - 2) + (3 - 4))"

            parse(Input("(1 + 2) * 3 * (4 - 5)"))?.payload
                .toStringExpression() shouldEqual "(((1 + 2) * 3) * (4 - 5))"
        }
    }
}
