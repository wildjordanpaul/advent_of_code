package shared

import java.math.BigInteger

data class BigPoint(val x: BigInteger = BigInteger.ZERO, val y: BigInteger = BigInteger.ZERO) {
    constructor(pair: Pair<BigInteger, BigInteger>) : this(pair.first, pair.second)

    fun manhattanDistance(offsetX: BigInteger = BigInteger.ZERO, offsetY: BigInteger = BigInteger.ZERO) = (x - offsetX).abs() + (y - offsetY).abs()
    fun manhattanDistance(point: BigPoint) = manhattanDistance(point.x, point.y)
}