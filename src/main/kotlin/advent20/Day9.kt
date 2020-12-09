package advent20

import shared.AdventSolution

class Day9 : AdventSolution(
    mapOf("""
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
    """.trimIndent() to 127),
    mapOf("""
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
    """.trimIndent() to 62),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        val numbers = input.split("\n").map(String::toLong)
        return numbers.findInvalidNumber()
    }

    override fun solveProblem2(input: String): Any {
        val numbers = input.split("\n").map(String::toLong)
        val invalidNumber = numbers.findInvalidNumber()
        numbers.indices.forEach { i ->
            var runningCount = 0L
            var i2 = 0
            while(runningCount < invalidNumber) {
                runningCount += numbers[i + i2]
                i2 += 1
            }
            if (i2 > 1 && runningCount == invalidNumber) {
                val ints2 = numbers.subList(i, i+i2)
                return ints2.minOrNull()!! + ints2.maxOrNull()!!
            }
        }
        return -1
    }

    private fun List<Long>.findInvalidNumber(): Long {
        val preambleCount = if(size < 30) 5 else 25
        forEachIndexed { i, number ->
            if(i >= preambleCount && !subList(i - preambleCount, i).hasAdders(number))
                return number
        }
        return -1L
    }


    private fun List<Long>.hasAdders(target: Long): Boolean {
        return any { target - it != it && contains(target - it) }
    }

}