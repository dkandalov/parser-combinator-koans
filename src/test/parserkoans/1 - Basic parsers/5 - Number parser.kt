package parserkoans

import org.junit.Test

/*
 * To complete this koan, implement `number()` function to return parser
 * which combines parsers from the previous koans to read a number from input,
 * where "number" is a non-negative integer.
 */

fun number(): Parser<String> = TODO()

fun <T, R> Parser<T>.map(transform: (T) -> R): Parser<R> = object : Parser<R> {
    override fun parse(input: Input): Output<R>? {
        val parser = this@map // parser on which `.map()` is defined as an extension function
        TODO()
    }
}


class `Step 5 - number parser` {
    private val parser = number()

    @Test fun `1 - match a digit`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("1")) shouldEqual Output(
            payload = "1",
            nextInput = Input("1", offset = 1)
        )
    }

    @Test fun `2 - match all digits`() {
        val allDigits = (0..9).map { it.toString() }
        allDigits.forEach { digit ->
            parser.parse(Input(digit)) shouldEqual Output(
                payload = digit,
                nextInput = Input(digit).consumed()
            )
        }
    }

    @Test fun `3 - full match number`() {
        val input = Input("123")
        parser.parse(input) shouldEqual Output(
            payload = "123",
            nextInput = input.consumed()
        )
    }

    @Test fun `4 - prefix match number`() {
        val input = Input("123---")
        parser.parse(input) shouldEqual Output(
            payload = "123",
            nextInput = input.copy(offset = 3)
        )
    }

    @Test fun `5 - postfix match number`() {
        val input = Input("---123", offset = 3)
        parser.parse(input) shouldEqual Output(
            payload = "123",
            nextInput = input.consumed()
        )
    }

    @Test fun `6 - convert payload to Int`() {
        val intParser: Parser<Int> = parser.map { it.toInt() }
        val input = Input("123")
        intParser.parse(input) shouldEqual Output(
            payload = 123,
            nextInput = input.consumed()
        )
    }
}
