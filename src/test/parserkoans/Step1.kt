package parserkoans

import org.junit.Test

fun string(s: String) = object : Parser<String> {
    override fun parse(input: Input) = TODO()
}

class `Step 1 - string parser` {
    private val foo: Parser<String> = string("foo")
    private val bar: Parser<String> = string("bar")

    @Test fun `1 - no match`() {
        foo.parse(Input("")) shouldEqual null
        foo.parse(Input("---")) shouldEqual null
        foo.parse(Input("f--")) shouldEqual null
        foo.parse(Input("fo-")) shouldEqual null
        foo.parse(Input("foo", offset = 1)) shouldEqual null
        foo.parse(Input("-foo")) shouldEqual null

        foo.parse(Input("bar")) shouldEqual null
        bar.parse(Input("foo")) shouldEqual null
    }

    @Test fun `2 - full match`() {
        foo.parse(Input("foo")) shouldEqual Output(
            payload = "foo",
            nextInput = Input("foo").consumed()
        )
        bar.parse(Input("bar")) shouldEqual Output(
            payload = "bar",
            nextInput = Input("bar").consumed()
        )
    }

    @Test fun `3 - prefix match`() {
        foo.parse(Input("foo--")) shouldEqual Output(
            payload = "foo",
            nextInput = Input("foo--", offset = 3)
        )
    }

    @Test fun `4 - postfix match`() {
        foo.parse(Input("--foo", offset = 2)) shouldEqual Output(
            payload = "foo",
            nextInput = Input("--foo", offset = 5)
        )
    }
}
