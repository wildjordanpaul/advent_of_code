package advent20

import shared.AdventSolution

class Day6 : AdventSolution(
    mapOf("""
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
    """.trimIndent() to 11),
    mapOf("""
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
    """.trimIndent() to 6),
    input1 = loadInput("/advent20/D6_input1.txt")
) {

    override fun solveProblem1(input: String): Any {
        return input.split("\n\n").sumBy { group ->
            group.split("\n")
                .flatMap{ it.toCharArray().toList() }
                .toSet()
                .size
        }
    }

    override fun solveProblem2(input: String): Any {
        return input.split("\n\n").sumBy { group ->
            val ppl = group.split("\n").map{ it.toCharArray().toList() }
            val charCount = mutableMapOf<Char, Int>()
            ppl.forEach { answers ->
                answers.forEach { char ->
                    val n = charCount[char] ?: 0
                    charCount[char] = n + 1
                }
            }
            charCount.entries.count { it.value == ppl.size }
        }
    }

}