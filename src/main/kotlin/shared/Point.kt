package shared

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    val adjacents by lazy { listOf(
        Point(x+1, y),
        Point(x-1, y),
        Point(x+1, y+1),
        Point(x-1, y+1),
        Point(x+1, y-1),
        Point(x-1, y-1),
        Point(x, y+1),
        Point(x, y-1)
    ) }

    fun below(i: Int = 1) = Point(x, y+i)
    fun right(i: Int = 1) = Point(x+i, y)
    fun left(i: Int = 1) = Point(x-i, y)
    fun above(i: Int = 1) = Point(x, y-i)

    fun manhattanDistance(offsetX: Int = 0, offsetY: Int = 0) = abs(x - offsetX) + abs(y - offsetY)

    fun navigate(char: Char, amount: Int = 1): Point {
        return when(char.toUpperCase()) {
            'N','U' -> above(amount)
            'S','D' -> below(amount)
            'W','L' -> left(amount)
            'E','R' -> right(amount)
            else -> this
        }
    }

    fun navigate(path: String, amount: Int = 1): Point {
        var p = this
        path.forEach { p = p.navigate(it, amount) }
        return p
    }

    fun rotate(degrees: Int): Point {
        return when(Math.floorMod(degrees, 360)) {
            90 -> Point(y * -1, x)
            180 -> Point(x * -1, y * -1)
            270 -> Point(y, x * -1)
            0 -> this
            else -> throw IllegalArgumentException("Invalid rotation: $degrees")
        }
    }
}