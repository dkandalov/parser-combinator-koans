package parserkoans

import org.junit.Test

/*
 * To complete this koan, assign to `PlusGrammar.expression` a parser which produces `IntLiteral` or `Plus`.
 * This should be achievable by combining parsers from the previous koans.
 *
 * The parsers in this koan are all defined inside `PlusGrammar` object.
 * In theory, we could use a function and define parsers as local variables.
 * However, we can't reference local variables defined on the following lines,
 * but can forward-reference fields, e.g. with `ref { expression }`.
 *
 * This koan can be completed without forward-references, but they will be useful later.
 *
 * After finishing the koan, you should have the first somewhat realistic parser
 * which consumes a string and produces [abstract syntax tree](https://en.wikipedia.org/wiki/Abstract_syntax_tree).
 */

fun <T> ref(f: () -> Parser<T>): Parser<T> = object : Parser<T> {
    override fun parse(input: Input) = f().parse(input)
}

private object PlusGrammar {

    private val expression: Parser<ASTNode> = TODO()

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
