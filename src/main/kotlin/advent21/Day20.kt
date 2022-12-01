package advent21

import shared.AdventSolution
import shared.Point
import shared.splitInTwo

class Day20 : AdventSolution(
    mapOf(
        """
            ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
            
            #..#.
            #....
            ##..#
            ..#..
            ..###
        """.trimIndent() to 35
    ),
    mapOf(

    ),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val (algo, imageRaw) = input.splitInTwo("\n\n")
        val image = imageRaw.split("\n").flatMapIndexed { row: Int, s: String ->
            s.mapIndexed { col, c -> Pair(Point(col, row), c == '#')  }
        }.toMap()
        val img2 = image.enhance(algo)
        img2.print()
        val img3 = img2.enhance(algo)
        img3.print()
        return img3.values.count { it }
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }

    private fun Map<Point, Boolean>.enhance(algo: String): Map<Point, Boolean> {
        return keys.flatMap { it.adjacents }.flatMap{ it.adjacents }.toSet().associateWith {
            val index = listOf(
                it.add(-1, -1),
                it.add(0, -1),
                it.add(1, -1),
                it.add(-1, 0),
                it,
                it.add(1, 0),
                it.add(-1, 1),
                it.add(0, 1),
                it.add(1, 1)
            ).map { p ->
                if (this[p] == true)
                    '1'
                else '0'
            }.joinToString("").toInt(2)
            algo[index] == '#'
        }
    }

    private fun Map<Point, Boolean>.print() {
        println((keys.minOf { it.y }..keys.maxOf { it.y }).map { y ->
            (keys.minOf{ it.x }..keys.maxOf { it.x }).map {x ->
                if(this[Point(x,y)] == true) '#' else '.'
            }.joinToString("")
        }.joinToString("\n"))
    }


}