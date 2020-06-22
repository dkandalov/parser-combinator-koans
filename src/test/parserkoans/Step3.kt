package parserkoans

import org.junit.Test
import parserkoans.util.shouldEqual

fun <T> oneOf(vararg parsers: Parser<T>): Parser<T> = object : Parser<T> {
    override fun parse(input: Input): Output<T>? {
        parsers.forEach {
            val output = it.parse(input)
            if (output != null) return output
        }
        return null
    }
}

class `Step 3 - choosing one of the parsers` {
    private val parser = oneOf(string("foo"), string("bar"), string("buz"))

    @Test fun `1 - no match`() {
        parser.parse(Input("---")) shouldEqual null
    }

    @Test fun `2 - full match first parser`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual Output("foo", nextInput = input.consumed())
    }

    @Test fun `3 - full match second parser`() {
        val input = Input("bar")
        parser.parse(input) shouldEqual Output("bar", nextInput = input.consumed())
    }

    @Test fun `4 - full match third parser`() {
        val input = Input("buz")
        parser.parse(input) shouldEqual Output("buz", nextInput = input.consumed())
    }
}
