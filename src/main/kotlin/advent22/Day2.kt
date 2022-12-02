package advent22

import shared.AdventSolution
import shared.splitInTwo
import shared.splitLines

class Day2 : AdventSolution(
    mapOf("""
        A Y
        B X
        C Z
    """.trimIndent() to 15),
    mapOf("""
        A Y
        B X
        C Z 
    """.trimIndent() to 12),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return input.splitLines().sumOf { line ->
            val (p1, p2) = line.splitInTwo(" ") { c -> toPlay(c.first()) }
            p1.against(p2).points + p2.points
        }
    }

    override fun solveProblem2(input: String): Any? {
        return input.splitLines().sumOf {
            val (p1, p2) = it.splitInTwo(" ")
            val play = toPlay(p1.first())
            val result = toResult(p2.first())
            play.forResult(result).points + result.points
        }
    }

    private fun toPlay(c: Char): Play {
        return when(c) {
            'A', 'X' -> Play.ROCK
            'B', 'Y' -> Play.PAPER
            'C', 'Z' -> Play.SCISSORS
            else -> null
        }!!
    }

    private fun toResult(c: Char): Result {
        return when(c) {
            'X' -> Result.LOSE
            'Y' -> Result.TIE
            'Z' -> Result.WIN
            else -> null
        }!!
    }

    private enum class Play(val points: Int) {
        ROCK(1), PAPER( 2), SCISSORS( 3);

        fun win() = when(this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }

        fun lose() = win().win()

        fun against(p2: Play): Result {
            return when (p2) {
                this -> Result.TIE
                win() -> Result.WIN
                else -> Result.LOSE
            }
        }

        fun forResult(result: Result) = when(result) {
            Result.WIN -> win()
            Result.TIE -> this
            Result.LOSE -> lose()
        }
    }

    private enum class Result(val points: Int) {
        WIN(6), TIE(3), LOSE(0)
    }
}