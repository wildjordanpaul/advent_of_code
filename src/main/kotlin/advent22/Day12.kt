package advent22

import shared.*

class Day12 : AdventSolution(
    mapOf("""
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent() to 31),
    29
) {

    override fun solveProblem1(input: String): Any? {
        val map = input.toPointMap(String::first)
        val graph = map.toGraph { e1, e2 ->
            e2.second.height() - e1.second.height() <= 1
        }
        val start = map.findKey('S')
        val end = map.findKey('E')

        val path = graph.shortestPath(start, end)!!
        return path.size
    }

    override fun solveProblem2(input: String): Any? {
        val map = input.toPointMap(String::first)
        val graph = map.toGraph { e1, e2 ->
            e2.second.height() - e1.second.height() <= 1
        }

        val end = map.findKey('E')
        return map.keys
            .filter { map[it]!!.height() == 'a' }
            .mapNotNull { start -> graph.shortestPath(start, end) }
            .minOf { it.size }
    }

    private fun Char.height() = when(this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }
}