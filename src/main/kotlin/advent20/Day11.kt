package advent20

import shared.AdventSolution
import shared.Point
import shared.splitInts

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
        var plane = input.split("\n").flatMapIndexed{ y, row ->
            row.mapIndexed { x, seat -> Point(x, y) to seat }
        }.toMap()

        do {
            var changed = false
            plane = plane.map { (location, seat) ->
                when(seat) {
                    'L' -> {
                        if (location.adjacents.all { plane[it] != '#' }) {
                            changed = true
                            location to '#'
                        } else location to seat
                    }
                    '#' -> {
                        if (location.adjacents.count { plane[it] == '#' } >= 4) {
                            changed = true
                            location to 'L'
                        } else location to seat
                    }
                    else -> location to seat
                }
            }.toMap()
        } while (changed)
        return plane.values.count { it == '#' }
    }

    override fun solveProblem2(input: String): Any {
        var plane = input.split("\n").flatMapIndexed{ y, row ->
            row.mapIndexed { x, seat -> Point(x, y) to seat }
        }.toMap()

        do {
            var changed = false
            plane = plane.map { (location, seat) ->
                when(seat) {
                    'L' -> {
                        if (plane.scanFrom(location) == 0) {
                            changed = true
                            location to '#'
                        } else location to seat
                    }
                    '#' -> {
                        if (plane.scanFrom(location) >= 5) {
                            changed = true
                            location to 'L'
                        } else location to seat
                    }
                    else -> location to seat
                }
            }.toMap()
        } while (changed)
        return plane.values.count { it == '#' }
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