package advent22

import shared.*

class Day22 : AdventSolution(
    mapOf("""
                ...#
                .#..
                #...
                ....
        ...#.......#
        ........#...
        ..#....#....
        ..........#.
                ...#....
                .....#..
                .#......
                ......#.
        
        10R5L5R10L4R5L5
    """.trimIndent() to 6032),
    5031
) {
    override fun solveProblem1(input: String): Any? {
        val (mapRaw, dirsRaw) = input.splitInTwo("\n\n")
        val map = mapRaw.split("\n").flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, c ->
                when(c) {
                    '.' -> Point(x,y) to false
                    '#' -> Point(x,y) to true
                    else -> null
                }
            }
        }.toMap()
        val topRow = map.keys.minOf { it.y }
        var position = map.keys.filter { it.y == topRow }.minBy { it.x }
        var direction = 'R'

        val dirs = dirsRaw.split(NUMBER_REGEX).filter(String::isNotBlank)
        val steps = dirsRaw.split(NOT_NUMBER_REGEX).filter(String::isNotBlank).map { it.toInt() }

        steps.forEachIndexed { i, step ->
            repeat(step) {
                var newPosition = position.navigate(direction)
                when(map[newPosition]) {
                    true -> return@repeat
                    false -> position = newPosition
                    null -> {
                        var found = false
                        var otherSide: Point?
                        while(!found) {
                            otherSide = newPosition.navigateAway(direction)
                            if(map[otherSide] == null) found = true
                            else newPosition = otherSide
                        }
                        if(map[newPosition] == false) position = newPosition
                    }
                }
            }

            dirs.getOrNull(i)?.let { dir ->
                when(dir) {
                    "R" -> direction = direction.rotateClockwise()
                    "L" -> direction = direction.rotateCounterClockwise()
                }
            }

//            printMap(map, position, direction)
        }

        return (position.y+1) * 1000 + (position.x+1) * 4 + direction.facing()
    }

    override fun solveProblem2(input: String): Any? {
        val (mapRaw, dirsRaw) = input.splitInTwo("\n\n")
        val map = mapRaw.split("\n").flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, c ->
                when(c) {
                    '.' -> Point(x,y) to false
                    '#' -> Point(x,y) to true
                    else -> null
                }
            }
        }.toMap()
        val topRow = map.keys.minOf { it.y }
        var position = map.keys.filter { it.y == topRow }.minBy { it.x }
        var direction = 'R'

        val edgesTemp = mutableMapOf<Pair<Char, Point>, Pair<Char, Point>>()
        if(input.length == 188) {
            val edgeLength = map.keys.count { it.y == 0 }
            (0 until edgeLength).forEach {
                edgesTemp['L' to Point(0, edgeLength*2-1-it)] = 'U' to Point(edgeLength*3+it, edgeLength*3-1) // A
                edgesTemp['D' to Point(it, edgeLength)] = 'D' to Point(edgeLength*3-1-it, 0) // B
                edgesTemp['D' to Point(edgeLength+it, edgeLength)] = 'R' to Point(edgeLength*2, it) // C
                edgesTemp['L' to Point(edgeLength*3-1, edgeLength - 1 - it)] = 'L' to Point(edgeLength*4-1, edgeLength*2+it) // D
                edgesTemp['L' to Point(edgeLength*3-1, edgeLength*2-1-it)] = 'D' to Point(edgeLength*3+it, edgeLength*2) // E
                edgesTemp['U' to Point(edgeLength*2 + it, edgeLength*3 -1)] = 'U' to Point(edgeLength - 1 - it, edgeLength*2 - 1) // F
                edgesTemp['U' to Point(edgeLength+it, edgeLength*2 - 1)] = 'R' to  Point(edgeLength*2, edgeLength*3-1-it) // G
            }
        } else {
            val edgeLength = map.keys.count { it.y == 0 } / 2
            (0 until edgeLength).forEach {
                edgesTemp['D' to Point(edgeLength + it, 0)] = 'R' to Point(0, edgeLength*3 + it) // A
                edgesTemp['R' to Point(edgeLength, edgeLength + it)] = 'D' to Point(it, edgeLength*2) // B
                edgesTemp['R' to Point(edgeLength, it)] = 'R' to Point(0, edgeLength*3 - 1 - it) // C
                edgesTemp['U' to Point(edgeLength + it, edgeLength * 3 - 1)] = 'L' to Point(edgeLength-1, edgeLength*3 + it) // D
                edgesTemp['L' to Point(edgeLength*3-1, it)] = 'L' to Point(edgeLength*2-1, edgeLength*3-1-it) // E
                edgesTemp['U' to Point(edgeLength*2 + it, edgeLength-1)] = 'L' to Point(edgeLength*2-1, edgeLength + it) // F
                edgesTemp['D' to Point(edgeLength*2 + it, 0)] = 'U' to Point(it, edgeLength*4 - 1) // G

            }
        }
        edgesTemp.keys.toSet().forEach { k -> edgesTemp[edgesTemp[k]!!] = k }
        val edges = edgesTemp.mapKeys { it.key.second }

        val dirs = dirsRaw.split(NUMBER_REGEX).filter(String::isNotBlank)
        val steps = dirsRaw.split(NOT_NUMBER_REGEX).filter(String::isNotBlank).map { it.toInt() }
        val paths = mutableMapOf<Point, Char>()

        steps.forEachIndexed { i, step ->
            repeat(step) {
                var newPosition = position.navigate(direction)
                when(map[newPosition]) {
                    true -> return@repeat
                    false -> position = newPosition
                    null -> {
                        val (dir, edge) = edges[position]!!
                        if(map[edge] == false) {
                            position = edge
                            direction = dir
                        } else {
                            return@repeat
                        }
                    }
                }
                paths[position] = direction.printDir()
            }

            dirs.getOrNull(i)?.let { dir ->
                when(dir) {
                    "R" -> direction = direction.rotateClockwise()
                    "L" -> direction = direction.rotateCounterClockwise()
                }
                paths[position] = direction.printDir()
            }

//            printMap(map, paths)
//            println("--------------------------------")
        }

        return (position.y+1) * 1000 + (position.x+1) * 4 + direction.facing()
    }

    private fun printMap(map: Map<Point, Boolean>, paths: Map<Point, Char>) {
        val (minY, maxY) = map.keys.minAndMaxOf { it.y }
        val (minX, maxX) = map.keys.minAndMaxOf { it.x }
        (minY..maxY).forEach { y ->
            println((minX..maxX).map { x ->
                val p = Point(x, y)
                paths[p] ?: when(map[p]) {
                    true -> '#'
                    false -> '.'
                    null -> ' '
                }
            }.joinToString(""))
        }
    }

    private fun Char.rotateClockwise(): Char = when(uppercaseChar()) {
        'R' -> 'D'
        'D' -> 'L'
        'L' -> 'U'
        'U' -> 'R'
        else -> this
    }

    private fun Char.rotateCounterClockwise(): Char = when(uppercaseChar()) {
        'R' -> 'U'
        'D' -> 'R'
        'L' -> 'D'
        'U' -> 'L'
        else -> this
    }

    private fun Char.facing() = when(uppercaseChar()) {
        'R' -> 0
        'D' -> 1
        'L' -> 2
        'U' -> 3
        else -> 0
    }

    private fun Char.printDir() = when(uppercaseChar()) {
        'R' -> '>'
        'D' -> 'v'
        'L' -> '<'
        'U' -> '^'
        else -> this
    }
}