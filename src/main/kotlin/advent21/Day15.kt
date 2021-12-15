package advent21

import shared.AdventSolution
import shared.Point
import shared.shortestDistance
import java.util.*

class Day15 : AdventSolution(
    mapOf("""
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent() to 40),
    mapOf("""
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent() to 315),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val rows = input.split("\n")
        val cave = rows.flatMapIndexed { y, row ->
            row.mapIndexed { x, c -> Point(x, y) to c.toString().toInt() }
        }.toMap()
        return cave.minRiskLevel()
    }

    override fun solveProblem2(input: String): Any? {
        val rows = input.split("\n")
        val cave = rows.flatMapIndexed { y, row ->
            row.flatMapIndexed { x, c ->
                (0 until 5).flatMap { dupY ->
                    (0 until 5).map { dupX ->
                        var v = c.toString().toInt() + dupY + dupX
                        if(v > 9) v -= 9
                        Point(dupX * row.count() + x, dupY * rows.count() + y) to v
                    }
                }
            }
        }.toMap()
        return cave.minRiskLevel()
    }

    private data class Edge(val point: Point, val risk: Int)

    private fun Map<Point, Int>.minRiskLevel(): Int? {
        return keys.shortestDistance(
            Point(0, 0),
            Point(keys.maxOf { it.x }, keys.maxOf { it.y })
        ) { _, to -> this[to]!! }
    }
}