package advent22

import shared.AdventSolution
import shared.Point3D
import shared.Point3D.Companion.boundaries
import shared.pop
import shared.splitLines

class Day18 : AdventSolution(
    mapOf("""
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent() to 64),
    58
) {
    override fun solveProblem1(input: String): Any? {
        return input.splitLines("\n").map(::Point3D).toSet().run { flatMap(Point3D::directAdjacents) - this }.size
    }

    override fun solveProblem2(input: String): Any? {
        val points = input.splitLines("\n").map(::Point3D).toSet()
        val sides = (points.flatMap(Point3D::directAdjacents) - points)
        val (min, max) = sides.boundaries()
        val queue = LinkedHashSet<Point3D>().also { it.add(min) }
        val steam = mutableSetOf<Point3D>()
        while(queue.isNotEmpty()) {
            queue.pop()
                ?.also(steam::add)
                ?.directAdjacents?.filter { next ->
                    next.x in min.x..max.x &&
                    next.y in min.y..max.y &&
                    next.z in min.z..max.z &&
                    !steam.contains(next) &&
                    !points.contains(next)
                }
                ?.run(queue::addAll)
        }
        val pockets = sides - steam
        return sides.size - pockets.size
    }
}