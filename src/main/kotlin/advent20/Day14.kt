package advent20

import shared.AdventSolution

class Day14 : AdventSolution(
    mapOf("""
        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        mem[8] = 11
        mem[7] = 101
        mem[8] = 0
    """.trimIndent() to 165),
    mapOf("""
        mask = 000000000000000000000000000000X1001X
        mem[42] = 100
        mask = 00000000000000000000000000000000X0XX
        mem[26] = 1
    """.trimIndent() to 208),
    pullInputFromNorthPole = true
) {

    companion object {
        val MEM_REGEX = """mem\[(\d+)] = (\d+)""".toRegex()
    }

    override fun solveProblem1(input: String): Any {
        val lines = input.split("\n")
        var mask = ""
        val memory = mutableMapOf<Int, Long>()
        lines.forEach { line ->
            if (line.startsWith("mask")) {
                mask = line.removePrefix("mask = ")
            } else {
                val (location, value) = MEM_REGEX.find(line)!!.destructured
                val binValue = value.toBin36()
                memory[location.toInt()] = mask.mapIndexed { i, char ->
                    when(char) {
                        'X' -> binValue.getOrElse(i) { '0' }
                        else -> char
                    }
                }.toLong36()
            }

        }
        return memory.values.sum()
    }

    override fun solveProblem2(input: String): Any {
        val lines = input.split("\n")
        var mask = ""
        val memory = mutableMapOf<Long, Long>()
        lines.forEach { line ->
            if (line.startsWith("mask")) {
                mask = line.removePrefix("mask = ")
            } else {
                val (location, value) = MEM_REGEX.find(line)!!.destructured
                val binLocation = location.toBin36()
                mask.mapIndexed { i, char ->
                    when(char) {
                        '0' -> binLocation[i]
                        else -> char
                    }
                }.permutations().forEach { permutation ->
                    memory[permutation.toLong36()] = value.toLong()
                }
            }

        }
        return memory.values.sum()
    }

    private fun List<Char>.toLong36() = takeLast(36).joinToString("").toLong(2)

    private fun String.toBin36() = toInt().toString(2).padStart(36, '0')


    private fun List<Char>.permutations() : List<List<Char>> {
        val chars = mutableListOf<Char>()
        forEachIndexed { i, char ->
            when(char) {
                'X' -> {
                    return subList(i + 1, size).permutations().flatMap { permutation ->
                        listOf(
                            chars + listOf('1') + permutation,
                            chars + listOf('0') + permutation
                        )
                    }
                }
                else -> chars.add(char)
            }
        }
        return listOf(chars)
    }

}