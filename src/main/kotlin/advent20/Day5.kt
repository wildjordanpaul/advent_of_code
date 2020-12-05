package advent20

import shared.AdventSolution
import shared.splitInTwo
import kotlin.math.max

class Day5 : AdventSolution(
    mapOf(
        """
            BFFFBBFRRR
            FFFBBBFRRR
            BBFFBBFRLL
        """.trimIndent() to 820
    ),
    mapOf(),
    input1 = loadInput("/advent20/D5_input1.txt")
) {

    override fun solveProblem1(input: String): Any? {
        return input.toSeatNumbers().maxOrNull()
    }

    override fun solveProblem2(input: String): Any {
        val seatNumbers = input.toSeatNumbers()
        val maxSeatNumber = seatNumbers.maxOrNull()!!
        return ((0..maxSeatNumber) - seatNumbers).last()
    }

    private fun String.toSeatNumbers() = split("\n").map { it.toSeatNumber() }

    private fun String.toSeatNumber(): Int {
        return replace("[BR]".toRegex(), "1")
            .replace("[FL]".toRegex(), "0")
            .toInt(2)
    }
}