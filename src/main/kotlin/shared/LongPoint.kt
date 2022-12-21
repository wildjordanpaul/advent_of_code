package shared

import kotlin.math.abs

data class LongPoint(val x: Long = 0, val y: Long = 0) {
    constructor(pair: Pair<Long, Long>) : this(pair.first, pair.second)

    fun below(i: Long = 1) = LongPoint(x, y+i)
    fun right(i: Long = 1) = LongPoint(x+i, y)
    fun left(i: Long = 1) = LongPoint(x-i, y)
    fun above(i: Long = 1) = LongPoint(x, y-i)

    fun navigate(char: Char, amount: Long = 1): LongPoint {
        return when(char.uppercaseChar()) {
            'N','U' -> above(amount)
            'S','D' -> below(amount)
            'W','L','<' -> left(amount)
            'E','R','>' -> right(amount)
            else -> this
        }
    }

    fun navigate(path: String, amount: Long = 1): LongPoint {
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

    fun manhattanDistance(offsetX: Long = 0, offsetY: Long = 0) = abs(x - offsetX) + abs(y - offsetY)
    fun manhattanDistance(point: LongPoint) = manhattanDistance(point.x, point.y)
}