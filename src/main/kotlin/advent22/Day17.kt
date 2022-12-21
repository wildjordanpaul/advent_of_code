package advent22

import shared.AdventSolution
import shared.LongPoint

class Day17 : AdventSolution(
    mapOf("""
        >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
    """.trimIndent() to 3068),
    "1514285714288"
) {
    override fun solveProblem1(input: String): Any? {
        return drop(input, 2022)
    }

    override fun solveProblem2(input: String): Any? {
        return drop(input, 1000000000000)
    }

    private fun drop(input: String, turns: Long): Long {
        val rocks = listOf(
            listOf(LongPoint(2, 0), LongPoint(3, 0), LongPoint(4, 0), LongPoint(5, 0)),
            listOf(LongPoint(3,0), LongPoint(2,1), LongPoint(3,1), LongPoint(4,1), LongPoint(3, 2)),
            listOf(LongPoint(2,0), LongPoint(3,0), LongPoint(4,0), LongPoint(4,1), LongPoint(4,2)),
            listOf(LongPoint(2,0), LongPoint(2,1), LongPoint(2,2), LongPoint(2,3)),
            listOf(LongPoint(2,0), LongPoint(2,1), LongPoint(3,0), LongPoint(3,1))
        )

        var stopped = mutableSetOf<LongPoint>()
        var jetIndex = 0
        var rockIndex = 0
        var top: Long = -1

        val cycle = input.length * rocks.size * 7
        var topAfterFirstCycle: Long? = null
        var topAfterSecondCycle = 0L
        var turnsTaken = 0L
        var topOffset = 0L

        while(turnsTaken < turns) {
            turnsTaken += 1
            if(turnsTaken % cycle == 0L) {
                if(topAfterFirstCycle == null)
                    topAfterFirstCycle = top
                else {
                    if(topAfterSecondCycle == 0L)
                        topAfterSecondCycle = top

                    while(turnsTaken + cycle < turns) {
                        turnsTaken += cycle
                        topOffset += topAfterSecondCycle - topAfterFirstCycle
                    }
                }
            }
            var rock = rocks[rockIndex].map { it.below(top + 4) }
            rockIndex += 1
            if(rockIndex > rocks.size-1) rockIndex = 0
            var dropping = true
            while(dropping) {
                val jet = input[jetIndex  % input.length]
                jetIndex += 1
                if(jetIndex > input.length-1) jetIndex = 0

                val jetRock = rock.map { it.navigate(jet) }
                if(jetRock.all { it.x in 0..6 && !stopped.contains(it) })
                    rock = jetRock

                val downRock = rock.map { it.above() }
                if(downRock.all { it.y >= 0 && !stopped.contains(it) })
                    rock = downRock
                else {
                    val rockTop = rock.maxOf { it.y }
                    if(rockTop > top) top = rockTop
                    stopped.addAll(rock)
                    dropping = false
                }
            }

        }
        return top + 1 + topOffset
    }

    private fun printRocks(
        top: Long, stopped: Set<LongPoint>, action: String = "Next", rock: List<LongPoint>? = null
    ) {
        println(action)
        (0..top + 4).reversed().forEach { y ->
            println((-1..7).joinToString("") { x ->
                val p = LongPoint(x.toLong(), y)
                if(x == -1 || x == 7) "|"
                else if(stopped.contains(p)) "#"
                else if(rock != null && rock.contains(p)) "@"
                else "."
            })
        }
        println("--------")
    }
}