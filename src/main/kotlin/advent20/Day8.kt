package advent20

import shared.AdventSolution
import shared.splitInTwo

class Day8 : AdventSolution(
    mapOf("""
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent() to 5),
    mapOf("""
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent() to 8),
    input1 = loadInput("/advent20/D8_input1.txt")
) {

    private data class Instruction(val type: String, val value: Int) {
        fun switchTo(newType: String) = Instruction(newType, value)
    }

    override fun solveProblem1(input: String): Any {
        val instructions = input.toInstructions()
        var i = 0
        var acc = 0
        val stack = mutableSetOf<Int>()
        while(!stack.contains(i)) {
            stack.add(i)
            val (type, value) = instructions[i]
            when(type) {
                "acc" -> {
                    acc += value
                    i += 1
                }
                "jmp" -> {
                    i += value
                }
                else -> {
                    i += 1
                }
            }
        }
        return acc
    }

    override fun solveProblem2(input: String): Any {
        val instructions = input.toInstructions().toMutableList()
        instructions.forEachIndexed { i, instruction ->
            when(instruction.type) {
                "jmp" -> {
                    instructions[i] = instruction.switchTo("nop")
                    instructions.run()?.let { return it }
                    instructions[i] = instruction
                }
                "nop" -> {
                    instructions[i] = instruction.switchTo("jmp")
                    instructions.run()?.let { return it }
                    instructions[i] = instruction
                }
            }
        }

        return -1
    }

    private fun List<Instruction>.run(): Int? {
        var i = 0
        var acc = 0
        val stack = mutableSetOf<Int>()
        while(i != size) {
            if (stack.contains(i))
                return null
            stack.add(i)
            val (type, value) = get(i)
            when(type) {
                "acc" -> {
                    acc += value
                    i += 1
                }
                "jmp" -> {
                    i += value
                }
                else -> {
                    i += 1
                }
            }
        }
        return acc
    }

    private fun String.toInstructions() = split("\n").map {
        val (type, value) = it.splitInTwo(" ")
        Instruction(type, value.toInt())
    }

}