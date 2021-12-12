package advent21

import shared.AdventSolution
import shared.reverse
import shared.splitInTwo

class Day12 : AdventSolution(
    mapOf("""
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent() to 10,
    """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent() to 19,
    """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent() to 226),
    mapOf("""
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent() to 36,
    """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent() to 103,
    """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent() to 3509),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return input.pathCount { visited, next ->
            next.isBig() || !visited.contains(next)
        }
    }

    override fun solveProblem2(input: String): Any? {
        return input.pathCount { path, next ->
            next.isBig() || (next.isSmall() && (!path.contains(next) || path.isValid()))
        }
    }

    private fun String.pathCount(condition: (List<String>, String) -> Boolean): Int {
        val caveMap = split(Regex("\n"))
            .map { it.splitInTwo("-") }
            .flatMap { listOf(it, it.reverse()) }
            .groupBy { it.first }
            .map { (cave, children) -> cave to children.map(Pair<String, String>::second) }
            .toMap()

        val paths = caveMap.traverse("start", condition = condition)
        return paths.count()
    }

    private fun Map<String, List<String>>.traverse(
        start: String,
        visited: List<String> = emptyList(),
        condition: (List<String>, String) -> Boolean
    ): Set<List<String>> {
        val paths = mutableSetOf<List<String>>()

        get(start)?.forEach { next ->
            val newPath = visited + start
            if(next.isEnd()) {
                paths.add(newPath + next)
            } else if (condition(newPath, next)) {
                paths.addAll(traverse(next, newPath, condition))
            }
        }
        return paths
    }

    private fun List<String>.isValid(): Boolean {
        return filter{ it.isSmall() }.groupBy { it }.all { it.value.size == 1 }
    }

    private fun String.isBig() = uppercase() == this
    private fun String.isStart() = this == "start"
    private fun String.isEnd() = this == "end"
    private fun String.isSmall() = !this.isBig() && !this.isStart() && !this.isEnd()
}