package advent17

import shared.AdventSolution
import shared.splitLines
import java.math.BigInteger

class Day18 : AdventSolution(
    mapOf("""
        set a 1
        add a 2
        mul a a
        mod a 5
        snd a
        set a 0
        rcv a
        jgz a -1
        set a 1
        jgz a -2
    """.trimIndent() to 4),
    1
) {
    private val reg = mutableMapOf<String, BigInteger>()
    override fun solveProblem1(input: String): Any? {
        reg.clear()

        var play: BigInteger? = null
        val lines = input.trim().splitLines("\n").filter { it.isNotEmpty() }.map { it.split(" ") }
        var i = 0
        while(true) {
            val parts = lines[i]
            when(parts[0]) {
                "snd" -> play = valueAt(parts[1])
                "set" -> reg[parts[1]] = valueAt(parts[2])
                "add" -> reg[parts[1]] = (reg[parts[1]] ?: BigInteger.ZERO) + valueAt(parts[2])
                "mul" -> reg[parts[1]] = (reg[parts[1]] ?: BigInteger.ZERO) * valueAt(parts[2])
                "mod" -> reg[parts[1]] = (reg[parts[1]] ?: BigInteger.ZERO) % valueAt(parts[2])
                "rcv" -> if(valueAt(parts[1]) != BigInteger.ZERO) return play
                "jgz" -> if(valueAt(parts[1]) > BigInteger.ZERO) i += (valueAt(parts[2]).toInt() - 1)
            }
            i += 1
            println(reg.values.joinToString(", "))
        }
    }

    private fun valueAt(s: String): BigInteger {
        return if(s.matches(Regex("[-0-9]*"))) {
            s.toBigInteger()
        } else {
            reg[s] ?: BigInteger.ZERO
        }
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }
}