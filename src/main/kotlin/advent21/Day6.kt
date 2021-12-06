package advent21

import shared.AdventSolution
import shared.splitInts
import java.math.BigInteger

class Day6 : AdventSolution(
    mapOf("""
        3,4,3,1,2
    """.trimIndent() to 5934),
    mapOf("""
        3,4,3,1,2
    """.trimIndent() to 26984457539),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return countFish(input, 80)
    }

    override fun solveProblem2(input: String): Any? {
        return countFish(input, 256)
    }

    private fun countFish(input: String, days: Int): BigInteger {
        val initialState = input.splitInts()
        var fishCounts = (0..8).associateWith { initialState.count { s -> s == it }.toBigInteger() }
        (1..days).forEach { _ ->
            fishCounts = (0..8).associateWith {
                when(it) {
                    8 -> fishCounts[0]!!
                    6 -> fishCounts[0]!!.add(fishCounts[7])
                    else -> fishCounts[it+1]!!
                }
            }
        }
        return fishCounts.values.fold(BigInteger.ZERO) { acc, next -> acc.add(next) }
    }
}