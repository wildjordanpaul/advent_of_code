package advent22

import shared.AdventSolution
import shared.splitInts

class Day1 : AdventSolution(
    mapOf("""
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent() to 24000),
    mapOf("""
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent() to 45000),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return input.split(Regex("\n\n"))
            .map(::toElf)
            .maxOf(Elf::totalCalories)
    }

    override fun solveProblem2(input: String): Any? {
        return input.split(Regex("\n\n"))
            .map(::toElf)
            .map(Elf::totalCalories)
            .sorted()
            .takeLast(3)
            .sum()
    }

    data class Elf(val food: List<Int>) {
        val totalCalories = food.sum()
    }

    private fun toElf(input: String) = Elf(input.splitInts())
}