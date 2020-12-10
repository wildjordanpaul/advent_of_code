package advent20

import shared.AdventSolution
import shared.splitInts

class Day10 : AdventSolution(
    mapOf("""
        28
        33
        18
        42
        31
        14
        46
        20
        48
        47
        24
        23
        49
        45
        19
        38
        39
        11
        1
        32
        25
        35
        8
        17
        7
        9
        4
        2
        34
        10
        3
    """.trimIndent() to 220),
    mapOf("""
        16
        10
        15
        5
        1
        11
        7
        19
        6
        12
        4
    """.trimIndent() to 8,
        """
        28
        33
        18
        42
        31
        14
        46
        20
        48
        47
        24
        23
        49
        45
        19
        38
        39
        11
        1
        32
        25
        35
        8
        17
        7
        9
        4
        2
        34
        10
        3
    """.trimIndent() to 19208),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        var oneVoltDiffs = 0
        var threeVoltDiffs = 0

        var currentJoltage = 0
        input.splitInts("\n").sorted().forEach { jolt ->
            when(jolt - currentJoltage) {
                1 -> oneVoltDiffs++
                3 -> threeVoltDiffs++
            }
            currentJoltage = jolt
        }

        return oneVoltDiffs * (threeVoltDiffs + 1)
    }

    override fun solveProblem2(input: String): Any {
        var adapters = input.splitInts("\n").sorted()
        adapters = listOf(0) + adapters + (adapters.last() + 3)

        val variations = LongArray(adapters.size) { if(it == 0) 1L else 0L }
        adapters.forEachIndexed { i, adapter ->
            var j = i+1
            while (j < adapters.size && adapters[j] - adapter <= 3) {
                variations[j++] += variations[i]
            }
        }

        return variations.last()
    }

}