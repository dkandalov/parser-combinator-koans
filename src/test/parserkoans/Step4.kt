package parserkoans

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import org.junit.Ignore
import org.junit.Test

fun <T> repeat(parser: Parser<T>) = object : Parser<List<T>> {
    override fun parse(input: Input): Output<List<T>>? {
        var result = Output(emptyList<T>(), input)
        var output = parser.parse(input)
        while (output != null) {
            result = Output(result.payload + output.payload, output.nextInput)
            output = parser.parse(result.nextInput)
        }
        return if (result.payload.isEmpty()) null else result
    }
}

class `Step 4 - one or more parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<List<String>>> {
        fixture {
            repeat(string("foo"))
        }
        test("1 - no match") {
            parse(Input("")) shouldEqual null
            parse(Input("---")) shouldEqual null
            parse(Input("f--")) shouldEqual null
            parse(Input("fo-")) shouldEqual null
        }

        test("2 - match once") {
            val input = Input("foo")
            parse(input) shouldEqual Output(
                payload = listOf("foo"),
                nextInput = input.consumed()
            )
        }

        test("3 - match twice") {
            val input = Input("foofoo")
            parse(input) shouldEqual Output(
                payload = listOf("foo", "foo"),
                nextInput = input.consumed()
            )
        }

        test("4 - match five times") {
            val input = Input("foofoofoofoofoo")
            parse(input) shouldEqual Output(
                payload = listOf("foo", "foo", "foo", "foo", "foo"),
                nextInput = input.consumed()
            )
        }
    }
}
