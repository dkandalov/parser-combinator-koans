package parserkoans

import org.junit.Test

/*
 * To complete this koan, implement `inOrder()` functions to return parser
 * which sequentially uses `parser1`, `parser2` (and `parser3`) to process input.
 * If any of the parsers can't consume input, the overall result is `null`.
 *
 * After finishing this koan, it's fair to say that we have a
 * [parser combinator](https://en.wikipedia.org/wiki/Parser_combinator)
 * which is a fancy name for parser which delegates to other parsers
 * (you can also think about it as a [higher-order function](https://en.wikipedia.org/wiki/Higher-order_function)
 * or [composite pattern](https://en.wikipedia.org/wiki/Composite_pattern)).
 */

fun <T1, T2> inOrder(parser1: Parser<T1>, parser2: Parser<T2>) =
    object : Parser<Pair<T1, T2>> {
        override fun parse(input: Input): Output<Pair<T1, T2>>? {
            TODO()
        }
    }

fun <T1, T2, T3> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>) =
    object : Parser<Triple<T1, T2, T3>> {
        override fun parse(input: Input): Output<Triple<T1, T2, T3>>? {
            TODO()
        }
    }

class `Step 2 - apply parsers in order` {
    @Test fun `1 - no match`() {
        val parser = inOrder(string("foo"), string("bar"))
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("foo---")) shouldEqual null
        parser.parse(Input("---bar")) shouldEqual null
    }

    @Test fun `2 - full match with two parsers`() {
        val parser = inOrder(string("foo"), string("bar"))
        val input = Input("foobar")
        parser.parse(input) shouldEqual Output(
            payload = Pair("foo", "bar"),
            nextInput = input.consumed()
        )
    }

    @Test fun `3 - full match with three parsers`() {
        val parser = inOrder(string("foo"), string("bar"), string("buz"))
        val input = Input("foobarbuz")
        parser.parse(input) shouldEqual Output(
            payload = Triple("foo", "bar", "buz"),
            nextInput = input.consumed()
        )
    }
}
