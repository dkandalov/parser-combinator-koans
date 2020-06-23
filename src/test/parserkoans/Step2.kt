package parserkoans

import org.junit.Ignore
import org.junit.Test
import parserkoans.util.shouldEqual

fun <T1, T2> inOrder(parser1: Parser<T1>, parser2: Parser<T2>): Parser<List2<T1, T2>> = TODO()

class `Step 2 - combining parsers in order` {
    @Ignore
    @Test fun `1 - no match`() {
        val parser = inOrder(string("foo"), string("bar"))
        parser.parse(Input("---")) shouldEqual null
        parser.parse(Input("foo---")) shouldEqual null
        parser.parse(Input("---bar")) shouldEqual null
    }

    @Ignore
    @Test fun `2 - full match with two parsers`() {
        val parser = inOrder(string("foo"), string("bar"))
        val input = Input("foobar")
        parser.parse(input) shouldEqual Output(
            List2("foo", "bar"),
            nextInput = input.consumed()
        )
    }

/*
    @Test fun `3 - full match with three parsers`() {
        val parser = inOrder(string("foo"), string("bar"), string("buz"))
        val input = Input("foobarbuz")
        parser.parse(input) shouldEqual Output(
            List3("foo", "bar", "buz"),
            nextInput = input.consumed()
        )
    }
*/
}
