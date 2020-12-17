package advent20

import shared.AdventSolution
import shared.Point3D
import shared.Point4D

class Day17 : AdventSolution(
    mapOf("""
        .#.
        ..#
        ###
    """.trimIndent() to 112),
    mapOf("""
        .#.
        ..#
        ###
    """.trimIndent() to 848),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        var map = input.split("\n").withIndex().flatMap { (y, line) ->
            line.mapIndexed { x, char -> Point3D(x, y, 0) to (char == '#') }
        }.toMap()

        repeat(6) {
            map = map.keys
                .flatMap { it.adjacents + it }
                .toSet()
                .map { point ->
                    val activeNeighborCount = point.adjacents.count { map.getOrDefault(it, false) }
                    val active = if (map.getOrDefault(point, false)) {
                        activeNeighborCount in listOf(2, 3)
                    } else {
                        activeNeighborCount == 3
                    }
                    point to active
                }.toMap()
        }
        return map.values.count { it }
    }

    override fun solveProblem2(input: String): Any {
        var map = input.split("\n").withIndex().flatMap { (y, line) ->
            line.mapIndexed { x, char -> Point4D(x, y, 0, 0) to (char == '#') }
        }.toMap()

        repeat(6) {
            map = map.keys
                .flatMap { it.adjacents + it }
                .toSet()
                .map { point ->
                    val activeNeighborCount = point.adjacents.count { map.getOrDefault(it, false) }
                    val active = if (map.getOrDefault(point, false)) {
                        activeNeighborCount in listOf(2, 3)
                    } else {
                        activeNeighborCount == 3
                    }
                    point to active
                }.toMap()
        }
        return map.values.count { it }
    }

}