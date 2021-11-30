package advent18

import shared.AdventSolution
import shared.Point

fun main() = object : AdventSolution(
        mapOf(
            """
                #######
                #.G...#
                #...EG#
                #.#.#G#
                #..G#E#
                #.....#
                #######
            """.trimIndent() to  "47 * 590 = 27730",
            """
                #######
                #G..#E#
                #E#E.E#
                #G.##.#
                #...#E#
                #...E.#
                #######
            """.trimIndent() to  "37 * 982 = 36334",
            """
                #######
                #E..EG#
                #.#G.E#
                #E.##E#
                #G..#.#
                #..E#.#
                #######
            """.trimIndent() to "46 * 859 = 39514",
            """
                #######
                #E.G#.#
                #.#G..#
                #G.#.G#
                #G..#.#
                #...E.#
                #######
            """.trimIndent() to "35 * 793 = 27755",
            """
                #######
                #.E...#
                #.#..G#
                #.###.#
                #E#G#G#
                #...#G#
                #######
            """.trimIndent() to "54 * 536 = 28944",
            """
                #########
                #G......#
                #.E.#...#
                #..##..G#
                #...##..#
                #...#...#
                #.G...G.#
                #.....G.#
                #########
            """.trimIndent() to "20 * 937 = 18740"
        ),
        mapOf(
            """
                #######
                #.G...#
                #...EG#
                #.#.#G#
                #..G#E#
                #.....#
                #######
            """.trimIndent() to "29 * 172 = 4988",
            """
                #######
                #E..EG#
                #.#G.E#
                #E.##E#
                #G..#.#
                #..E#.#
                #######
            """.trimIndent() to "33 * 948 = 31284",
            """
                #######
                #E.G#.#
                #.#G..#
                #G.#.G#
                #G..#.#
                #...E.#
                #######
            """.trimIndent() to "37 * 94 = 3478",
            """
                #######
                #.E...#
                #.#..G#
                #.###.#
                #E#G#G#
                #...#G#
                #######
            """.trimIndent() to "39 * 166 = 6474",
            """
                #########
                #G......#
                #.E.#...#
                #..##..G#
                #...##..#
                #...#...#
                #.G...G.#
                #.....G.#
                #########
            """.trimIndent() to "30 * 38 = 1140"

        ),
        """
            ################################
            #######..##########.##.G.##.####
            #######...#######........#..####
            #######..G.######..#...##G..####
            ########..G###........G##...####
            ######....G###....G....###.#####
            ######....####..........##..####
            #######...###...........##..E..#
            #######.G..##...........#.#...##
            ######....#.#.....#..GG......###
            #####..#..G...G........G.#....##
            ##########.G.......G........####
            #########.G.G.#####EE..E...#####
            #########....#######.......#####
            #########...#########.......####
            ########....#########...G...####
            #########...#########.#....#####
            ##########..#########.#E...E####
            ######....#.#########........#.#
            ######..G.#..#######...........#
            #####.........#####.E......#####
            ####........................####
            ####.........G...####.....######
            ##................##......######
            ##..........##.##.........######
            #............########....E######
            ####..........#######.E...######
            ####........#..######...########
            ########....#.E#######....######
            #########...####################
            ########....####################
            ################################
        """.trimIndent()
) {

    override fun solveProblem1(input: String): String {
        return solveWith(input) ?: "ERROR"
    }

    override fun solveProblem2(input: String): String {
        var elfPower = 4
        while(true) {
            val result = solveWith(input, elfPower, true)
            if(result != null)
                return result
            else
                println("Not Strong enough: $elfPower")
            elfPower += 1
        }
    }

    private fun solveWith(input: String, elfPower: Int = 3, failOnElfDeath: Boolean = false): String? {
        val rows = input.split("\n")
        val width = rows.first().length
        val height = rows.size
        val spaces = mutableSetOf<Point>()
        val units = mutableListOf<D15Unit>()
        rows.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                val p = Point(x,y)
                if(c != '#') spaces.add(p)
                if(c == 'G') units.add(D15Unit(false, x, y))
                if(c == 'E') units.add(D15Unit(true, x, y, elfPower))
            }
        }

        var roundsComplete = 0
        while(true) {

            val occupied = units.map { it.point }.toSet()
            val openSpaces = spaces.filter { !occupied.contains(it) }.toMutableSet()
            val unitMap = units.associateBy { it.point }

            println()
            println("=== round $roundsComplete ===")
            (0 until height).forEach { y ->
                val unitsOnLine = mutableListOf<D15Unit>()
                (0 until width).forEach { x ->
                    val pt = Point(x,y)
                    val unit = unitMap[pt]
                    print(when {
                        unit != null -> unit.c
                        openSpaces.contains(pt) -> '.'
                        else -> '#'
                    })
                    if(unit != null) unitsOnLine.add(unit)
                }
                if(unitsOnLine.size > 0) {
                    print(" ")
                    print(unitsOnLine.joinToString(" ") { "[${it.c}: ${it.hp}]" })
                }
                println()
            }

            val deadUnits = mutableSetOf<D15Unit>()
            units.sortedWith(compareBy({ it.startingY }, { it.startingX })).forEach { unit ->

                if(deadUnits.contains(unit)) return@forEach
                val targets = units
                    .filter { unit2 -> unit2.isElf != unit.isElf }
                    .associateBy { it.point }

                if(targets.isEmpty()) {
                    val points = units.sumOf { it.hp }
                    return "$roundsComplete * $points = ${roundsComplete * points}"
                }

                fun getBestTarget(): D15Unit? {
                    return unit.adjacentPoints
                        .mapNotNull { targets[it] }
                        .minWithOrNull(compareBy({ it.hp }, { it.startingY }, { it.startingX }))
                }

                var bestImmediateTarget = getBestTarget()

                if(bestImmediateTarget == null) {
                    targets.values
                        .flatMap { unit2 -> unit2.adjacentPoints
                            .filter { openSpaces.contains(it) }
                            .map { unit2 to D15Directions(it, unit.point.shortestPathTo(it, openSpaces)) }
                            .filter { it.second.paths.isNotEmpty() }
                        }
                        .minWithOrNull(compareBy(
                            { it.second.distance },
                            { it.second.y },
                            { it.second.x }
                        ))?.let { (_, vector) ->
                            vector.paths.firstOrNull()
                        }?.let { nextPoint ->
                            openSpaces.add(Point(unit.x, unit.y))
                            when {
                                nextPoint.y < unit.y -> unit.y -= 1
                                nextPoint.x < unit.x -> unit.x -= 1
                                nextPoint.x > unit.x -> unit.x += 1
                                nextPoint.y > unit.y -> unit.y += 1
                            }
                            openSpaces.remove(Point(unit.x, unit.y))
                            bestImmediateTarget = getBestTarget()
                        }
                }

                bestImmediateTarget?.let {
                    it.hp -= unit.attackPower
                    if(it.isDead) {
                        if(it.isElf && failOnElfDeath)
                            return null
                        units.remove(it)
                        deadUnits.add(it)
                        openSpaces.add(it.point)
                    }
                }
            }

            units.forEach { it.commit() }

            roundsComplete += 1
        }
    }

    fun Point.adjacents() = listOf(
        Point(x, y-1), Point(x-1, y), Point(x+1, y), Point(x, y+1)
    )

    fun Point.shortestPathTo(dest: Point, openSpaces: Set<Point>): List<Point> {
        val paths = mutableMapOf<Point, List<Point>>()

        fun iteratePaths(point: Point) {
            val pathSoFar = paths[point] ?: emptyList()
            point.adjacents().filter { adjacent ->
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

data class D15Directions(val destination: Point, val paths: List<Point>) {
    val x = destination.x
    val y = destination.y
    val distance = paths.size
}

data class D15Unit(
    val isElf: Boolean,
    var x: Int,
    var y: Int,
    val attackPower: Int = 3,
    var hp: Int = 200,
    var startingX: Int = x,
    var startingY: Int = y
) {
    val c: Char = if(isElf) 'E' else 'G'
    val point: Point
        get() = Point(x,y)

    val adjacentPoints: List<Point>
        get() = listOf(
            Point(x, y-1), Point(x-1, y), Point(x+1, y), Point(x, y+1)
        )

    val isDead: Boolean
        get() = hp <= 0

    fun commit() {
        startingX = x
        startingY = y
    }
}

