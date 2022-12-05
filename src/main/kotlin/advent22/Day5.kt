package advent22

import shared.AdventSolution
import shared.splitInTwo

class Day5 : AdventSolution(
    mapOf("""
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent() to "CMZ"),
    "MCD",
    trimInput = false
) {
    override fun solveProblem1(input: String): Any? {
        val (cratesRaw, instructionsRaw) = input.splitInTwo("\n\n")
        val crates = cratesRaw.toCrates()
        instructionsRaw.split("\n").filter(String::isNotBlank).forEach { line ->
            val ints = line.split(Regex("[a-z ]")).filter(String::isNotBlank).map { it.toInt() }
            val from = crates[ints[1] - 1]
            val to = crates[ints[2] - 1]
            repeat(ints[0]) {
                to.add(from.removeLast())
            }
        }
        return crates.map{ it.last() }.joinToString("")
    }

    override fun solveProblem2(input: String): Any? {
        val (cratesRaw, instructionsRaw) = input.splitInTwo("\n\n")
        val crates = cratesRaw.toCrates()
        instructionsRaw.split("\n").filter(String::isNotBlank).forEach { line ->
            val ints = line.split(Regex("[a-z ]")).filter(String::isNotBlank).map { it.toInt() }
            val from = crates[ints[1] - 1]
            val to = crates[ints[2] - 1]
            (0 until ints[0]).map { from.removeLast() }.reversed().forEach { to.add(it) }
        }
        return crates.map{ it.last() }.joinToString("")
    }
}

private fun String.toCrates(): List<ArrayDeque<Char>> {
    val lines = split("\n")
    val numbers = lines.last()
    val letters = lines.dropLast(1).reversed()
    val stacks = numbers.trim().last().toString().toInt()
    return (0 until stacks).map {
        val q = ArrayDeque<Char>()
        val i = numbers.indexOf((it+1).toString())
        letters.forEach { letterLine ->
            val letter = letterLine[i]
            if (letter.isLetter()) {
                q.add(letter)
            }
        }
        q
    }
}
