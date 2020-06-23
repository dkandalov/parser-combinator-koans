package parserkoans

import org.junit.Ignore
import org.junit.Test
import parserkoans.util.shouldEqual

fun string(s: String) = object : Parser<String> {
    override fun parse(input: Input): Output<String>? {
        TODO()
    }
}

class `Step 1 - string parser` {

    private val parser = string("foo")

    @Ignore
    @Test fun `1 - no match`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("f--")) shouldEqual null
        parser.parse(Input("fo-")) shouldEqual null
    }

    @Ignore
    @Test fun `2 - full match`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual
            Output(payload = "foo", nextInput = input.consumed())
    }

    @Ignore
    @Test fun `3 - prefix match`() {
        val input = Input("foo--")
        parser.parse(input) shouldEqual
            Output(payload = "foo", nextInput = input.copy(offset = 3))
    }

    @Ignore
    @Test fun `4 - postfix match`() {
        val input = Input("--foo", offset = 2)
        parser.parse(input) shouldEqual
            Output(payload = "foo", nextInput = input.consumed())
    }
}

fun Input.consumed() = copy(offset = value.length)
