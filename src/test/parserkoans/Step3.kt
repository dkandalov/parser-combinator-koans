package parserkoans

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import org.junit.Ignore
import org.junit.Test

fun <T> oneOf(vararg parsers: Parser<T>) = object : Parser<T> {
    override fun parse(input: Input): Output<T>? {
        parsers.forEach {
            val output = it.parse(input)
            if (output != null) return output
        }
        return null
    }
}

fun <T> oneOf(parsers: List<Parser<T>>): Parser<T> = oneOf(*parsers.toTypedArray())

class `Step 3 - choose the first matching parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<String>> {
        fixture {
            oneOf(string("foo"), string("bar"), string("buz"))
        }
        test("1 - no match") {
            parse(Input("---")) shouldEqual null
        }

        test("2 - full match first parser") {
            val input = Input("foo")
            parse(input) shouldEqual Output(
                payload = "foo",
                nextInput = input.consumed()
            )
        }

        test("3 - full match second parser") {
            val input = Input("bar")
            parse(input) shouldEqual Output(
                payload = "bar",
                nextInput = input.consumed()
            )
        }

        test("4 - full match third parser") {
            val input = Input("buz")
            parse(input) shouldEqual Output(
                payload = "buz",
                nextInput = input.consumed()
            )
        }
    }
}
