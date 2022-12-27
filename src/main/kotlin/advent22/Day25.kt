package advent22

import shared.AdventSolution
import shared.splitLines
import shared.sum
import java.math.BigInteger

class Day25 : AdventSolution(
    mapOf("""
        1=-0-2
        12111
        2=0=
        21
        2=01
        111
        20012
        112
        1=-1=
        1-12
        12
        1=
        122
    """.trimIndent() to "2=-1=0"),
    1
) {
    override fun solveProblem1(input: String): Any? {
        val sum = input.splitLines().map { it.snafuToDecimal() }.sum()
        return sum.toSnafu()
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }

    private fun String.snafuToDecimal(): BigInteger {
        return reversed().mapIndexed { i, c ->
            val f = when(c) {
                '2' -> BigInteger.TWO
                '1' -> BigInteger.ONE
                '0' -> BigInteger.ZERO
                '-' -> BigInteger("-1")
                '=' -> BigInteger("-2")
                else -> throw RuntimeException("Invalid SNAFU")
            }
            5.toBigInteger().pow(i).times(f)
        }.sum()
    }
}

private fun BigInteger.toSnafu(): String {
    var rem = 0
    val radix = ("0" + toString(5))
    val snafu = radix.reversed().map { c ->
        val (s, r) = when(c.digitToInt() + rem) {
            5 -> '0' to 1
            4 -> '-' to 1
            3 -> '=' to 1
            2 -> '2' to 0
            1 -> '1' to 0
            0 -> '0' to 0
            else -> throw RuntimeException("Invalid SNAFU")
        }
        rem = r
        s
    }.reversed().joinToString("")
    return if(snafu[0] == '0') snafu.drop(1)
    else snafu
}
