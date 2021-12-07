package advent21

import shared.AdventSolution
import shared.max
import shared.min
import shared.splitInts
import kotlin.math.absoluteValue

class Day7 : AdventSolution(
    mapOf("""
        16,1,2,0,4,2,7,1,2,14
    """.trimIndent() to 37),
    mapOf("""
        16,1,2,0,4,2,7,1,2,14
    """.trimIndent() to 168),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val positions = input.splitInts()
        return (positions.min()..positions.max()).minOf { position ->
            positions.sumOf { (it - position).absoluteValue }
        }
    }

    override fun solveProblem2(input: String): Any? {
        val positions = input.splitInts()
        return (positions.min()..positions.max()).minOf { position ->
            positions.sumOf { fuelCost((it - position).absoluteValue) }
        }
    }

    private tailrec fun fuelCost(n: Int, prev: Int = 0): Int {
        return if (n == 0) prev
        else fuelCost(n - 1, prev + n)
    }

}