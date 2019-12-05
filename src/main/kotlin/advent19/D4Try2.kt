package advent19

import shared.AdventSolution

class D4Try2 : AdventSolution(
    mapOf(),
    mapOf(),
    "382345-843167"
) {
    override fun solveProblem1(input: String): Any? {
        return countPossiblePasswords(input)
    }

    override fun solveProblem2(input: String): Any? {
        return countPossiblePasswords(input, true)
    }

    private fun countPossiblePasswords(input: String, pairsOnly: Boolean = false): Int {
        val numbers = input.split('-').map(String::toInt)
        return (numbers.first()..numbers.last()).count { i ->
            val ca = i.toString().toCharArray()
            ca.sorted() == ca.toList() && ca.hasAdjacentDuplication(pairsOnly)
        }
    }

    private fun CharArray.hasAdjacentDuplication(pairsOnly: Boolean = false): Boolean {
        return groupBy { it }.values.any { if(pairsOnly) it.size == 2 else it.size > 1 }
    }

}
