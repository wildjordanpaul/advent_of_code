package advent22

import shared.AdventSolution
import shared.Point
import shared.flatFold
import shared.splitInTwo

class Day14 : AdventSolution(
    mapOf("""
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent() to 24),
    93
) {
    override fun solveProblem1(input: String): Any? {
        val blocked = input.toRocks()
        val rockCount = blocked.size
        val abyss = blocked.maxOf(Point::y)
        while(true) {
            drop(abyss) { !blocked.contains(it) }
                ?.let(blocked::add)
                ?: return blocked.size - rockCount
        }
        return null
    }

    override fun solveProblem2(input: String): Any? {
        val blocked = input.toRocks()
        val rockCount = blocked.size
        val floor = blocked.maxOf { it.y } + 2
        while(true) {
            val next = drop {
                it.y != floor && !blocked.contains(it)
            }?.also(blocked::add)
            if(next == Point(500, 0)) return blocked.size - rockCount
        }
    }

    private fun drop(abyss: Int? = null, isOpen: (Point) -> Boolean): Point? {
        var current = Point(500, 0)
        while(true) {
            var below = current.below()
            if(abyss != null && below.y > abyss) return null
            val next = listOf(below, below.left(), below.right())
                .firstOrNull(isOpen) ?: return current
            current = next
        }
    }

    private fun String.toRocks(): MutableSet<Point> {
        return split("\n").filter(String::isNotBlank).flatMap { line ->
            line.split(" -> ")
                .map { p -> Point(p.splitInTwo(",", String::toInt)) }
                .flatFold { p1, p2 -> p1.straightPathTo(p2) }
        }.toMutableSet()
    }

}