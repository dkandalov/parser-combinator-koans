package parserkoans

interface Parser<out T> {
    fun parse(input: Input): Output<T>?
}

data class Input(val value: String, val offset: Int = 0) {
    val unprocessed = value.substring(offset)
    fun consumed() = copy(offset = value.length)
}

data class Output<out T>(val payload: T, val nextInput: Input)
