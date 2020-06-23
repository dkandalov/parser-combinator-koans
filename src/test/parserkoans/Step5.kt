package parserkoans

import org.junit.Test
import parserkoans.util.shouldEqual

fun number(): Parser<String> =
    oneOrMore(oneOf(*(0..9).map { string(it.toString()) }.toTypedArray()))
        .map { it.joinToString("") }

fun <T, R> Parser<T>.map(transform: (T) -> R): Parser<R> = object : Parser<R> {
    override fun parse(input: Input): Output<R>? {
        val parser = this@map
        val output = parser.parse(input) ?: return null
        return Output(transform(output.payload), output.nextInput)
    }
}


class `Step 5 - number parser` {
    private val parser = number()

    @Test fun `1 - no match`() {
        parser.parse(Input("")) shouldEqual null
        parser.parse(Input("foo")) shouldEqual null
        parser.parse(Input("foo123")) shouldEqual null
    }

    @Test fun `2 - match digits`() {
        (0..9).map { it.toString() }
            .forEach { digit ->
                parser.parse(Input(digit)) shouldEqual Output(digit, nextInput = Input(digit).consumed())
            }
    }

    @Test fun `3 - full match`() {
        val input = Input("123")
        parser.parse(input) shouldEqual
            Output("123", nextInput = input.consumed())
    }

    @Test fun `4 - prefix match`() {
        val input = Input("123---")
        parser.parse(input) shouldEqual
            Output("123", nextInput = input.copy(offset = 3))
    }

    @Test fun `5 - postfix match`() {
        val input = Input("---123", offset = 3)
        parser.parse(input) shouldEqual
            Output("123", nextInput = input.consumed())
    }

    @Test fun `6 - convert parser payload to Int`() {
        val input = Input("123")
        parser.map { it.toInt() }.parse(input) shouldEqual
            Output(123, nextInput = input.consumed())
    }
}
