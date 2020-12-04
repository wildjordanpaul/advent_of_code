package advent20

import shared.AdventSolution
import java.io.File

class Day3 : AdventSolution(
    mapOf("""
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent() to 7),
    mapOf("""
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent() to 336),
    input1 = loadInput("/advent20/D3_input1.txt")
) {
    companion object {
        const val TREE = '#'
    }

    override fun solveProblem1(input: String): Any {
        val lines = input.split("\n")
        var position = 0
        return lines.count { line ->
            val spot = line[position % line.length]
            position += 3
            spot == TREE
        }
    }

    override fun solveProblem2(input: String): Any {
        val lines = input.split("\n")

        val counts = listOf(
            Pair(1,1),
            Pair(3,1),
            Pair(5,1),
            Pair(7,1),
            Pair(1,2),
        ).map { (right, down) ->
            var position = 0
            var treeCounter = 0
            for (lineIndex in lines.indices step down) {
                val line = lines[lineIndex]
                if(line[position % line.length] == TREE) {
                    treeCounter += 1
                }
                position += right
            }
            treeCounter
        }
        return counts.map(Int::toLong).reduce { acc, i -> i * acc }
    }
}