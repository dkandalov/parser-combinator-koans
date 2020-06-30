package parserkoans

import org.junit.Before
import org.junit.Test

fun <T> Parser<T>.with(outputCache: HashMap<Pair<Parser<T>, Int>, Output<T>?>): Parser<T> =
    object : Parser<T> {
        override fun parse(input: Input): Output<T>? {
            TODO()
        }
    }

class `Step 12 - parser performance` {
    private val logEvents = ArrayList<ParsingEvent>()
    private val log = ParsingLog { logEvents.add(it) }
    private val outputCache = HashMap<Pair<Parser<ASTNode>, Int>, Output<ASTNode>?>()

    private val number =
        number().map { IntLiteral(it.toInt()) }
            .with("int", log)
            .with(outputCache)

    private val plus =
        inOrder(ref { expression1 }, onceOrMore(inOrder(string(" + "), ref { expression1 })))
            .map { (first, rest) ->
                rest.fold(first) { left, (_, right) -> Plus(left, right) }
            }
            .with("plus", log)
            .with(outputCache)

    private val multiply =
        inOrder(ref { number }, onceOrMore(inOrder(string(" * "), ref { number })))
            .map { (first, rest) ->
                rest.fold(first) { left, (_, right) -> Multiply(left, right) }
            }
            .with("multiply", log)
            .with(outputCache)

    private val expression1 = oneOf(multiply, number)
    private val expression: Parser<ASTNode> = oneOf(plus, multiply, number)

    @Before fun setup() {
        logEvents.clear()
    }

    @Test fun `1 - number`() {
        expression.parse(Input("123"))?.payload shouldEqual IntLiteral(123)

        logEvents.joinToString("\n") { it.toDebugString() } shouldEqual """
            "123" plus:0
            "123" plus:0 multiply:0
            "123" plus:0 multiply:0 int:0
            "123" plus:0 multiply:0 int:0 -- 123
            "123" plus:0 multiply:0 -- no match
            "123" plus:0 -- no match
        """.trimIndent()
    }

    @Test fun `2 - plus`() {
        expression.parse(Input("1 + 2"))?.payload shouldEqual Plus(IntLiteral(1), IntLiteral(2))

        logEvents.joinToString("\n") { it.toDebugString() } shouldEqual """
            "1 + 2" plus:0
            "1 + 2" plus:0 multiply:0
            "1 + 2" plus:0 multiply:0 int:0
            "1 + 2" plus:0 multiply:0 int:0 -- 1
            "1 + 2" plus:0 multiply:0 -- no match
            "1 + 2" plus:0 multiply:4
            "1 + 2" plus:0 multiply:4 int:4
            "1 + 2" plus:0 multiply:4 int:4 -- 2
            "1 + 2" plus:0 multiply:4 -- no match
            "1 + 2" plus:0 -- 1 + 2
        """.trimIndent()
    }
}
