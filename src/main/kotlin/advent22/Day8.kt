package advent22

import shared.AdventSolution
import shared.Point
import shared.toPointMap
import java.math.BigInteger

class Day8 : AdventSolution(
    mapOf("""
        30373
        25512
        65332
        33549
        35390
    """.trimIndent() to 21),
    8
) {
    override fun solveProblem1(input: String): Any? {
        val map = input.toPointMap(String::toInt)
        return map.count { (point, height) ->
            listOf('u','d','r','l').any { dir ->
                var current: Point = point
                do {
                    val next = current.navigate(dir, 1)
                    map[next]?.let{ nextHeight ->
                        if(nextHeight >= height) return@any false
                    }
                } while(map.containsKey(next))
                true
            }
        }
    }

    override fun solveProblem2(input: String): Any? {
        val map = input.toPointMap(String::toInt)
        return map.maxOf { (point, height) ->
            listOf('u','d','r','l').map { dir ->
                var next: Point = point
                var score = 0
                do {
                    next = next.navigate(dir, 1)
                    map[next]?.let{ nextHeight ->
                        score += 1
                        if(nextHeight >= height) return@map score
                    }
                } while(map.containsKey(next))
                score
            }.fold(BigInteger.ONE) { bi, score ->
                bi.times(score.toBigInteger())
            }
        }
    }
}