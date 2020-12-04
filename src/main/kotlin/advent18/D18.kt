package advent18

import shared.AdventSolution
import shared.Point

fun main() = object : AdventSolution(
        mapOf(
            """
                .#.#...|#.
                .....#|##|
                .|..|...#.
                ..|#.....#
                #.#|||#|#|
                ...#.||...
                .|....|...
                ||...#|.#|
                |.||||..|.
                ...#.|..|.
            """.trimIndent() to "37 * 31 = 1147"
        ),
        mapOf(

        ),
        """
            ....#......|.#........###|...||.#|||.||.|...|.#.|.
            |#|#....|#...||.|#.#|.|.|..#...||.#|#.|.#||#||.|#.
            .....##...#..###..##.|.|....|..#.|...#.#.....|.|..
            |....#|||#..||##....||#||.|..|...|..#....#|#....|#
            |......|......|.#...|.....|..|.#...#.#..#|.....#|.
            |#...#....#...#.|..#..|...|#..|.#.......#..#....|#
            ....|#.|#...........##...||......##...||#...#..|.|
            .#.....|..|...|#..##||..#.#...#...#|.#...#.|....#.
            .##|.....|||.....||.#...#...#...#......##...||#...
            .||#.|#..|.....#.|.|..........|.#..|##...||...|#..
            |......|..#...#.##||..||..#.|..|.|....##|..|..|.|.
            |#...####.#.|.....|#..#....|#.#..|.#.#|####.#..|..
            .#|.....#.|....|###..#||...#|...||.|.#|#.....|##..
            #.|..#|..#...||#.#|...#.##|..|..#...|#.....|..#|..
            #.|.....##..||##....|..|.|.|..##.#..|||.....|.....
            ......##|..|#.|#||...#.|..#..|.#....|..#....#..|##
            |........|#.#.|.##...#|..|##.....|##.|.#....#.#...
            #.#..#..|.........#|.##.......|...|.#..#.#|####.#.
            .....#||#..|......#|.....#..|||..##.......#.#..#.#
            #...........#|..#..|.||.|||.|....#||....|#..##.#..
            .|...#..##|#...#.||.|##.|..#.||.#.#.#.###...#...#|
            |#|...|.......#..#..#....|.###..|.||...|.#...|....
            ..#.#......|..|.||#.||.......|..#|.#.|..|.#..#.#.#
            #..#...|...|..|..#....|..|...|#|...#......#...#...
            |...|.|.||#...|...|....|...#.|.#.|.|.|#..|..###.#.
            ..|.|.....|##...##....|..|.....||...#..||##......|
            .#.#....|.##|....|.|..|.|##..#...|##|.#.#.##|....#
            ..#|.|.....|.#....|...||....|.#..|#.#.|#|.||.||...
            .|##.|.#|#|...|...##.||.....|.#|....|.....|#..||..
            |.#|...||....#|..#...|.....|.....#|...|#.#|..|....
            .|...|....###.|....||##||..|#||.#|##|..|.#.......|
            ...#.||###|#|.#.|...#...|.##.|.||#..#.......||.#.#
            .#|....|#.|.###.##|...|#...#.||..##...#.#|##...#.#
            ..|#|#..#..#..#|#.....|.#.|...|..#.#......###..|.|
            #.|.|..#.#.#.#.....|........|#.||..#......#|.....#
            ...#.#|#|.|.###|#...#.|......#|.......##||......#.
            .#|#.|#..#|...|.|...##|.#....|#........|..|.#.#.#.
            ..|.##.|#..|...#|.#...#........|.|#|.#.|.|..|#|.#.
            ...#.#.#||.|||...|#||..##.....###......#..#|||#..#
            ...#.....#||##.|..#.#|......||..#..#..#..|..|..|..
            ####.|....|.......|.|.#...|...#.#.......|.|.#...||
            ..|.|#|.#..##..##...#.....|...|...#|.|...#|..#..##
            |...##.#|.........#..||#..||.#....||#..|..||....#|
            .#..........#|#.#|#.|...|#....|..|...|...##....|#.
            |.|#..|..|......#..|...|..##|||#...|..##|...#.|#..
            ||#.||.#|.|...#.........#...|.|##.#.|#||.|.#|..#..
            |..|..|..#....#...|.......#|.........|#....#|....|
            ##..###......#|.........|.......|...||.......#|..#
            |..............#.......#...#|.|||..#..|..#........
            ...|||.#.|.#.|..#.....##|....###.#.|....|.......|.
        """.trimIndent()
) {

    override fun solveProblem1(input: String): String {
        return solve(input)
    }

    override fun solveProblem2(input: String): String {
        return solve(input, 1000000000)
    }

    private fun solve(input: String, years: Long = 10): String {
        val grid = mutableMapOf<Point, Char>()
        val lines = input.split("\n")
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                val c2 = when(c) {
                    '|' -> 'w'
                    '#' -> 'y'
                    '.' -> 'o'
                    else -> TODO()
                }
                grid[Point(x,y)] = c2
            }
        }

        fun Char?.isWood() = this == 'w' || this == 'W'
        fun Char?.isYard() = this == 'y' || this == 'Y'
        fun Point.enoughWood(): Boolean {
            var counter = 0
            adjacents.forEach {
                if(grid[it].isWood())
                    counter += 1
                if(counter >= 3)
                    return true
            }

            return false
        }

        fun Point.enoughYards(): Boolean {
            var counter = 0
            adjacents.forEach {
                if(grid[it].isYard())
                    counter += 1
                if(counter >= 3)
                    return true
            }

            return false
        }

        fun Point.sustainableYard(): Boolean {
            var yardFound = false
            var woodFound = false

            adjacents.forEach {
                val v = grid[it]
                if(yardFound || v.isYard()) yardFound = true
                if(woodFound || v.isWood()) woodFound = true
                if(yardFound && woodFound) return true
            }
            return false
        }

        fun woodCount() = grid.count { (_, v) -> v.isWood() }
        fun yardCount() = grid.count { (_, v) -> v.isYard() }

        val turner =  years/100000
        var turnPrinter = turner

        fun currentHash() = grid.entries.sortedBy { it.key.hashCode() }.map{ it.value }.joinToString().hashCode()

        val hashCodes = mutableListOf<Int>()
        val values = mutableListOf<Pair<Int, Int>>()

        (1..years).forEach { turn ->

            val ch = currentHash()
            if(ch in hashCodes) {
                val prefix = hashCodes.indexOf(ch)
                val l = hashCodes.size - prefix
                val pair = values[((years - prefix) % l).toInt() + prefix]
                return "${pair.first} * ${pair.second} = ${pair.first*pair.second}"
            }

            hashCodes.add(ch)
            values.add(Pair(woodCount(), yardCount()))

            grid.iterator().forEach { entry ->
                when(entry.value) {
                    'o' -> if(entry.key.enoughWood()) entry.setValue('O')
                    'w' -> if(entry.key.enoughYards()) entry.setValue('W')
                    'y' -> if(!entry.key.sustainableYard()) entry.setValue('Y')
                }
            }

            grid.iterator().forEach { entry ->
                when(entry.value) {
                    'O' -> entry.setValue('w')
                    'W' -> entry.setValue('y')
                    'Y' -> entry.setValue('o')
                }
            }

            if(turnPrinter <= 0) {
                turnPrinter = turner
                println("Complete: ${turn.toDouble()/years*100} %, Minute: $turn, Woods: ${woodCount()}, Yards: ${yardCount()}")

//                (0 until lines.size).forEach { y ->
//                    println()
//                    (0 until lines.first().length).forEach { x ->
//                        val c = grid[Point(x,y)]
//                        print(when(c) {
//                            'o' -> '.'
//                            'w' -> '|'
//                            'y' -> '#'
//                            else -> c
//                        })
//                    }
//                }
//                println()
            }
            turnPrinter -= 1
        }

        return "${woodCount()} * ${yardCount()} = ${yardCount()*woodCount()}"
    }

}.solve()

