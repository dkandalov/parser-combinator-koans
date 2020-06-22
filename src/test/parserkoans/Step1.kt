package parserkoans

import org.junit.Test
import parserkoans.util.shouldEqual

fun string(s: String) = object : Parser<String> {
    override fun parse(input: Input) =
        if (!input.value.drop(input.offset).startsWith(s)) null
        else Output(s, input.copy(offset = input.offset + s.length))
}

class `Step 1 - string parser` {
    private val parser = string("foo")

    @Test fun `1 - no match`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("f--")) shouldEqual null
        parser.parse(Input("fo-")) shouldEqual null
    }

    @Test fun `2 - full match`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual
            Output("foo", nextInput = input.consumed())
    }

    @Test fun `3 - prefix match`() {
        val input = Input("foo--")
        parser.parse(input) shouldEqual
            Output("foo", nextInput = input.copy(offset = 3))
    }

    @Test fun `4 - postfix match`() {
        val input = Input("--foo", offset = 2)
        parser.parse(input) shouldEqual
            Output("foo", nextInput = input.consumed())
    }
}

fun Input.consumed() = copy(offset = value.length)
