package shared

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    val adjacents by lazy {
        listOf(x, x+1, x-1).flatMap { x2 ->
            listOf(y, y+1, y-1).mapNotNull { y2 ->
                val point = Point(x2, y2)
                if(point != this) point else null
            }
        }
    }

    fun below(i: Int = 1) = Point(x, y+i)
    fun right(i: Int = 1) = Point(x+i, y)
    fun left(i: Int = 1) = Point(x-i, y)
    fun above(i: Int = 1) = Point(x, y-i)

    fun manhattanDistance(offsetX: Int = 0, offsetY: Int = 0) = abs(x - offsetX) + abs(y - offsetY)

    fun navigate(char: Char, amount: Int = 1): Point {
        return when(char.uppercaseChar()) {
            'N','U' -> above(amount)
            'S','D' -> below(amount)
            'W','L' -> left(amount)
            'E','R' -> right(amount)
            else -> this
        }
    }

    fun navigate(path: String, amount: Int = 1): Point {
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

    fun add(x: Int, y: Int): Point {
        return copy(x = this.x + x, y = this.y + y)
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