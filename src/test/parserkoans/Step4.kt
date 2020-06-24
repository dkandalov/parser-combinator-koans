package parserkoans

import org.junit.Ignore
import org.junit.Test

fun <T> repeat(parser: Parser<T>) = object : Parser<List<T>> {
    override fun parse(input: Input): Output<List<T>>? {
        TODO()
    }
}

class `Step 4 - one or more parser` {

    private val parser = repeat(string("foo"))

    @Ignore
    @Test fun `1 - no match`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("f--")) shouldEqual null
        parser.parse(Input("fo-")) shouldEqual null
    }

    @Ignore
    @Test fun `2 - match once`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual Output(
            payload = listOf("foo"),
            nextInput = input.consumed()
        )
    }

    @Ignore
    @Test fun `3 - match twice`() {
        val input = Input("foofoo")
        parser.parse(input) shouldEqual Output(
            payload = listOf("foo", "foo"),
            nextInput = input.consumed()
        )
    }

    @Ignore
    @Test fun `4 - match five times`() {
        val input = Input("foofoofoofoofoo")
        parser.parse(input) shouldEqual Output(
            payload = listOf("foo", "foo", "foo", "foo", "foo"),
            nextInput = input.consumed()
        )
    }
}
