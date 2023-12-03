package advent23

import shared.AdventSolution
import shared.ends
import shared.toPointMap

class Day3 : AdventSolution(
    mapOf("""
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent() to 4361),
        467835
) {
    override fun solveProblem1(input: String): Any? {
        val digits = (0..9).map(Int::toString)
        val nonSymbols = digits + listOf(".")
        val points = input.toPointMap { it }
        val symbols = points.filter { !nonSymbols.contains(it.value) }.keys
        val adjacentDigits = symbols.flatMap { symbolPoint ->
            symbolPoint.adjacents.filter { digits.contains(points[it]) }
        }.map {
            var leftMost = it
            while(digits.contains(points[leftMost.left()])) {
                leftMost = leftMost.left()
            }
            leftMost
        }.toSet()
        val numbers = adjacentDigits.map { digitStart ->
            val number = mutableListOf<String>()
            var current = digitStart
            while(digits.contains(points[current])) {
                number.add(points[current]!!)
                current = current.right()
            }
            number.joinToString("").toIntOrNull() ?: 0
        }
        return numbers.sum()
    }

    override fun solveProblem2(input: String): Any? {
        val digits = (0..9).map(Int::toString)
        val points = input.toPointMap { it }
        val ratios = points.mapNotNull { (point, value) ->
            if (value == "*") {
                val numberPoints = point.adjacents.filter { digits.contains(points[it]) }.map {
                    var leftMost = it
                    while(digits.contains(points[leftMost.left()])) {
                        leftMost = leftMost.left()
                    }
                    leftMost
                }.toSet()
                if (numberPoints.size == 2) {
                    val (first, second) = numberPoints.map {
                        val number = mutableListOf<String>()
                        var current = it
                        while(digits.contains(points[current])) {
                            number.add(points[current]!!)
                            current = current.right()
                        }
                        number.joinToString("").toLongOrNull() ?: 0L
                    }.ends()
                    return@mapNotNull first * second
                }
            }
            return@mapNotNull null
        }
        return ratios.sum()
    }
}