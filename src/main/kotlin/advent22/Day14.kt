package advent22

import shared.AdventSolution
import shared.Point
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
        val abyss = blocked.maxOf { it.y }
        while(true) {
            val next = drop(blocked, abyss)
            if(next == null) return blocked.size - rockCount
            else blocked.add(next)
        }
        return null
    }

    override fun solveProblem2(input: String): Any? {
        val blocked = input.toRocks()
        val rockCount = blocked.size
        val floor = blocked.maxOf { it.y } + 2
        while(true) {
            val next = drop2(blocked, floor)
            blocked.add(next)
            if(next == Point(500, 0)) return blocked.size - rockCount
        }
    }

    private fun drop(blocked: Set<Point>, abyss: Int): Point? {
        var current = Point(500, 0)
        while(true) {
            var next = current.below()
            if(next.y > abyss) return null
            if(blocked.contains(next)) next = next.left()
            if(blocked.contains(next)) next = next.right(2)
            if(blocked.contains(next)) return current
            current = next
        }
    }

    private fun drop2(blocked: Set<Point>, floor: Int): Point {
        var current = Point(500, 0)
        while(true) {
            var next = current.below()
            if(next.y == floor) return current
            if(blocked.contains(next)) next = next.left()
            if(blocked.contains(next)) next = next.right(2)
            if(blocked.contains(next)) return current
            current = next
        }
    }

    private fun String.toRocks(): MutableSet<Point> {
        return split("\n").filter(String::isNotBlank).flatMap { line ->
            line.split(" -> ").map { p ->
                Point(p.splitInTwo(",", String::toInt))
            }.fold(mutableListOf<Point>()) { acc, next ->
                val last = acc.lastOrNull()
                if(last != null) acc.addAll(last.straightPathTo(next))
                acc.add(next)
                acc
            }
        }.toMutableSet()
    }

}