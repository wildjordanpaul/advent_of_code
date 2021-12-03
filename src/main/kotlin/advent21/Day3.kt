package advent21

import shared.*

class Day3 : AdventSolution(
    mapOf("""
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent() to 198),
    mapOf("""
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent() to 230),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val lines = input.split(Regex("\n"))
        var gamma = ""
        var epsilon = ""
        (0 until lines.first().length).forEach { i ->
            val c = lines.maxAt(i)
            gamma += c
            epsilon += if(c == '1') '0' else '1'
        }
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    override fun solveProblem2(input: String): Any? {
        val lines = input.split(Regex("\n"))
        var o2numbers = lines
        var co2numbers = lines
        (0 until lines.first().length).forEach { i ->
            if (o2numbers.count() > 1) {
                val max = o2numbers.maxAt(i)
                o2numbers = o2numbers.filter { it[i] == max }
            }
            if (co2numbers.count() > 1) {
                val max = co2numbers.maxAt(i)
                co2numbers = co2numbers.filter { it[i] != max }
            }
        }
        return o2numbers.first().toInt(2) * co2numbers.first().toInt(2)
    }

    private fun List<String>.maxAt(i: Int)= map{ it[i] }.sorted()[count().div(2)]
}