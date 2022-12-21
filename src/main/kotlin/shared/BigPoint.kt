package shared

import java.math.BigInteger

data class BigPoint(val x: BigInteger = BigInteger.ZERO, val y: BigInteger = BigInteger.ZERO) {
    constructor(pair: Pair<BigInteger, BigInteger>) : this(pair.first, pair.second)
    constructor(iX: Int, iY: Int) : this(iX.toBigInteger(), iY.toBigInteger())

    fun below(i: BigInteger = BigInteger.ONE) = BigPoint(x, y+i)
    fun right(i: BigInteger = BigInteger.ONE) = BigPoint(x+i, y)
    fun left(i: BigInteger = BigInteger.ONE) = BigPoint(x-i, y)
    fun above(i: BigInteger = BigInteger.ONE) = BigPoint(x, y-i)

    fun below(i: Int) = below(i.toBigInteger())
    fun right(i: Int) = right(i.toBigInteger())
    fun left(i: Int) = left(i.toBigInteger())
    fun above(i: Int) = above(i.toBigInteger())

    fun navigate(char: Char, amount: BigInteger = BigInteger.ONE): BigPoint {
        return when(char.uppercaseChar()) {
            'N','U' -> above(amount)
            'S','D' -> below(amount)
            'W','L','<' -> left(amount)
            'E','R','>' -> right(amount)
            else -> this
        }
    }

    fun navigate(char: Char, amount: Int) = navigate(char, amount.toBigInteger())

    fun navigate(path: String, amount: Int = 1): BigPoint {
        var p = this
        if (Regex("[NESWUDLR]+").matches(path.uppercase())) {
            path.forEach { p = p.navigate(it, amount) }
        } else {
            p = when(path.lowercase()) {
                "down", "below" -> p.below(amount)
                "up", "above" -> p.above(amount)
                "forward", "right" -> p.right(amount)
                "back", "backward", "left" -> p.left(amount)
                else -> p
            }
        }
        return p
    }

    fun manhattanDistance(offsetX: BigInteger = BigInteger.ZERO, offsetY: BigInteger = BigInteger.ZERO) = (x - offsetX).abs() + (y - offsetY).abs()
    fun manhattanDistance(point: BigPoint) = manhattanDistance(point.x, point.y)
}