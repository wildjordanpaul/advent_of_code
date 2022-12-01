package advent21

import shared.AdventSolution
import shared.Point
import shared.splitInTwo

class Day21 : AdventSolution(
    mapOf(
        """
            Player 1 starting position: 4
            Player 2 starting position: 8
        """.trimIndent() to 739785
    ),
    mapOf(

    ),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val players = input.split("\n").mapNotNull { it ->
            if(it.isNotEmpty()) {
                val ints = it.split(" ").mapNotNull(String::toIntOrNull)
                Player(ints.first(), ints.last() - 1)
            } else null
        }
        val dice = Dice()
        while (true) {
            players.forEach { player ->
                player.move(dice.roll() + dice.roll() + dice.roll())
                if (player.score >= 1000) {
                    return players.minOf(Player::score) * dice.rollCount
                }
            }
        }
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }

    private class DiracDice {
        
    }

    private class Dice {
        private var value = 0
        var rollCount = 0

        fun roll(): Int {
            rollCount++
            value += 1
            if (value > 100)
                value %= 100
            return value
        }
    }

    private data class Player(
        val id: Int,
        var position: Int,
        var score: Int = 0
    ) {
        fun move(spaces: Int) {
            position = (position + spaces) % 10
            score += position + 1
        }
    }




}