package advent20

import shared.AdventSolution
import shared.Point

class Day11 : AdventSolution(
    mapOf("""
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent() to 37),
    mapOf("""
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent() to 26),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        var seatMap = input.split("\n").flatMapIndexed{ y, row ->
            row.mapIndexed { x, seat -> Point(x, y) to seat }
        }.toMap()

        do {
            var changed = false
            seatMap = seatMap.mapValues { (location, seat) ->
                when(seat) {
                    'L' -> {
                        if (location.adjacents.all { seatMap[it] != '#' }) {
                            changed = true
                            '#'
                        } else seat
                    }
                    '#' -> {
                        if (location.adjacents.count { seatMap[it] == '#' } >= 4) {
                            changed = true
                            'L'
                        } else seat
                    }
                    else -> seat
                }
            }
        } while (changed)
        return seatMap.values.count { it == '#' }
    }

    override fun solveProblem2(input: String): Any {
        var seatMap = input.split("\n").flatMapIndexed{ y, row ->
            row.mapIndexed { x, seat -> Point(x, y) to seat }
        }.toMap()

        do {
            var changed = false
            seatMap = seatMap.mapValues { (location, seat) ->
                when(seat) {
                    'L' -> {
                        if (seatMap.scanFrom(location) == 0) {
                            changed = true
                            '#'
                        } else seat
                    }
                    '#' -> {
                        if (seatMap.scanFrom(location) >= 5) {
                            changed = true
                            'L'
                        } else seat
                    }
                    else -> seat
                }
            }
        } while (changed)
        return seatMap.values.count { it == '#' }
    }

    private fun Map<Point, Char>.scanFrom(from: Point): Int {
        var count = 0

        listOf("U", "D", "L", "R", "UR", "UL", "DR", "DL").forEach { direction ->
            var i = 1
            do {
                val next = this[from.navigate(direction, i)]
                if (next == '#') count++
                i++
            } while (next == '.')
        }
        return count
    }

}