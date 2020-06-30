@file:Suppress("PackageDirectoryMismatch")

package parserkoans


sealed class ASTNode
data class IntLiteral(val value: Int) : ASTNode()
data class Plus(val left: ASTNode, val right: ASTNode) : ASTNode()
data class Minus(val left: ASTNode, val right: ASTNode) : ASTNode()
data class Multiply(val left: ASTNode, val right: ASTNode) : ASTNode()

fun ASTNode?.toStringExpression(): String =
    when (this) {
        is IntLiteral -> value.toString()
        is Plus -> "(${left.toStringExpression()} + ${right.toStringExpression()})"
        is Minus -> "(${left.toStringExpression()} - ${right.toStringExpression()})"
        is Multiply -> "(${left.toStringExpression()} * ${right.toStringExpression()})"
        null -> "null"
    }


