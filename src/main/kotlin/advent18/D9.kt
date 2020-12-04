package advent18

import shared.AdventSolution
import java.math.BigInteger
import java.util.*

fun main() = object : AdventSolution(
    mapOf(
        "9 players; last marble is worth 25 points" to "32",
        "10 players; last marble is worth 1618 points" to "8317",
        "13 players; last marble is worth 7999 points" to "146373",
        "17 players; last marble is worth 1104 points" to "2764",
        "21 players; last marble is worth 6111 points" to "54718",
        "30 players; last marble is worth 5807 points" to "37305"
    ),
    mapOf(

    ),
    "459 players; last marble is worth 71790 points",
    "459 players; last marble is worth 7179000 points"
) {

    private fun String.toGame(): D9Game {
        val (d1, d2) = """(\d+) players; last marble is worth (\d+) points""".toRegex().find(this)!!.destructured
        return D9Game(d1.toInt(), d2.toInt())
    }

    override fun solveProblem1(input: String): String {
        val game = input.toGame()
        val score = mutableMapOf<Int, BigInteger>()
        val circle = LinkedList<Int>().apply { add(0) }
        var iter = circle.listIterator()
        var currentPlayer = 0
        var counter = 1

        fun next(): Int {
            return if(iter.hasNext()) iter.next()
            else {
                iter = circle.listIterator()
                iter.next()
            }
        }

        fun prev(): Int {
            return if(iter.hasPrevious())
                iter.previous()
            else {
                iter = circle.listIterator(circle.size)
                iter.previous()
            }
        }

        fun printState() {
            val ci = iter.nextIndex()
            val state = circle.mapIndexed { idx, i -> if(idx == ci-1) "($i)" else " $i "}.joinToString(" ")
            println("[$currentPlayer] $state")
        }

        (1..game.marbleCount).forEach {
            if(counter == 23) {
                counter = 0

                val removed = (0..7).map { prev() }.last()
                iter.remove()
                next()

                val prevScore = score[currentPlayer] ?: BigInteger.ZERO
                score[currentPlayer] = prevScore.add((removed+it).toBigInteger())
            } else {
                next()
                iter.add(it)
            }

            currentPlayer = (currentPlayer + 1) % (game.playerCount)
            counter += 1
        }
        return score.values.maxOrNull().toString()
    }

    override fun solveProblem2(input: String): String {
        return solveProblem1(input)
    }

}.solve()

data class D9Game(
    val playerCount: Int,
    val marbleCount: Int
)