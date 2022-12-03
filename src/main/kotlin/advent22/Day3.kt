package advent22

import shared.AdventSolution
import shared.splitAt
import shared.mapBoth
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
            val (r1, r2) = line.splitAt(line.length/2).mapBoth(String::toSet)
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