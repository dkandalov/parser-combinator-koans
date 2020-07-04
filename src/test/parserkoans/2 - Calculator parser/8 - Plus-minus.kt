package parserkoans

import org.junit.Test

/*
 * To complete this koan, assign to `PlusMinusGrammar.expression` a parser
 * which produces `IntLiteral`, `Plus` or `Minus`. Note that both `Plus` and `Minus` operations are left-associative.
 * (It's ok to copy-paste some code from the previous koan.)
 *
 * At this point, `PlusMinusGrammar` object should more or less justify being called "grammar"
 * by looking somewhat similar to grammars expressed in
 * [Backus–Naur form](https://en.wikipedia.org/wiki/Backus%E2%80%93Naur_form).
 */

private object PlusMinusGrammar {

    private val int = number().map { IntLiteral(it.toInt()) }

    private val plusOrMinus = inOrder(
        int,
        oneOrMore(inOrder(
            oneOf(string(" + "), string(" - ")),
            int
        ))
    ).map { (first, rest) ->
        rest.fold(first as ASTNode) { left, (op, right) ->
            when (op) {
                " + " -> Plus(left, right)
                " - " -> Minus(left, right)
                else -> error("")
            }
        }
    }

    private val expression: Parser<ASTNode> = oneOf(plusOrMinus, int)

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

        PlusMinusGrammar.parse("1 + 2 - 3 + 4")?.payload
            .toStringExpression() shouldEqual "(((1 + 2) - 3) + 4)"
    }
}
