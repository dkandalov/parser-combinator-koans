package parserkoans

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import org.junit.Ignore
import org.junit.Test

fun <T> ref(f: () -> Parser<T>): Parser<T> = object : Parser<T> {
    override fun parse(input: Input) = f().parse(input)
}

class `Step 6 - plus parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<Expression>> {
        fixture {
            object : Parser<Expression> {

                val number = number().map { IntLiteral(it.toInt()) }

                val plus = inOrder(number, string(" + "), ref { expression })
                    .map { (left, _, right) -> Plus(left, right) }

                val expression: Parser<Expression> = oneOf(plus, number)

                override fun parse(input: Input) = expression.parse(input)
            }
        }
        test("1 - parse number") {
            parse(Input("123"))?.payload shouldEqual IntLiteral(123)
        }
        test("2 - add two numbers") {
            parse(Input("1 + 2"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
            parse(Input("12 + 34"))?.payload shouldEqual Plus(IntLiteral(12), IntLiteral(34))
        }
        test("3 - add three numbers (right associative)") {
            parse(Input("1 + 2 + 3"))?.payload.let {
                it shouldEqual Plus(
                    IntLiteral(1),
                    Plus(IntLiteral(2), IntLiteral(3))
                )
                it.toStringExpression() shouldEqual "(1 + (2 + 3))"
                it.evaluate() shouldEqual 6
            }
        }
    }
}
