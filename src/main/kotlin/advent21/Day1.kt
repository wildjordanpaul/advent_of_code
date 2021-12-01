package advent21

import shared.AdventSolution
import shared.splitInts

class Day1 : AdventSolution(
    mapOf("""199
        200
        208
        210
        200
        207
        240
        269
        260
        263
    """.trimIndent() to 7),
    mapOf("""199
        200
        208
        210
        200
        207
        240
        269
        260
        263
    """.trimIndent() to 5),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any {
        val depths = input.splitInts()
        return depths.filterIndexed { i, depth ->
            i > 0 && depth > depths[i-1]
        }.count()
    }

    override fun solveProblem2(input: String): Any {
        val depths = input.splitInts()
        return depths.filterIndexed { i, depth ->
            i > 2 && depth > depths[i-3]
        }.count()
    }
}