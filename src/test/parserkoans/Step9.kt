package parserkoans

import org.junit.Test

object PlusMinusMultGrammar {

    private val expression: Parser<ASTNode> = TODO()

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 9 - plus-minus-multiply parser` {
    @Test fun `1 - add and subtract`() {
        PlusMinusMultGrammar.parse("1 - 2 + 3 - 4")?.payload
            .toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
    }

    @Test fun `2 - multiply three numbers`() {
        PlusMinusMultGrammar.parse("2 * 3 * 4")?.payload
            .toStringExpression() shouldEqual "((2 * 3) * 4)"
    }

    @Test fun `3 - add and multiply`() {
        PlusMinusMultGrammar.parse("1 * 2 + 3")?.payload
            .toStringExpression() shouldEqual "((1 * 2) + 3)"

        PlusMinusMultGrammar.parse("1 + 2 * 3")?.payload
            .toStringExpression() shouldEqual "(1 + (2 * 3))"

        PlusMinusMultGrammar.parse("1 + 2 * 3 * 4 - 5 - 6")?.payload
            .toStringExpression() shouldEqual "(((1 + ((2 * 3) * 4)) - 5) - 6)"
    }
}
