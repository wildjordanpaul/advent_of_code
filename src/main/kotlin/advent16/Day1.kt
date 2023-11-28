package advent16

import shared.AdventSolution
import shared.Point
import kotlin.math.absoluteValue

class Day1 : AdventSolution(
    mapOf(
            "R2, L3" to 5,
            "R2, R2, R2" to 2,
            "R5, L5, R5, R3" to 12
    ),
    mapOf("R8, R4, R4, R8" to 4)
) {
    override fun solveProblem1(input: String): Any? {
        var position = Point(0,0)
        input.split(", ").forEach { str ->
            val direction = str.first()
            val steps = str.drop(1).trim().toInt()
            when(direction) {
                'R' -> position = position.rotate(90)
                'L' -> position = position.rotate(270)
            }
            position = position.above(steps);
        }

        return position.x.absoluteValue + position.y.absoluteValue
    }

    override fun solveProblem2(input: String): Any? {
        var position = Point(0,0)
        var visited = mutableSetOf(position)
        input.split(", ").forEach { str ->
            val direction = str.first()
            val steps = str.drop(1).trim().toInt()
            when(direction) {
                'R' -> position = position.rotate(90)
                'L' -> position = position.rotate(270)
            }
            repeat(steps) {
                position = position.above(steps);
                if (visited.contains(position))
                    return position.x.absoluteValue + position.y.absoluteValue
                visited.add(position)
            }
        }

        return null
    }
}