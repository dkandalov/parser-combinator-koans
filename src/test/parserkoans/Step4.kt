package parserkoans

import org.junit.Test
import parserkoans.util.shouldEqual

fun <T> repeat(parser: Parser<T>): Parser<List<T>> = object : Parser<List<T>> {
    override fun parse(input: Input): Output<List<T>>? {
        var result = Output(emptyList<T>(), input)
        var output = parser.parse(result.nextInput)
        while (output != null) {
            result = Output(result.payload + output.payload, output.nextInput)
            output = parser.parse(output.nextInput)
        }
        return if (result.payload.isEmpty()) null else result
    }
}

class `Step 4 - repeat parser` {
    private val parser = repeat(string("foo"))

    @Test fun `1 - no match`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("f--")) shouldEqual null
        parser.parse(Input("fo-")) shouldEqual null
    }

    @Test fun `2 - match once`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual
            Output(listOf("foo"), nextInput = input.consumed())
    }

    @Test fun `3 - match twice`() {
        val input = Input("foofoo")
        parser.parse(input) shouldEqual
            Output(listOf("foo", "foo"), nextInput = input.consumed())
    }

    @Test fun `4 - match three times`() {
        val input = Input("foofoofoo")
        parser.parse(input) shouldEqual
            Output(listOf("foo", "foo", "foo"), nextInput = input.consumed())
    }
}
