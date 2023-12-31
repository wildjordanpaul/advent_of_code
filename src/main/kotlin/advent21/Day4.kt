package advent21

import shared.*

class Day4 : AdventSolution(
    mapOf("""
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19

         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6

        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7

    """.trimIndent() to 4512),
    mapOf("""
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19

         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6

        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7

    """.trimIndent() to 1924),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return playBingo(input, true)
    }

    override fun solveProblem2(input: String): Any? {
        return playBingo(input, false)
    }

    private fun playBingo(input: String, returnWinner: Boolean): Int {
        val inputs = input.split(Regex("\n\n"))
        val numbers = inputs.first().splitInts()
        val boards = inputs.drop(1).map(::Board)
        val calledNumbers = mutableSetOf<Int>()
        val winners = mutableSetOf<Int>()
        numbers.forEach { number ->
            calledNumbers.add(number)
            boards.forEachIndexed { i, board ->
                if(!winners.contains(i) && board.hasWinWith(calledNumbers)) {
                    winners.add(i)
                    if(returnWinner || winners.count() == boards.count()) {
                        return board.score(calledNumbers) * number
                    }
                }
            }
        }
        return 0
    }

    data class Board(val data: String) {
        private val rows = data.trim()
            .split(Regex("\n"))
            .map { a -> a.trim().splitInts(Regex("\\s+")) }
        private val columns = (0 until 5).map { i -> rows.map { it[i] } }
        private val possibilities = rows + columns

        fun hasWinWith(calledNumbers: Collection<Int>): Boolean {
            return possibilities.any{ calledNumbers.containsAll(it) }
        }

        fun score(calledNumbers: Collection<Int>): Int {
            return rows.flatten().subtract(calledNumbers).sum()
        }
    }
}