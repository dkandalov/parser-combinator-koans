package parserkoans

import org.junit.Test

/*
 * To complete this koan, implement `oneOf()` function to return parser
 * which tries all `parsers` and applies the first one which was able to consume input,
 * or `null` if none of the `parsers` match.
 */

fun <T> oneOf(vararg parsers: Parser<T>): Parser<T> = object : Parser<T> {
    override fun parse(input: Input): Output<T>? {
        TODO()
    }
}

fun <T> oneOf(parsers: List<Parser<T>>): Parser<T> = oneOf(*parsers.toTypedArray())

class `Step 3 - apply first matching parser` {
    private val parser: Parser<String> =
        oneOf(string("foo"), string("bar"), string("buz"))

    @Test fun `1 - no match`() {
        parser.parse(Input("---")) shouldEqual null
    }

    @Test fun `2 - full match first parser`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual Output(
            payload = "foo",
            nextInput = input.consumed()
        )
    }

    @Test fun `3 - full match second parser`() {
        val input = Input("bar")
        parser.parse(input) shouldEqual Output(
            payload = "bar",
            nextInput = input.consumed()
        )
    }

    @Test fun `4 - full match third parser`() {
        val input = Input("buz")
        parser.parse(input) shouldEqual Output(
            payload = "buz",
            nextInput = input.consumed()
        )
    }
}
