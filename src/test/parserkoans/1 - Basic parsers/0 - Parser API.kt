package parserkoans

/**
 * In software, [parser](https://en.wikipedia.org/wiki/Parsing#Computer_languages) is a component which converts text
 * into a more structured data format (usually [abstract syntax tree](https://en.wikipedia.org/wiki/Abstract_syntax_tree)).
 * Therefore, it's reasonable to think about parser being a function which takes input as plain text
 * and produces some kind of structured output.
 */

interface Parser<out T> {
    fun parse(input: Input): Output<T>?
}

data class Input(val value: String, val offset: Int = 0) {
    val unprocessed = value.substring(offset)
    fun consumed() = copy(offset = value.length)
}

data class Output<out T>(val payload: T, val nextInput: Input)
