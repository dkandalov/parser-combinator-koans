package parserkoans.`2 - Calculator parser`

import org.junit.Test
import parserkoans.*

object MinusGrammar {

    private val expression: Parser<ASTNode> = TODO()

    fun parse(s: String) = expression.parse(Input(s))
}

class `Step 7 - minus parser` {
    @Test fun `1 - subtract two numbers`() {
        MinusGrammar.parse("1 - 2")?.payload shouldEqual Minus(IntLiteral(1), IntLiteral(2))
        MinusGrammar.parse("2 - 1")?.payload shouldEqual Minus(IntLiteral(2), IntLiteral(1))
    }

    @Test fun `2 - subtract three numbers`() {
        MinusGrammar.parse("1 - 2 - 3")?.payload.let {
            it shouldEqual Minus(
                Minus(IntLiteral(1), IntLiteral(2)),
                IntLiteral(3)
            )
            it.toStringExpression() shouldEqual "((1 - 2) - 3)"
        }
    }

    @Test fun `3 - subtract five numbers`() {
        MinusGrammar.parse("1 - 2 - 3 - 4 - 5")?.payload
            .toStringExpression() shouldEqual "((((1 - 2) - 3) - 4) - 5)"
    }
}
