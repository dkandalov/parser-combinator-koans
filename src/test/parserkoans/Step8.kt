package parserkoans

import org.junit.Test

object PlusMinusGrammar {

    private val expression: Parser<ASTNode> = TODO()

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 8 - plus-minus parser` {
    @Test fun `1 - subtract two numbers`() {
        PlusMinusGrammar.parse("1 - 2")?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        PlusMinusGrammar.parse("2 - 1")?.payload shouldEqual Minus(IntLiteral(2), IntLiteral(1))
    }

    @Test fun `2 - add two numbers`() {
        PlusMinusGrammar.parse("1 + 2")?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))
        PlusMinusGrammar.parse("12 + 34")?.payload shouldEqual Plus(IntLiteral(12), IntLiteral(34))
    }

    @Test fun `3 - subtract three numbers`() {
        PlusMinusGrammar.parse("1 - 2 - 3")?.payload
            .toStringExpression() shouldEqual "((1 - 2) - 3)"
    }

    @Test fun `4 - add three numbers`() {
        PlusMinusGrammar.parse("1 + 2 + 3")?.payload
            .toStringExpression() shouldEqual "((1 + 2) + 3)"
    }

    @Test fun `5 - add and subtract`() {
        PlusMinusGrammar.parse("1 - 2 + 3 - 4")?.payload
            .toStringExpression() shouldEqual "(((1 - 2) + 3) - 4)"
    }
}
