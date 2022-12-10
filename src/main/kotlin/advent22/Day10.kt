package advent22

import shared.AdventSolution
import shared.splitLines
import java.math.BigInteger
import kotlin.math.absoluteValue

class Day10 : AdventSolution(
    mapOf("""
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent() to 13140),
    1
) {
    override fun solveProblem1(input: String): Any? {
        val program = Program()
        input.splitLines().forEach { command ->
            program.execute(command)
        }
        return program.totalSignalStrength
    }

    override fun solveProblem2(input: String): Any? {
        val program = Program()
        input.splitLines().forEach { command ->
            program.execute(command)
        }
        program.draw()
        return 1
    }

    private class Program {
        var cycle = 1
            private set
        var regX = 1
            private set
        var totalSignalStrength = BigInteger.ZERO
            private set
        var pixels = mutableListOf<Boolean>()

        fun cycle(op: (() -> Unit)? = null) {
            val lit = (((cycle - 1) % 40) - regX).absoluteValue <= 1
            pixels.add(lit)
            op?.invoke()
            cycle += 1
            if(cycle < 221 && (cycle - 20) % 40 == 0) {
                totalSignalStrength = totalSignalStrength.add(cycle.toBigInteger().times(regX.toBigInteger()))
            }
        }

        fun execute(command: String) {
            cycle()
            val words = command.split(" ")
            when(words.first()) {
                "noop" -> {}
                "addx" -> {
                    cycle { regX += words.last().toInt() }
                }
            }
        }

        fun draw () {
            pixels.chunked(40).forEach { line ->
                println(line.joinToString("") { if (it) "#" else "." })
            }
        }
    }
}