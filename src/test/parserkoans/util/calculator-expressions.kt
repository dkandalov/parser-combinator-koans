@file:Suppress("PackageDirectoryMismatch")

package parserkoans


interface Expression

data class IntLiteral(val value: Int) : Expression
data class Plus(val left: Expression, val right: Expression) : Expression
data class Minus(val left: Expression, val right: Expression) : Expression
data class Multiply(val left: Expression, val right: Expression) : Expression

fun Expression?.evaluate(): Int =
    when (this) {
        is IntLiteral -> value
        is Plus -> left.evaluate() + right.evaluate()
        is Minus -> left.evaluate() - right.evaluate()
        is Multiply -> left.evaluate() * right.evaluate()
        else -> error("Expected '$this' to be an Expression")
    }

fun Expression?.toStringExpression(): String =
    when (this) {
        is IntLiteral -> value.toString()
        is Plus -> "(${left.toStringExpression()} + ${right.toStringExpression()})"
        is Minus -> "(${left.toStringExpression()} - ${right.toStringExpression()})"
        is Multiply -> "(${left.toStringExpression()} * ${right.toStringExpression()})"
        else -> error("Expected '$this' to be an Expression")
    }


