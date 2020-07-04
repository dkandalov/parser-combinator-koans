package parserkoans

import org.junit.Test

/*
 * To complete this koan, assign to `MinusGrammar.expression` a parser which produces `IntLiteral` or `Minus`.
 * This should be achievable by combining parsers from the previous koans.
 *
 * Note that unlike the previous koan, `Minus` has to be left-associative
 * (i.e the operations are grouped from the left to right).
 */

private object MinusGrammar {

    private val int = number().map { IntLiteral(it.toInt()) }

    private val minus = inOrder(
        int,
        oneOrMore(inOrder(string(" - "), int))
    ).map { (first, rest) ->
        rest.fold(first as ASTNode) { left, (_, right) ->
            Minus(left, right)
        }
    }

    private val expression: Parser<ASTNode> = oneOf(minus, int)

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
