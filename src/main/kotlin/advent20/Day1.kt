package advent20

import shared.AdventSolution
import shared.splitInts

class Day1 : AdventSolution(
    mapOf("1721,979,366,299,675,1456" to 514579),
    mapOf("1721,979,366,299,675,1456" to 241861950),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val ints = input.splitInts()
        return ints.findEntryMultiplier(2020)
    }

    override fun solveProblem2(input: String): Any {
        val ints = input.splitInts()
        ints.forEachIndexed { index1, n1 ->
            val multiplier = ints.findEntryMultiplier(2020 - n1, ignore = index1)
            if (multiplier != null) return multiplier * n1
        }
        throw RuntimeException("No Matches Found!")
    }

    private fun List<Int>.findEntryMultiplier(
        addingUpTo: Int = 2020,
        ignore: Int = -1
    ) : Int? {
        forEachIndexed { i, n ->
            val remainder = addingUpTo - n
            val matchIndex = indexOf(remainder)
            if(matchIndex !in listOf(-1, ignore, i))
                return n * remainder
        }
        return null
    }
}