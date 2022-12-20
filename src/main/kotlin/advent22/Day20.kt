package advent22

import shared.AdventSolution
import shared.splitInts
import java.math.BigInteger

class Day20 : AdventSolution(
    mapOf("""
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent() to 3),
    "1623178306"
) {
    override fun solveProblem1(input: String): Any? {
        val nodes = input.splitInts().mapIndexed { i, v -> Node(v.toBigInteger(), i) }
        val node0 = nodes.first { it.value == BigInteger.ZERO }
        val mixed = mix(nodes)
        val index0 = mixed.indexOf(node0)
        return listOf(1000, 2000, 3000).sumOf { mixed[(index0 + it).mod(mixed.size)].value }
    }

    override fun solveProblem2(input: String): Any? {
        val key = "811589153".toBigInteger()
        val nodes = input.splitInts().mapIndexed { i, v -> Node(v.toBigInteger().times(key), i) }
        val node0 = nodes.first { it.value == BigInteger.ZERO }
        val mixed = (1..10).fold(nodes) { next, _ -> mix(nodes, next) }
        val index0 = mixed.indexOf(node0)
        return listOf(1000, 2000, 3000).sumOf { mixed[(index0 + it).mod(mixed.size)].value }
    }

    private data class Node(val value: BigInteger, val initialIndex: Int)

    private fun mix(originals: List<Node>, current: List<Node> = originals): List<Node> {
        val mixed = current.toMutableList()
        originals.forEach { node ->
            val newIndex = (mixed.indexOf(node).toBigInteger() + node.value).mod((originals.size - 1).toBigInteger()).toInt()
            mixed.remove(node)
            if(newIndex == 0) mixed.add(node)
            else mixed.add(newIndex, node)
        }
        return mixed
    }
}