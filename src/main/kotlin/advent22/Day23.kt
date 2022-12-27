package advent22

import shared.AdventSolution
import shared.Point
import shared.minAndMaxOf
import shared.splitLines

class Day23 : AdventSolution(
    mapOf("""
        ....#..
        ..###.#
        #...#.#
        .#...##
        #.###..
        ##.#.##
        .#..#..
    """.trimIndent() to 110),
    20
) {
    override fun solveProblem1(input: String): Any? {
        val (elves, _) = run(input, 10)

        val (minX, maxX) = elves.minAndMaxOf { it.x }
        val (minY, maxY) = elves.minAndMaxOf { it.y }
        return (minY..maxY).sumOf { y -> (minX..maxX).count { x -> !elves.contains(Point(x, y)) } }
    }

    override fun solveProblem2(input: String): Any? {
        val (_, roundNumber) = run(input)
        return roundNumber
    }

    private fun run(input: String, rounds: Int = -1): Pair<Set<Point>, Int> {
        var elves = input.splitLines().flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if(c == '#') Point(x, y) else null
            }
        }.toSet()

        val directionsToCheck = listOf(
            listOf("N", "NE", "NW"),
            listOf("S", "SE", "SW"),
            listOf("W", "NW", "SW"),
            listOf("E", "NE", "SE"),
        )
        var initialDirectionIndex = 0
        var roundNumber = 1

        while(rounds == -1 || roundNumber <= rounds) {
            val proposals = elves.associateWith { elf ->
                var di = initialDirectionIndex
                var checksLeft = directionsToCheck.size
                if(elf.adjacents.none { elves.contains(it) })
                    return@associateWith elf

                while(checksLeft > 0) {
                    val directions = directionsToCheck[di % directionsToCheck.size]
                    val points = directions.map { dir -> elf.navigate(dir) }
                    if(points.none { elves.contains(it) })
                        return@associateWith points.first()

                    checksLeft -= 1
                    di += 1
                }
                elf
            }
            val conflicts = proposals.values.groupBy { it }.filter { it.value.size > 1 }.keys
            var allTheSame = true
            elves = proposals.map { (k,v) ->
                if(k != v) allTheSame = false
                if(conflicts.contains(v)) k else v
            }.toSet()
            if(allTheSame) return elves to roundNumber
            initialDirectionIndex += 1
            printMap(elves)
            roundNumber += 1
        }
        return elves to roundNumber
    }

    fun printMap(elves: Set<Point>) {
        val (minX, maxX) = elves.minAndMaxOf { it.x }
        val (minY, maxY) = elves.minAndMaxOf { it.y }
        (minY-3..3+maxY).forEach { y ->
            println((minX-3..3+maxX).joinToString("") { x-> if(elves.contains(Point(x, y))) "#" else "." })
        }
        println("------------------------------------------------------")
    }
}