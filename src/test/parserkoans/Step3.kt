package parserkoans

import org.junit.Ignore
import org.junit.Test

fun <T> oneOf(vararg parsers: Parser<T>) = object : Parser<T> {
    override fun parse(input: Input): Output<T>? {
        TODO()
    }
}

class `Step 3 - choose the first matching parser` {
    private val parser: Parser<String> =
        oneOf(string("foo"), string("bar"), string("buz"))

    @Ignore
    @Test fun `1 - no match`() {
        parser.parse(Input("---")) shouldEqual null
    }

    @Ignore
    @Test fun `2 - full match first parser`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual Output(
            payload = "foo",
            nextInput = input.consumed()
        )
    }

    @Ignore
    @Test fun `3 - full match second parser`() {
        val input = Input("bar")
        parser.parse(input) shouldEqual Output(
            payload = "bar",
            nextInput = input.consumed()
        )
    }

    @Ignore
    @Test fun `4 - full match third parser`() {
        val input = Input("buz")
        parser.parse(input) shouldEqual Output(
            payload = "buz",
            nextInput = input.consumed()
        )
    }
}
