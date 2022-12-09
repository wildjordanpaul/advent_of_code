package advent22

import shared.AdventSolution
import shared.Point
import shared.splitInTwo
import shared.splitLines
import kotlin.math.absoluteValue

class Day9 : AdventSolution(
    mapOf("""
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent() to 13),
    mapOf("""
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent() to 1,
        """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent() to 36,
    ),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return countTailVisits(input, 2)
    }

    override fun solveProblem2(input: String): Any? {
        return countTailVisits(input, 10)
    }

    private fun countTailVisits(directions: String, ropeLength: Int = 2): Int {
        var rope = (0 until ropeLength).map { Point(0,0) }
        val visited = mutableSetOf(rope.last())
        directions.splitLines().forEach {
            val (dir, amount) = it.splitInTwo(" ")
            repeat(amount.toInt()) {
                val leader = rope.first().navigate(dir)
                rope = rope.drop(1).runningFold(leader) { last, next ->
                    next.follow(last)
                }
                visited.add(rope.last())
            }
        }
        return visited.size
    }
}