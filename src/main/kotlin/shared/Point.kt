package shared

import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue

data class Point(val x: Int = 0, val y: Int = 0) {

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    val adjacents by lazy {
        listOf(x, x+1, x-1).flatMap { x2 ->
            listOf(y, y+1, y-1).mapNotNull { y2 ->
                val point = Point(x2, y2)
                if(point != this) point else null
            }
        }
    }

    val directAdjacents by lazy {
        listOf(above(), below(), right(), left())
    }

    fun below(i: Int = 1) = Point(x, y+i)
    fun right(i: Int = 1) = Point(x+i, y)
    fun left(i: Int = 1) = Point(x-i, y)
    fun above(i: Int = 1) = Point(x, y-i)

    fun manhattanDistance(offsetX: Int = 0, offsetY: Int = 0) = abs(x - offsetX) + abs(y - offsetY)
    fun manhattanDistance(point: Point) = manhattanDistance(point.x, point.y)

    fun navigate(char: Char, amount: Int = 1): Point {
        return when(char.uppercaseChar()) {
            'N','U','^' -> above(amount)
            'S','D','V' -> below(amount)
            'W','L','<' -> left(amount)
            'E','R','>' -> right(amount)
            else -> this
        }
    }

    fun navigateAway(char: Char, amount: Int = 1): Point {
        return when(char.uppercaseChar()) {
            'N','U','^' -> below(amount)
            'S','D','V' -> above(amount)
            'W','L','<' -> right(amount)
            'E','R','>' -> left(amount)
            else -> this
        }
    }

    fun navigate(path: String, amount: Int = 1): Point {
        var p = this
        if (Regex("[NESWUDLRV^<>]+").matches(path.uppercase())) {
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
    
    fun straightPathTo(end: Point, includeDiagonals: Boolean = true): List<Point> {
        return if (x == end.x) {
            val ends = listOf(y, end.y).sorted()
            (ends.first()..ends.last()).map { Point(x, it) }
        } else if (y == end.y) {
            val ends = listOf(x, end.x).sorted()
            (ends.first()..ends.last()).map { Point(it, y) }
        } else if(includeDiagonals && (x - end.x).absoluteValue == (y - end.y).absoluteValue) {
            (0..(x - end.x).absoluteValue).map {
                val newX = if(x < end.x) x + it else x - it
                val newY = if(y < end.y) y + it else y - it
                Point(newX, newY)
            }
        } else {
            emptyList()
        }
    }

    fun follow(leader: Point, maintainDistance: Int = 1, stepAmount: Int = 1): Point {
        val diffX = leader.x - x
        val diffY = leader.y - y
        var newX = if(diffX > maintainDistance) x+stepAmount else if(diffX < maintainDistance*-1) x-stepAmount else x
        var newY = if(diffY > maintainDistance) y+stepAmount else if(diffY < maintainDistance*-1) y-stepAmount else y
        if (diffX == maintainDistance && diffY.absoluteValue > maintainDistance) newX += stepAmount
        if (diffX == maintainDistance*-1 && diffY.absoluteValue > maintainDistance) newX -= stepAmount
        if (diffY == maintainDistance && diffX.absoluteValue > maintainDistance) newY += stepAmount
        if (diffY == maintainDistance*-1 && diffX.absoluteValue > maintainDistance) newY -= stepAmount

        return Point(newX, newY)
    }
}