package advent20

import shared.AdventSolution

class Day15 : AdventSolution(
    mapOf(
        "0,3,6" to 436,
        "1,3,2" to 1,
        "2,1,3" to 10,
        "1,2,3" to 27,
        "2,3,1" to 78,
        "3,2,1" to 438,
        "3,1,2" to 1836
    ),
    mapOf(
        "0,3,6" to 175594,
        "1,3,2" to 2578,
        "2,1,3" to 3544142,
        "1,2,3" to 261214,
        "2,3,1" to 6895259,
        "3,2,1" to 18,
        "3,1,2" to 362
    ),
    "0,3,1,6,7,5"
) {

    override fun solveProblem1(input: String): Any {
        return play(input, 2020)
    }

    override fun solveProblem2(input: String): Any {
        return play(input, 30_000_000)
    }

    private fun play(input: String, end: Int): Int {
        val ints = input.split(",").map(String::toInt)
        var last: Int = ints.first()
        val turnsSince1 = mutableMapOf<Int, Int>()
        val turnsSince2 = mutableMapOf<Int, Int>()
        (0 until end).forEach { turn ->
            val next: Int = when {
                turn < ints.size -> ints[turn]
                turnsSince2.containsKey(last) -> turnsSince1[last]!! - turnsSince2[last]!!
                else -> 0
            }

            turnsSince1[next]?.let { turnsSince2[next] = it }
            turnsSince1[next] = turn
            last = next
        }
        return last
    }

}