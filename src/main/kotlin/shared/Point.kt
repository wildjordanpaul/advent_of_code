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

    fun navigate(char: Char): Point {
        return when(char.toUpperCase()) {
            'N' -> above()
            'S' -> below()
            'W' -> left()
            'E' -> right()
            else -> this
        }
    }
}