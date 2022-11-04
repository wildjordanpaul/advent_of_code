package advent20

import shared.AdventSolution
import shared.Point

class Day12 : AdventSolution(
    mapOf("""
        F10
        N3
        F7
        R90
        F11
    """.trimIndent() to 25),
    mapOf("""
        F10
        N3
        F7
        R90
        F11
    """.trimIndent() to 286),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        var facing = 1
        var point = Point(0,0)

        input.split("\n").forEach { instruction ->
            val (action, value) = """(.)(\d+)""".toRegex().find(instruction)!!.destructured
            when(action) {
                "N", "S", "E", "W" ->
                   point = point.navigate(action, value.toInt())
                "L" ->
                    facing = Math.floorMod(facing - value.toInt()/90, 4)
                "R" ->
                    facing = Math.floorMod(facing + value.toInt()/90, 4)
                "F" -> {
                    point = point.navigate(facing.toDirection(), value.toInt())
                }
            }
        }
        return point.manhattanDistance()
    }

    override fun solveProblem2(input: String): Any {
        var point = Point(0,0)
        var waypoint = Point(10,-1)

        input.split("\n").forEach { instruction ->
            val (action, value) = """(.)(\d+)""".toRegex().find(instruction)!!.destructured
            when(action) {
                "N", "S", "E", "W" ->
                    waypoint = waypoint.navigate(action, value.toInt())
                "L" ->
                    waypoint = waypoint.rotate(-1 * value.toInt())
                "R" ->
                    waypoint = waypoint.rotate(value.toInt())
                "F" -> {
                    point = Point(point.x + waypoint.x * value.toInt(), point.y + waypoint.y * value.toInt())
                }
            }
        }
        return point.manhattanDistance()
    }

    private fun Int.toDirection(): Char {
        return when (this) {
            0 -> 'N'
            1 -> 'E'
            2 -> 'S'
            3 -> 'W'
            else -> throw IllegalArgumentException("Invalid Direction: $this")
        }
    }

}