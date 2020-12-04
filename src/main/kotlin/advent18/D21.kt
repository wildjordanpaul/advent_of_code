package advent18

import advent18.model18.Instruction
import shared.AdventSolution

fun main() = object : AdventSolution(
        mapOf(),
        mapOf(),
        """
            #ip 3
            seti 123 0 4
            bani 4 456 4
            eqri 4 72 4
            addr 4 3 3
            seti 0 0 3
            seti 0 6 4
            bori 4 65536 5
            seti 1855046 9 4
            bani 5 255 2
            addr 4 2 4
            bani 4 16777215 4
            muli 4 65899 4
            bani 4 16777215 4
            gtir 256 5 2
            addr 2 3 3
            addi 3 1 3
            seti 27 0 3
            seti 0 9 2
            addi 2 1 1
            muli 1 256 1
            gtrr 1 5 1
            addr 1 3 3
            addi 3 1 3
            seti 25 5 3
            addi 2 1 2
            seti 17 0 3
            setr 2 7 5
            seti 7 9 3
            eqrr 4 0 2
            addr 2 3 3
            seti 5 3 3
        """.trimIndent()
) {

    override fun solveProblem1(input: String): String {
        return solve(input)
    }

    override fun solveProblem2(input: String): String {
        return solve(input, false)
    }

    private fun solve(input: String, p1: Boolean = true): String {
        var register = listOf(0,0,0,0,0,0)
        val lines = input.lines()
        val instructionRegister = lines.first().split(" ").last().toInt()
        val instructions = lines.takeLast(lines.size-1).map { line ->
            val (instruction, code) = """(\w+) ([0-9\s]+)""".toRegex().find(line)!!.destructured
            Pair(
                Instruction.valueOf(instruction.toUpperCase()),
                code.split(" ").map(String::toInt)
            )
        }
        val seen = mutableSetOf<Int>()
        var lastSeen: Int? = null
        val comparable = instructions[28].second.first()
        while(true) {
            if(register[instructionRegister] == 28) {
                if(p1)
                    return register[comparable].toString()
                if(!seen.add(register[comparable]))
                    return "$lastSeen"
                lastSeen = register[comparable]
            }
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
//            println("[${register.joinToString(", ")}] ${instruction.first.name.toLowerCase()} ${instruction.second.joinToString(" ")} [${newRegister.joinToString(", ")}]")
            register = newRegister
        }
    }

}.solve()

