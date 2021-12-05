package advent21

import shared.AdventSolution
import shared.Point
import shared.splitInTwo

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
        return start.straightPathTo(end, includeDiagonals)
    }
}