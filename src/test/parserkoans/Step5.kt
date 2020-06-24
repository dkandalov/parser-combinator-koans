package parserkoans

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import org.junit.Ignore
import org.junit.Test

fun number(): Parser<String> =
    repeat(oneOf((0..9).map { string(it.toString()) }))
        .map { it.joinToString("") }

fun <T, R> Parser<T>.map(transform: (T) -> R) = object : Parser<R> {
    override fun parse(input: Input): Output<R>? {
        val parser = this@map
        val output = parser.parse(input) ?: return null
        return Output(
            payload = transform(output.payload),
            nextInput = output.nextInput
        )
    }
}

class `Step 5 - number parser` : JUnit5Minutests {
    fun tests() = rootContext<Parser<String>> {
        fixture {
            number()
        }
        test("1 - match a digit") {
            parse(Input("")) shouldEqual null
            parse(Input("1")) shouldEqual Output(
                payload = "1",
                nextInput = Input("1", offset = 1)
            )
        }

        test("2 - match all digits") {
            val allDigits = (0..9).map { it.toString() }
            allDigits.forEach { digit ->
                parse(Input(digit)) shouldEqual Output(
                    payload = digit,
                    nextInput = Input(digit).consumed()
                )
            }
        }

        test("3 - full match number") {
            val input = Input("123")
            parse(input) shouldEqual Output(
                payload = "123",
                nextInput = input.consumed()
            )
        }

        test("4 - prefix match number") {
            val input = Input("123---")
            parse(input) shouldEqual Output(
                payload = "123",
                nextInput = input.copy(offset = 3)
            )
        }

        test("5 - postfix match number") {
            val input = Input("---123", offset = 3)
            parse(input) shouldEqual Output(
                payload = "123",
                nextInput = input.consumed()
            )
        }

        test("6 - convert payload to Int") {
            val intParser: Parser<Int> = number().map { it.toInt() }
            val input = Input("123")
            intParser.parse(input) shouldEqual Output(
                payload = 123,
                nextInput = input.consumed()
            )
        }
    }
}
