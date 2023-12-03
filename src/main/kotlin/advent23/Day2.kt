package advent23

import shared.AdventSolution
import shared.splitInTwo
import shared.splitLines
import kotlin.math.max

class Day2 : AdventSolution(
    mapOf("""
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent() to 8),
        2286
) {
    override fun solveProblem1(input: String): Any? {
        return input.splitLines().sumOf { line ->
            val (game, rounds) = line.splitInTwo(": ")
            if (rounds.split(';').all { round ->
                round.split(',').all { cubes ->
                    val (count, color) = cubes.trim().splitInTwo(" ")
                    when(color) {
                        "red" -> count.toInt() <= 12
                        "green" -> count.toInt() <= 13
                        "blue" -> count.toInt() <= 14
                        else -> throw RuntimeException("Unknown color $color")
                    }
                }
            }) game.split(" ").last().toInt() else 0
        }
    }

    override fun solveProblem2(input: String): Any? {
        return input.splitLines().sumOf { line ->
            val (_, rounds) = line.splitInTwo(": ")
            var maxR = 0L
            var maxG = 0L
            var maxB = 0L
            rounds.split(';').forEach { round ->
                round.split(',').forEach { cubes ->
                    val (count, color) = cubes.trim().splitInTwo(" ")
                    when(color) {
                        "red" -> maxR = max(maxR, count.toLong())
                        "green" -> maxG = max(maxG, count.toLong())
                        "blue" -> maxB = max(maxB, count.toLong())
                        else -> throw RuntimeException("Unknown color $color")
                    }
                }
            }
            maxR * maxG * maxB
        }
    }
}