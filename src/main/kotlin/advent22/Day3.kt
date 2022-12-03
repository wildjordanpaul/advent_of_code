package advent22

import shared.AdventSolution
import shared.splitLines

class Day3 : AdventSolution(
    mapOf("""
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent() to 157),
    70
) {
    override fun solveProblem1(input: String): Any? {
        return input.splitLines().sumOf {line ->
            val r1 = line.substring(0, line.length/2).toSet()
            val r2 = line.substring(line.length/2, line.length).toSet()
            r1.intersect(r2).first().priority()
        }
    }

    override fun solveProblem2(input: String): Any? {
        return input.splitLines()
            .map(String::toSet)
            .chunked(3)
            .sumOf { lines ->
                lines.reduce { acc, n -> acc.intersect(n) }.first().priority()
            }
    }

    private fun Char.priority(): Int {
        return if (this < 'a')
            this - 'A' + 27
        else
            this - 'a' + 1
    }
}