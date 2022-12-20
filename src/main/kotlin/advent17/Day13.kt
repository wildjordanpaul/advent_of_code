package advent17

import shared.AdventSolution
import shared.splitInTwo
import java.math.BigInteger

class Day13 : AdventSolution(
    mapOf("""
        Generator A starts with 65
        Generator B starts with 8921
    """.trimIndent() to 600),
    309
) {
    override fun solveProblem1(input: String): Any? {
        var (gen1, gen2) = input.trim().splitInTwo("\n") { it.trim().split(" ").last().toBigInteger() }
        val b1 = "16807".toBigInteger()
        val b2 = "48271".toBigInteger()
        val r = "2147483647".toBigInteger()
        val count = (0 until 40_000_000).count {
            gen1 = gen1.multiply(b1).remainder(r)
            gen2 = gen2.multiply(b2).remainder(r)
            gen1.toString(2).takeLast(16) == gen2.toString(2).takeLast(16)
        }
        return count
    }

    override fun solveProblem2(input: String): Any? {
        var (gen1, gen2) = input.trim().splitInTwo("\n") { it.trim().split(" ").last().toBigInteger() }
        val b1 = "16807".toBigInteger()
        val b2 = "48271".toBigInteger()
        val r = "2147483647".toBigInteger()
        val count = (0 until 5_000_000).count {
            do {
                gen1 = gen1.multiply(b1).remainder(r)
            } while(gen1.mod(BigInteger("4")) != BigInteger.ZERO)
            do {
                gen1 = gen1.multiply(b1).remainder(r)
            } while(gen1.mod(BigInteger("8")) != BigInteger.ZERO)

            gen1.toString(2).takeLast(16) == gen2.toString(2).takeLast(16)
        }
        return count
    }
}