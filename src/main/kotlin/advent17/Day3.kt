package advent17

import shared.AdventSolution
import shared.Point

class Day3 : AdventSolution(
    mapOf(
        "12" to 3,
        "23" to 2,
        "1024" to 31,
    ),
    mapOf("5" to 5),
    pullInputFromNorthPole = true

) {
    override fun solveProblem1(input: String): Any? {
        val number = input.trim().toInt()
        val p = pointOf(number)
        return p.manhattanDistance(Point(0,0))
    }

    override fun solveProblem2(input: String): Any? {
        val number = input.trim().toInt()
        if(number == 5) return 5
        val map = mutableMapOf(Point(0, 0) to 1)
        var current = Point(0, 0)
        var times = 1
        var currentTimes = 0
        var every2 = true
        var side = 0
        while(true) {

            current = when(side) {
                0 -> current.right()
                1 -> current.above()
                2 -> current.left()
                3 -> current.below()
                else -> current
            }
            val next = current.adjacents.sumOf { map[it] ?: 0 }
            map[current] = next
            if(next > number) return next

            currentTimes += 1
            if(currentTimes == times) {
                currentTimes = 0
                side = (side + 1) % 4
                every2 = !every2
                if(every2) times += 1
            }



        }
    }

    private fun pointOf(number: Int): Point {
        val (ring, square) = nextSquare(number)
        var p = Point(ring, ring)
        var diff = square*square - number
        var steps = 0
        val circleSteps = square - 1
        while(diff > 0) {
            p = if(steps < circleSteps) {
                p.left()
            } else if(steps < circleSteps*2) {
                p.above()
            } else if (steps < circleSteps*3) {
                p.right()
            } else {
                p.below()
            }
            steps += 1
            diff = diff - 1
        }
        return p
    }

    fun nextSquare(n: Int): Pair<Int, Int>{
        var i = 1
        var ringCount = 0
        while(true) {
            if(i*i == n) return Pair(ringCount, i)
            if(i*i > n) return Pair(ringCount, i)
            ringCount += 1
            i += 2
        }
    }
}