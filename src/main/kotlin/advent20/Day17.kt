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
        var map = mutableMapOf<Point3D, Boolean>()

        input.split("\n").forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                map[Point3D(x, y, 0)] = char == '#'
            }
        }

        repeat(6) {
            val nextMap = mutableMapOf<Point3D, Boolean>()
            map.forEach { (point, active) ->
                val activeNeighborCount = point.adjacents.count { map.getOrDefault(it, false) }
                if (active) {
                    nextMap[point] = activeNeighborCount in listOf(2, 3)
                } else {
                    nextMap[point] = activeNeighborCount == 3
                }
                point.adjacents.filter { !nextMap.containsKey(it) }.forEach { adjacent ->
                    nextMap[adjacent] = adjacent.adjacents.count { map.getOrDefault(it, false) } == 3
                }
            }
            map = nextMap
        }
        return map.values.count { it }
    }

    override fun solveProblem2(input: String): Any {
        var map = mutableMapOf<Point4D, Boolean>()

        input.split("\n").forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                map[Point4D(x, y, 0, 0)] = char == '#'
            }
        }

        repeat(6) {
            val nextMap = mutableMapOf<Point4D, Boolean>()
            map.forEach { (point, active) ->
                val activeNeighborCount = point.adjacents.count { map.getOrDefault(it, false) }
                if (active) {
                    nextMap[point] = activeNeighborCount in listOf(2, 3)
                } else {
                    nextMap[point] = activeNeighborCount == 3
                }
                point.adjacents.filter { !nextMap.containsKey(it) }.forEach { adjacent ->
                    nextMap[adjacent] = adjacent.adjacents.count { map.getOrDefault(it, false) } == 3
                }
            }
            map = nextMap
        }
        return map.values.count { it }
    }

}