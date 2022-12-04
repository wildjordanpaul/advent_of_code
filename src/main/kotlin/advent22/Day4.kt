package advent22

import shared.*

class Day4 : AdventSolution(
    mapOf("""
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent() to 2),
    4
) {
    override fun solveProblem1(input: String): Any? {
        return input.split("\n").count { line ->
            val (e1, e2) = line.splitInTwo(",", String::toRange)
            e1.all { e2.contains(it) } || e2.all { e1.contains(it) }
        }
    }

    override fun solveProblem2(input: String): Any? {
        return input.split("\n").count { line ->
            val (e1, e2) = line.splitInTwo(",", String::toRange)
            e1.intersect(e2).isNotEmpty()
        }
    }
}