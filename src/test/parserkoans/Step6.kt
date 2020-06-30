package parserkoans

import org.junit.Test

fun <T> ref(f: () -> Parser<T>): Parser<T> = object : Parser<T> {
    override fun parse(input: Input) = f().parse(input)
}

object PlusGrammar {
    private val number = number().map { IntLiteral(it.toInt()) }

    private val plus = inOrder(number, string(" + "), ref { expression })
        .map { (left, _, right) -> Plus(left, right) }

    private val expression: Parser<ASTNode> = oneOf(plus, number)

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 6 - plus parser` {
    @Test fun `1 - parse number`() {
        PlusGrammar.parse("123")?.payload shouldEqual IntLiteral(123)
    }

    @Test fun `2 - add two numbers`() {
        PlusGrammar.parse("1 + 2")?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        PlusGrammar.parse("12 + 34")?.payload shouldEqual Plus(IntLiteral(12), IntLiteral(34))
    }

    @Test fun `3 - add three numbers`() {
        PlusGrammar.parse("1 + 2 + 3")?.payload.let {
            it shouldEqual Plus(
                IntLiteral(1),
                Plus(IntLiteral(2), IntLiteral(3))
            )
            it.toStringExpression() shouldEqual "(1 + (2 + 3))"
        }
    }
}
