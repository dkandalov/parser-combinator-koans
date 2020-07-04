package parserkoans

import org.junit.Test

/*
 * To complete this koan, implement `string()` function to return parser
 * which consumes `Input` if it starts with the specified `value`.
 *
 * This parser can seem a bit pointless because `Output` with `String`
 * as a payload is not much more structured than the input,
 * but it's a building block for the other parsers and will make more sense later.
 */

fun string(s: String) = object : Parser<String> {
    override fun parse(input: Input) =
        if (!input.unprocessed.startsWith(s)) null
        else Output(
            payload = s,
            nextInput = input.copy(offset = input.offset + s.length)
        )
}

class `Step 1 - string parser` {
    private val fooParser: Parser<String> = string("foo")
    private val barParser: Parser<String> = string("bar")

    @Test fun `1 - no match`() {
        fooParser.parse(Input("")) shouldEqual null
        fooParser.parse(Input("---")) shouldEqual null
        fooParser.parse(Input("f--")) shouldEqual null
        fooParser.parse(Input("fo-")) shouldEqual null
        fooParser.parse(Input("foo", offset = 1)) shouldEqual null
        fooParser.parse(Input("-foo")) shouldEqual null

        fooParser.parse(Input("bar")) shouldEqual null
        barParser.parse(Input("foo")) shouldEqual null
    }

    @Test fun `2 - full match`() {
        fooParser.parse(Input("foo")) shouldEqual Output(
            payload = "foo",
            nextInput = Input("foo").consumed()
        )
        barParser.parse(Input("bar")) shouldEqual Output(
            payload = "bar",
            nextInput = Input("bar").consumed()
        )
    }

    @Test fun `3 - prefix match`() {
        fooParser.parse(Input("foo--")) shouldEqual Output(
            payload = "foo",
            nextInput = Input("foo--", offset = 3)
        )
    }

    @Test fun `4 - postfix match`() {
        fooParser.parse(Input("--foo", offset = 2)) shouldEqual Output(
            payload = "foo",
            nextInput = Input("--foo", offset = 5)
        )
    }
}
