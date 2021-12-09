package advent21

import shared.AdventSolution
import shared.Point

class Day9 : AdventSolution(
    mapOf("""
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent() to 15),
    mapOf("""
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent() to 1134),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val depthMap = input.toDepthMap()
        return depthMap.findLowPoints().map { it.value + 1 }.sum()
    }

    override fun solveProblem2(input: String): Any? {
        val depthMap = input.toDepthMap()
        val lowPoints = depthMap.findLowPoints()
        return lowPoints.map { (lowPoint, _) ->
            val basin = mutableSetOf<Point>()
            var pointsToAdd = setOf(lowPoint)
            while(pointsToAdd.isNotEmpty()) {
                basin.addAll(pointsToAdd)
                pointsToAdd = basin.flatMap { p ->
                    p.directAdjacents.filter {
                        !basin.contains(it) && depthMap.getOrDefault(it, 9) != 9
                    }
                }.toSet()
            }
            basin.count()
        }.sorted().takeLast(3).fold(1) { acc, i -> acc * i }
    }

    private fun String.toDepthMap(): Map<Point, Int> {
        return split(Regex("\n")).flatMapIndexed { row, line ->
            line.mapIndexed { col, c ->
                Point(row, col) to c.toString().toInt()
            }
        }.toMap()
    }

    private fun Map<Point, Int>.findLowPoints(): Map<Point, Int> {
        return filter { (point, height) ->
            point.directAdjacents.all { getOrDefault(it, 9) > height }
        }
    }
}