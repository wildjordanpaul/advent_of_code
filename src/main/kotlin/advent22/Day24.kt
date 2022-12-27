package advent22

import shared.AdventSolution
import shared.Point
import shared.splitLines

class Day24 : AdventSolution(
    mapOf(
    """
        #.######
        #>>.<^<#
        #.<..<<#
        #>v.><>#
        #<^v^^>#
        ######.# 
    """.trimIndent() to 18),
    54
) {
    override fun solveProblem1(input: String): Any? {
        val mapState = MapState(input)
        return mapState.navigate(mapState.start, mapState.end)
    }

    override fun solveProblem2(input: String): Any? {
        val mapState = MapState(input)
        return listOf(
            mapState.start to mapState.end,
            mapState.end to mapState.start,
            mapState.start to mapState.end
        ).sumOf {
            mapState.navigate(it.first, it.second)
        }
    }

    private class MapState(input: String) {
        val walls = mutableSetOf<Point>()
        val openings = mutableSetOf<Point>()
        var blizzards = input.splitLines().flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                when(c) {
                    '#' -> walls.add(Point(x, y))
                    '.' -> openings.add(Point(x, y))
                    else -> return@mapIndexedNotNull Blizzard(Point(x, y), c)
                }
                null
            }
        }

        val maxX = walls.maxOf { it.x }
        val maxY = walls.maxOf { it.y }

        val start = openings.minBy { it.y }
        val end = openings.maxBy { it.y }

        fun navigate(start: Point, end: Point): Int {
            var minutes = 0
            var validPaths = setOf(listOf(start))

            while(true) {
                minutes += 1
                blizzards = blizzards.map { b ->
                    var newPoint = b.point.navigate(b.direction)
                    if(newPoint.x == 0) newPoint = Point(maxX - 1, newPoint.y)
                    if(newPoint.y == 0) newPoint = Point(newPoint.x, maxY - 1)
                    if(newPoint.x == maxX) newPoint = (Point(1, newPoint.y))
                    if(newPoint.y == maxY) newPoint = (Point(newPoint.x, 1))

                    Blizzard(newPoint, b.direction)
                }
                val blizzardPoints = blizzards.map { it.point }.toSet()
                validPaths = validPaths.flatMap { path ->
                    val variations = mutableListOf<List<Point>>()
                    val last = path.last()
                    if(!blizzardPoints.contains(last))
                        variations.add(path)
                    last.directAdjacents
                        .filter { n ->
                            if(n == end) {
                                return minutes
                            }
                            n.x in 1 until maxX
                                    && n.y in 1 until maxY
                                    && !blizzardPoints.contains(n)
                        }
                        .forEach {
                            variations.add(path + it)
                        }
                    variations
                }.filter{ it.isNotEmpty() }.distinctBy { it.last() }.toSet()
            }
        }
    }

    private data class Blizzard(val point: Point, val direction: Char)
}