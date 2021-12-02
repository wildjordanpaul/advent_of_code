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
        var point = Point(0,0)
        input.split(Regex("\n")).forEach{
            val (direction, amount) = it.splitInTwo(" ")
            point = point.navigate(when(direction) {
                "forward" -> "R"
                "down" -> "D"
                "up" -> "U"
                else -> ""
            }, amount.toInt())
        }
        return point.x * point.y
    }

    override fun solveProblem2(input: String): Any? {
        var point = Point3D(0, 0, 0)
        input.split(Regex("\n")).forEach{
            val (direction, amount) = it.splitInTwo(" ")
            point = when(direction) {
                "forward" -> point.add(x = amount.toInt(), y = point.z * amount.toInt())
                "down" -> point.add(z = amount.toInt())
                "up" -> point.add(z = -1 * amount.toInt())
                else -> point
            }
        }
        return point.x * point.y
    }
}