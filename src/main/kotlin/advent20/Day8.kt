package advent20

import advent20.models.Instruction
import advent20.models.Instruction.Companion.run
import advent20.models.Instruction.Companion.toInstructions
import shared.AdventSolution

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
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        val instructions = input.toInstructions()
        var i = 0
        var acc = 0
        val stack = mutableSetOf<Int>()
        while(!stack.contains(i)) {
            stack.add(i)
            val (type, value) = instructions[i]
            when(type) {
                Instruction.InstructionType.ACC -> {
                    acc += value
                    i += 1
                }
                Instruction.InstructionType.JMP -> {
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
                Instruction.InstructionType.JMP -> {
                    instructions[i] = instruction.switchTo(Instruction.InstructionType.NOP)
                    instructions.run()?.let { return it }
                    instructions[i] = instruction
                }
                Instruction.InstructionType.NOP -> {
                    instructions[i] = instruction.switchTo(Instruction.InstructionType.JMP)
                    instructions.run()?.let { return it }
                    instructions[i] = instruction
                }
            }
        }

        return -1
    }

}