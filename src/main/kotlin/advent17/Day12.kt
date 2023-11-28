package advent17

import shared.AdventSolution
import shared.splitInTwo
import java.util.Queue

class Day12 : AdventSolution(
    mapOf("""
        0 <-> 2
        1 <-> 1
        2 <-> 0, 3, 4
        3 <-> 2, 4
        4 <-> 2, 3, 6
        5 <-> 6
        6 <-> 4, 5
    """.trimIndent() to 6),
    2
) {
    override fun solveProblem1(input: String): Any? {
        val seen = mutableSetOf("0")
        val map = input.split("\n").filter(String::isNotBlank).associate { line ->
            val (k, v) = line.splitInTwo(" <-> ")
            k to v.split(", ").filter(String::isNotBlank)
        }
        val queue = mutableSetOf("0")
        while(queue.isNotEmpty()) {
            val next = queue.last()
            queue.remove(next)
            seen.add(next)
            map[next]?.let { list ->
                queue.addAll(list.filter{ !seen.contains(it) })
            }
        }
        return seen.count()
    }

    override fun solveProblem2(input: String): Any? {
        val groups = mutableSetOf<MutableSet<String>>()
        val map = input.split("\n").filter(String::isNotBlank).associate { line ->
            val (k, v) = line.splitInTwo(" <-> ")
            k to v.split(", ").filter(String::isNotBlank)
        }
        map.forEach { (k, _) ->
            if(groups.any { group -> group.contains(k) }) return@forEach
            val seen = mutableSetOf<String>()
            val queue = mutableSetOf(k)
            while(queue.isNotEmpty()) {
                val next = queue.last()
                queue.remove(next)
                seen.add(next)
                map[next]?.let { list ->
                    queue.addAll(list.filter{ !seen.contains(it) })
                }
            }
            groups.add(seen)
        }
        return groups.size

    }
}