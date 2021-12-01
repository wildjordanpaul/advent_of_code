package advent20

import shared.AdventSolution
import shared.splitInTwo
import shared.splitInts

class Day22 : AdventSolution(
mapOf("""
    Player 1:
    9
    2
    6
    3
    1
    
    Player 2:
    5
    8
    4
    7
    10
        """.trimIndent() to 306
),
mapOf(),
pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any? {
        val decks = input.split("\n\n").map{ it.split(':').last().trim().splitInts().toMutableList() }
        val deck1 = decks.first()
        val deck2 = decks.last()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            val card1 = deck1.removeFirst()
            val card2 = deck2.removeFirst()
            if (card1 > card2) {
                deck1.add(card1)
                deck1.add(card2)
            } else {
                deck2.add(card2)
                deck2.add(card1)
            }
        }
        val winningDeck = deck1.ifEmpty { deck2 }
        return winningDeck.reversed().mapIndexed { i, card -> card * (i+1) }.sum()
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }
}