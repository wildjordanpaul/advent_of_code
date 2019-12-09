package advent19

import shared.AdventSolution
import shared.splitInts

class D7 : AdventSolution(
    mapOf(
        "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0" to  43210,
        "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0" to 54321,
        "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0" to 65210
    ),
    mapOf(
        "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5" to 139629729,
        "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10" to 18216

    ),
    "3,8,1001,8,10,8,105,1,0,0,21,38,47,64,85,106,187,268,349,430,99999,3,9,1002,9,4,9,1001,9,4,9,1002,9,4,9,4,9,99,3,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,102,5,9,9,1001,9,5,9,4,9,99,3,9,101,3,9,9,102,5,9,9,1001,9,4,9,102,4,9,9,4,9,99,3,9,1002,9,3,9,101,2,9,9,102,4,9,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99"
) {
    override fun solveProblem1(input: String): Any? {
        val amp = Amplifier(0, input)
        return listOf(0,1,2,3,4).permutations().map { permutation ->
            permutation.fold(0) { programInput, phaseSetting ->
                amp.reset()
                amp.execute(programInput, phaseSetting, false)
            }
        }.max()
    }

    override fun solveProblem2(input: String): Any? {
        val amps = (0..4).map { Amplifier(it, input) }
        return listOf(5,6,7,8,9).permutations().map { permutation ->
            amps.forEach(Amplifier::reset)
            var nextInput = 0
            while(!amps.last().halted) {
                amps.forEachIndexed { index, amp ->
                    nextInput = amp.execute(nextInput, permutation[index], true)
                }
            }
            nextInput
        }.max()
    }

    private fun List<Int>.permutations(): List<List<Int>> {
        if(size == 0) return emptyList()
        if(size == 1) return listOf(this)
        val current = mutableListOf<List<Int>>()
        (0 until size).forEach { i ->
            val remList = this.toMutableList()
            val m = remList.removeAt(i)
            remList.permutations().forEach { permutation ->
                current.add(listOf(m) + permutation)
            }
        }
        return current
    }

    private class Amplifier(
        val id: Int,
        val rawProgram: String
    ) {
        lateinit var program: MutableList<Int>
        lateinit var output: MutableList<Int>

        var pointer: Int = 0
        var halted: Boolean = false
        var phaseSettingUsed: Boolean = false

        init {
            reset()
        }

        fun reset() {
            program = rawProgram.splitInts().toMutableList()
            output = mutableListOf<Int>()
            pointer = 0
            halted = false
            phaseSettingUsed = false
        }

        fun execute(programInput: Int, phaseSetting: Int, returnOutput: Boolean = true): Int {
            while(true) {
                val instruction = program[pointer].toString()

                val val1 by lazy { valueAt(instruction, pointer, 1) }
                val val2 by lazy { valueAt(instruction, pointer, 2) }

                when(val opcode = instruction.takeLast(2).toInt()) {
                    1 -> {
                        program[program[pointer+3]] = val1 + val2
                        pointer += 4
                    }
                    2 -> {
                        program[program[pointer+3]] = val1 * val2
                        pointer += 4
                    }
                    3 -> {
                        val input = if(phaseSettingUsed) programInput else {
                            phaseSettingUsed = true
                            phaseSetting
                        }
                        program[program[pointer+1]] = input
                        pointer += 2
                    }
                    4 -> {
                        output.add(val1)
                        pointer += 2
                        if(returnOutput) return val1
                    }
                    5 -> {
                        if(val1 != 0) pointer = val2
                        else pointer += 3
                    }
                    6 -> {
                        if(val1 == 0) pointer = val2
                        else pointer += 3
                    }
                    7 -> {
                        program[program[pointer+3]] = if(val1 < val2) 1 else 0
                        pointer += 4
                    }
                    8 -> {
                        program[program[pointer+3]] = if(val1 == val2) 1 else 0
                        pointer += 4
                    }
                    99 -> {
                        halted = true
                        return output.lastOrNull() ?: -1
                    }
                    else -> {
                        throw RuntimeException("Not a valid opcode: $opcode")
                    }
                }
            }
        }

        private fun valueAt(instruction: String, pointer: Int, offset: Int): Int {
            val position = instruction.length-2-offset
            return if(position < 0 || instruction[position] == '0') program[program[pointer+offset]] else program[pointer+offset]
        }
    }

}
