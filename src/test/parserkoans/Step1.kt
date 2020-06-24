package parserkoans

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import org.junit.Ignore
import org.junit.Test

fun string(s: String) = object : Parser<String> {
    override fun parse(input: Input): Output<String>? {
        return if (!input.unprocessed.startsWith(s)) null
        else Output(s, input.copy(offset = input.offset + s.length))
    }
}

class `Step 1 - string parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<String>> {
        fixture {
            string("foo")
        }
        test("1 - no match") {
            parse(Input("")) shouldEqual null
            parse(Input("---")) shouldEqual null
            parse(Input("f--")) shouldEqual null
            parse(Input("fo-")) shouldEqual null
        }
        test("2 - full match") {
            val input = Input("foo")
            parse(input) shouldEqual Output(
                payload = "foo",
                nextInput = input.copy(offset = 3)
            )
        }
        test("3 - prefix match") {
            val input = Input("foo--")
            parse(input) shouldEqual Output(
                payload = "foo",
                nextInput = input.copy(offset = 3)
            )
        }
        test("4 - postfix match") {
            val input = Input("--foo", offset = 2)
            parse(input) shouldEqual Output(
                payload = "foo",
                nextInput = input.copy(offset = 5)
            )
        }
    }
}
