package parserkoans

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

/*
 * At this point you should have a simple parser and a way to evaluate expressions like `(1 + 2) * 3 - 4`.
 * It shouldn't be too hard to add `Divide` operation or switch from `Int` to `BigDecimal`.
 *
 * However, there are a couple fundamental problems with it:
 *  - performance - the call tree produced by the parser will have many overlaps
 *    computing output at the same input offset multiple times (you try making pass the bonus test below)
 *  - left-associative operators readability - unfortunately, because of the left recursion we can't just write
 *    `val plus = inOrder(expr, plusToken, expr)` and have to do awkward folds
 *  - operator precedence readability - well, it depends on the code you wrote,
 *    but the most intuitive approach for dealing with precedence is usually verbose
 *
 * There are solutions to the above problems.
 * Try to find them yourself... sorry :)
 *
 * I hope you enjoyed the koans! Have a good day.
 */

fun <T> Parser<T>.with(outputCache: HashMap<Pair<Parser<T>, Int>, Output<T>?>): Parser<T> =
    object : Parser<T> {
        override fun parse(input: Input): Output<T>? {
            TODO()
        }
    }

@Ignore
class `Bonus Step - parser performance` {
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
