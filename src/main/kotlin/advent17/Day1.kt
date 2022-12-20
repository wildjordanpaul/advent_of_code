package advent17

import shared.AdventSolution

class Day1 : AdventSolution(
    mapOf("""
        91212129
    """.trimIndent() to 9),
    mapOf("12131415" to 4),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val s = (input + input.first()).map { it.toString() }.filter(String::isNotBlank).map(String::toInt)
        var last = s.first()
        var acc = 0
        s.drop(1).forEach {
            if(last == it) acc += it
            last = it
        }
        return acc
    }

    override fun solveProblem2(input: String): Any? {
        val s = (input).map { it.toString() }.filter(String::isNotBlank).map(String::toInt)
        val steps = input.length / 2
        var acc = 0
        s.forEachIndexed { i, next ->
            if(next == s[(i + steps) % s.size]) acc += next
        }
        return acc
    }
}