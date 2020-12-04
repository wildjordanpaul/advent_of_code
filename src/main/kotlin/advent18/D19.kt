package advent18

import advent18.model18.Instruction
import shared.AdventSolution

fun main() = object : AdventSolution(
        mapOf("""
            #ip 0
            seti 5 0 1
            seti 6 0 2
            addi 0 1 0
            addr 1 2 3
            setr 1 0 0
            seti 8 0 4
            seti 9 0 5
        """.trimIndent() to "6"),
        mapOf(),
        """
            #ip 3
            addi 3 16 3
            seti 1 7 1
            seti 1 7 5
            mulr 1 5 4
            eqrr 4 2 4
            addr 4 3 3
            addi 3 1 3
            addr 1 0 0
            addi 5 1 5
            gtrr 5 2 4
            addr 3 4 3
            seti 2 2 3
            addi 1 1 1
            gtrr 1 2 4
            addr 4 3 3
            seti 1 5 3
            mulr 3 3 3
            addi 2 2 2
            mulr 2 2 2
            mulr 3 2 2
            muli 2 11 2
            addi 4 2 4
            mulr 4 3 4
            addi 4 2 4
            addr 2 4 2
            addr 3 0 3
            seti 0 8 3
            setr 3 8 4
            mulr 4 3 4
            addr 3 4 4
            mulr 3 4 4
            muli 4 14 4
            mulr 4 3 4
            addr 2 4 2
            seti 0 7 0
            seti 0 9 3
        """.trimIndent()
) {

    override fun solveProblem1(input: String): String {
        return solve(input, listOf(0,0,0,0,0,0))
    }

    override fun solveProblem2(input: String): String {
//        return solve(input, listOf(1,0,0,0,0,0))
        val number = 10551282
//        10551282
        val sumOfFactors = (1..number).filter { number % it == 0 }.sum()
        return sumOfFactors.toString()
    }

    private fun solve(input: String, initialRegister: List<Int>): String {
        var register = initialRegister
        val lines = input.lines()
        val instructionRegister = lines.first().split(" ").last().toInt()
        val instructions = lines.takeLast(lines.size-1).map { line ->
            val (instruction, code) = """(\w+) ([0-9\s]+)""".toRegex().find(line)!!.destructured
            Pair(
                Instruction.valueOf(instruction.toUpperCase()),
                code.split(" ").map(String::toInt)
            )
        }
        while(true) {
            val index = register[instructionRegister]
            val instruction = instructions.getOrNull(index) ?: return register[0].toString()
            val newRegister = instruction.first.execute(register, instruction.second).toMutableList().apply {
                val newIndex = this[instructionRegister]+1
                if(newIndex >= instructions.size) {
                    return this[0].toString()
                } else {
                    set(instructionRegister, get(instructionRegister) + 1)
                }
            }
            println("[${register.joinToString(", ")}] ${instruction.first.name.toLowerCase()} ${instruction.second.joinToString(" ")} [${newRegister.joinToString(", ")}]")
            register = newRegister
        }
    }

}.solve(true)

