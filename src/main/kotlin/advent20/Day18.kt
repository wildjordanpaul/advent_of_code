package advent20

import shared.AdventSolution
import shared.Point3D
import shared.Point4D

class Day18 : AdventSolution(
    mapOf(
        "1 + 2 * 3 + 4 * 5 + 6" to 71,
        "1 + (2 * 3) + (4 * (5 + 6))" to 51,
        "2 * 3 + (4 * 5)" to 26,
        "5 + (8 * 3 + 9 + 3 * 4 * 3)" to 437,
        "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))" to 12240,
        "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" to 13632
    ),
    mapOf(
        "1 + 2 * 3 + 4 * 5 + 6" to 231,
        "1 + (2 * 3) + (4 * (5 + 6))" to 51,
        "2 * 3 + (4 * 5)" to 46,
        "5 + (8 * 3 + 9 + 3 * 4 * 3)" to 1445,
        "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))" to 669060,
        "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" to 23340
    ),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        return input.split("\n").map { line ->
            line.nestedReplace("""\(([^()]+)\)""".toRegex()) {
                val (sub) = it.destructured
                sub.evalLTR().toString()
            }.evalLTR()
        }.sum()
    }

    override fun solveProblem2(input: String): Any {
        return input.split("\n").map { line ->
            line.nestedReplace("\\(([^()]+)\\)".toRegex()) {
                val (sub) = it.destructured
                sub.evalPlusThenMultiply().toString()
            }.evalPlusThenMultiply()
        }.sum()
    }

    private fun String.evalLTR(): Long {
        var total = 0L
        var lastOp: Boolean? = null
        split(" ").forEachIndexed { i, s ->
            when {
                i == 0 -> total = s.toLong()
                s == "*" -> lastOp = true
                s == "+" -> lastOp = false
                lastOp == true -> total *= s.toInt()
                lastOp == false -> total += s.toInt()
            }
        }
        return total
    }

    private fun String.evalPlusThenMultiply(): Long {
        return nestedReplace("""(\d+) \+ (\d+)""".toRegex()) {
            val (f,s) = it.destructured
            (f.toLong() + s.toLong()).toString()
        }.nestedReplace("""(\d+) \* (\d+)""".toRegex()) {
            val (f,s) = it.destructured
            (f.toLong() * s.toLong()).toString()
        }.toLong()
    }

    private fun String.nestedReplace(regex: Regex, transform: (MatchResult) -> CharSequence): String {
        var current = this
        do {
            val next = current.replace(regex, transform)
            val changed = next != current
            current = next
        } while(changed)
        return current
    }

}