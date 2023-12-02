package advent23

import shared.AdventSolution
import shared.ends
import shared.splitLines

class Day1 : AdventSolution(
    mapOf("""
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent() to 142),
    mapOf("""
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent() to 281)
) {
    override fun solveProblem1(input: String): Any? {
        return input.splitLines().sumOf {
            val (first, last) = it.toCharArray().filter(Char::isDigit).ends()
            "$first$last".toInt()
        }
    }

    override fun solveProblem2(input: String): Any? {
        val results = input.splitLines().map {
            val (first, last) = listOfNotNull(
                    input.findAnyOf(DIGIT_WORDS + DIGIT_NUMBERS),
                    input.findLastAnyOf(DIGIT_WORDS + DIGIT_NUMBERS)
            ).map {
                val i = DIGIT_WORDS.indexOf(it.second)
                if (i >= 0) i + 1
                else it.second.toInt()
            }.ends()
            "$first$last".toInt()
        }
        return results.sum()
    }

    companion object {
        val DIGIT_WORDS = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
        val DIGIT_NUMBERS = (1..9).map { it.toString() }
    }
}