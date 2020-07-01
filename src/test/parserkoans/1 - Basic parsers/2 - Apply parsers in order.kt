package parserkoans

import org.junit.Test

fun <T1, T2> inOrder(parser1: Parser<T1>, parser2: Parser<T2>) =
    object : Parser<List2<T1, T2>> {
        override fun parse(input: Input): Output<List2<T1, T2>>? {
            TODO()
        }
    }

fun <T1, T2, T3> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>) =
    object : Parser<List3<T1, T2, T3>> {
        override fun parse(input: Input): Output<List3<T1, T2, T3>>? {
            TODO()
        }
    }

class `Step 2 - apply parsers in order` {
    @Test fun `1 - no match`() {
        val parser = inOrder(string("foo"), string("bar"))
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("foo---")) shouldEqual null
        parser.parse(Input("---bar")) shouldEqual null
    }

    @Test fun `2 - full match with two parsers`() {
        val parser = inOrder(string("foo"), string("bar"))
        val input = Input("foobar")
        parser.parse(input) shouldEqual Output(
            payload = List2("foo", "bar"),
            nextInput = input.consumed()
        )
    }

    @Test fun `3 - full match with three parsers`() {
        val parser = inOrder(string("foo"), string("bar"), string("buz"))
        val input = Input("foobarbuz")
        parser.parse(input) shouldEqual Output(
            payload = List3("foo", "bar", "buz"),
            nextInput = input.consumed()
        )
    }
}
