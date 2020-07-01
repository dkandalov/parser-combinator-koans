package parserkoans

import org.junit.Test

/*
 * To complete this koan, assign to `PlusMinusMultGrammar.expression` a parser which produces
 * `IntLiteral`, `Plus`, `Minus` or `Multiply`. (It's ok to copy-paste some code from the previous task.)
 *
 * Just like the parser from the previous koan, this parser should be able to parse `Plus` and `Minus`
 * with left-associativity. But in addition, it should be able to parse `Multiply` with higher operator precedence
 * (i.e. just like in mathematics multiplication is processed/calculated first).
 *
 * Hint:
 * `val expression = oneOf(plusOrMinus, multiply, number)` can almost be used for specifying operator precedence,
 * especially if the operator parsers refer to expression itself... or something along these lines.
 */

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
