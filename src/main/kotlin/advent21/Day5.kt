package advent21

import shared.*
import kotlin.math.absoluteValue

class Day5 : AdventSolution(
    mapOf("""
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent() to 5),
    mapOf("""
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent() to 12),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return countOverlaps(input,false)
    }

    override fun solveProblem2(input: String): Any? {
        return countOverlaps(input, true)
    }

    private fun countOverlaps(input: String, includeDiagonals: Boolean): Int {
        val data = input.split(Regex("\n"))
        val points = data.flatMap { it.buildPath(includeDiagonals) }
        return points.groupBy { it }.count { it.value.count() > 1 }
    }

    private fun String.buildPath(includeDiagonals: Boolean = false): List<Point> {
        val (start, end) = splitInTwo(" -> ") { points ->
            Point(points.splitInTwo(",") { it.toInt() })
        }
        return if (start.x == end.x) {
            val ends = listOf(start.y, end.y).sorted()
            (ends.first()..ends.last()).map { Point(start.x, it) }
        } else if (start.y == end.y) {
            val ends = listOf(start.x, end.x).sorted()
            (ends.first()..ends.last()).map { Point(it, start.y) }
        } else if(includeDiagonals && (start.x - end.x).absoluteValue == (start.y - end.y).absoluteValue) {
            (0..(start.x - end.x).absoluteValue).map {
                val newX = if(start.x < end.x) start.x + it else start.x - it
                val newY = if(start.y < end.y) start.y + it else start.y - it
                Point(newX, newY)
            }
        } else {
            emptyList()
        }
    }
}