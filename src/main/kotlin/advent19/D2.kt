package advent19

import shared.AdventSolution
import shared.forEachPair
import shared.splitInts

class D2 : AdventSolution(
    mapOf(
        "1,9,10,3,2,3,11,0,99,30,40,50" to "3500,9,10,70,2,3,11,0,99,30,40,50",
        "1,0,0,0,99" to "2,0,0,0,99",
        "2,3,0,3,99" to "2,3,0,6,99",
        "2,4,4,5,99,0" to "2,4,4,5,99,9801",
        "1,1,1,4,99,5,6,0,99" to "30,1,1,4,2,5,6,0,99"
    ),
    mapOf(
    ),
    "1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,2,19,6,23,2,13,23,27,1,9,27,31,2,31,9,35,1,6,35,39,2,10,39,43,1,5,43,47,1,5,47,51,2,51,6,55,2,10,55,59,1,59,9,63,2,13,63,67,1,10,67,71,1,71,5,75,1,75,6,79,1,10,79,83,1,5,83,87,1,5,87,91,2,91,6,95,2,6,95,99,2,10,99,103,1,103,5,107,1,2,107,111,1,6,111,0,99,2,14,0,0"
) {
    override fun solveProblem1(input: String): Any {
        return input
            .splitInts()
            .executeProgram()
            .joinToString(",")
    }

    override fun solveProblem2(input: String): Any {
        val program = input.splitInts()
        val desiredOutput = 19690720
        (0..99).forEachPair { (noun, verb)->
            if(program.executeProgram(noun, verb)[0] == desiredOutput) {
                return 100 * noun + verb
            }
        }
        throw RuntimeException("No reachable noun/verb combinations produce result of $desiredOutput")
    }

    private fun List<Int>.executeProgram(
        noun: Int? = null,
        verb: Int? = null
    ): List<Int> {
        val program = this.toMutableList()

        if(noun != null) program[1] = noun
        if(verb != null) program[2] = verb

        var pointer = 0
        while(true) {
            val val1 by lazy { program[program[pointer+1]] }
            val val2 by lazy { program[program[pointer+2]] }
            val dest by lazy { program[pointer+3] }

            when(val instruction = program[pointer]) {
                1 -> {
                    program[dest] = val1 + val2
                    pointer += 4
                }
                2 -> {
                    program[dest] = val1 * val2
                    pointer += 4
                }
                99 -> {
                    return program
                }
                else -> {
                    throw RuntimeException("Not a valid opcode: $instruction")
                }
            }
        }
    }

}
