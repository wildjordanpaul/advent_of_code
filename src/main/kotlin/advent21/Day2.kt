package advent21

import shared.*

class Day2 : AdventSolution(
    mapOf("""
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent() to 150),
    mapOf("""
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent() to 900),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return input.split(Regex("\n"))
            .map(::parseCommand)
            .fold(Point()){ point, (direction, amount) ->
                point.navigate(direction, amount)
            }.let { it.x * it.y }
    }

    override fun solveProblem2(input: String): Any? {
        return input.split(Regex("\n"))
            .map(::parseCommand)
            .fold(Point3D()) { point, (direction, amount) ->
                when(direction) {
                    "forward" -> point.add(x = amount, y = point.z * amount)
                    "down" -> point.add(z = amount)
                    "up" -> point.add(z = amount * -1)
                    else -> point
                }
            }.let{ it.x * it.y }
    }

    private fun parseCommand(s: String) = s.splitInTwo(" ").let { (a, b) -> Pair(a, b.toInt()) }
}