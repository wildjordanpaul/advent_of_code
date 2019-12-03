package advent18

import shared.AdventSolution
import shared.Point
import java.math.BigInteger

fun main(args: Array<String>) = object : AdventSolution(
        mapOf(
            "510 10 10" to "114"
        ),
        mapOf(

        ),
        "4845 6 770"
) {

    override fun solveProblem1(input: String): Any? {
        val inputs = input.split(" ").map(String::toInt)
        val depth = inputs[0].toBigInteger()
        val target = Point(inputs[1], inputs[2])
        val grid = mutableMapOf<Point, BigInteger>()
        val xFactor = 48271.toBigInteger()
        val yFactor = 16807.toBigInteger()
        val modFactor = 20183.toBigInteger()
        val three = 3.toBigInteger()
        var counter = 0
        (0..target.y).forEach { y ->
            (0..target.x).forEach { x ->
                val pt = Point(x,y)
                val geoIndex = when {
                    pt == target -> BigInteger.ZERO
                    y == 0 -> x.toBigInteger().times(yFactor)
                    x == 0 -> y.toBigInteger().times(xFactor)
                    else -> grid[pt.left()]!!.times(grid[pt.above()]!!)
                }
                grid[pt] = geoIndex.add(depth).mod(modFactor)
                counter += grid[pt]!!.mod(three).toInt()
            }
        }

        return counter.toString()
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }

    fun Point.shortestPathTo(dest: Point, openSpaces: Set<Point>): List<Point> {
        val paths = mutableMapOf<Point, List<Point>>()

        fun iteratePaths(point: Point) {
            val pathSoFar = paths[point] ?: emptyList()
            point.adjacents.filter { adjacent ->
                if(openSpaces.contains(adjacent) && !pathSoFar.contains(adjacent)) {
                    val prevPath = paths[adjacent]

                    if(prevPath == null || prevPath.size > pathSoFar.size + 1) {
                        val newPathSoFar = (pathSoFar + adjacent)
                        paths[adjacent] = newPathSoFar
                        return@filter true
                    }
                }

                false
            }.forEach(::iteratePaths)
        }

        iteratePaths(this)
        return paths[dest] ?: emptyList()
    }

}.solve()

