package advent21

import shared.AdventSolution
import shared.Point
import java.math.BigInteger

class Day11 : AdventSolution(
    mapOf("""
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent() to 1656),
    mapOf("""
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent() to 195),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        var map = input.toOctopusMap()

        return (1..100).sumOf {
            val (newMap, flashes) = map.findFlashes()
            map = newMap
            flashes.count()
        }
    }

    override fun solveProblem2(input: String): Any? {
        var map = input.toOctopusMap()
        var turn = 0
        do {
            turn += 1
            val (newMap, flashes) = map.findFlashes()
            map = newMap
        } while(flashes.count() < 100)
        return turn
    }

    private fun Map<Point, Int>.findFlashes(): Pair<Map<Point, Int>, Set<Point>> {
        val flashes = mutableSetOf<Point>()
        val map = mapValues { (point, number) ->
            if(number == 9) {
                flashes.add(point)
                0
            } else {
                number + 1
            }
        }.toMutableMap()

        var newFlashes = flashes.toSet()
        while(newFlashes.isNotEmpty()) {
            flashes.addAll(newFlashes)
            newFlashes = newFlashes.flatMap(Point::adjacents).mapNotNull { point ->
                when(val value = map[point]) {
                    9 -> {
                        map[point] = 0
                        point
                    }
                    0, null -> null
                    else -> {
                        map[point] = value + 1
                        null
                    }
                }
            }.toSet()
        }
        return Pair(map, flashes)
    }

    private fun String.toOctopusMap() = split(Regex("\n")).flatMapIndexed { x, row ->
        row.trim().mapIndexed { y, it -> Point(x, y) to it.toString().toInt() }
    }.toMap()
}