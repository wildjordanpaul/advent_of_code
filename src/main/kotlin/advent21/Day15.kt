package advent21

import shared.AdventSolution
import shared.Point
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
        val visited = mutableSetOf<Point>()
        val riskLevels = mutableMapOf(Point(0, 0) to 0)
        val maxX = maxOf { it.key.x }
        val maxY = maxOf { it.key.y }
        val queue = PriorityQueue<Edge>(compareBy { it.risk })
        queue.add(Edge(Point(0, 0), 0))

        while(queue.isNotEmpty()) {
            val current = queue.poll()
            current.point.directAdjacents.filter { contains(it) && !visited.contains(it) }.forEach { neighbor ->
                val neighborRisk = riskLevels[neighbor]
                val newRisk = current.risk + this[neighbor]!!
                if(neighborRisk == null || newRisk < neighborRisk) {
                    riskLevels[neighbor] = newRisk
                    queue.add(Edge(neighbor, newRisk))
                }
            }
        }

        return riskLevels[Point(maxX, maxY)]
    }
}