package advent20.models

import shared.splitInTwo

data class Instruction(val type: InstructionType, val value: Int) {

    enum class InstructionType {
        ACC, JMP, NOP
    }

    companion object {
        fun String.toInstructions() = split("\n").map {
            val (type, value) = it.splitInTwo(" ")
            Instruction(InstructionType.valueOf(type.toUpperCase()), value.toInt())
        }

        fun List<Instruction>.run(): Int? {
            var i = 0
            var acc = 0
            val stack = mutableSetOf<Int>()
            while(i != size) {
                if (stack.contains(i))
                    return null
                stack.add(i)
                val (type, value) = get(i)
                when(type) {
                    InstructionType.ACC -> {
                        acc += value
                        i += 1
                    }
                    InstructionType.JMP -> {
                        i += value
                    }
                    else -> {
                        i += 1
                    }
                }
            }
            return acc
        }
    }

    fun switchTo(newType: InstructionType) = Instruction(newType, value)
}