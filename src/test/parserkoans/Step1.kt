package parserkoans

import org.junit.Test

fun string(s: String) = object : Parser<String> {
    override fun parse(input: Input) =
        TODO()
}

class `Step 1 - string parser` {
    private val parser = string("foo")

    @Test fun `1 - no match`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("f--")) shouldEqual null
        parser.parse(Input("fo-")) shouldEqual null
        parser.parse(Input("foo", offset = 1)) shouldEqual null
        parser.parse(Input("-foo")) shouldEqual null
    }

    @Test fun `2 - full match`() {
        val input = Input("foo")
        parser.parse(input) shouldEqual Output(
            payload = "foo",
            nextInput = input.copy(offset = 3)
        )
    }

    @Test fun `3 - prefix match`() {
        val input = Input("foo--")
        parser.parse(input) shouldEqual Output(
            payload = "foo",
            nextInput = input.copy(offset = 3)
        )
    }

    @Test fun `4 - postfix match`() {
        val input = Input("--foo", offset = 2)
        parser.parse(input) shouldEqual Output(
            payload = "foo",
            nextInput = input.copy(offset = 5)
        )
    }
}
