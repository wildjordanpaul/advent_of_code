package advent21

import shared.AdventSolution
import shared.Point
import shared.splitInTwo

class Day13 : AdventSolution(
    mapOf("""
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0
        
        fold along y=7
        fold along x=5
    """.trimIndent() to 17),
    mapOf(),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val (points, folds) = input.parseDay13()
        val (axis, value) = folds.first()
        return points.fold(axis, value.toInt()).count()
    }

    override fun solveProblem2(input: String): Any? {
        var (points, folds) = input.parseDay13()
        folds.forEach { (axis, value) ->
            points = points.fold(axis, value.toInt())
        }

        return "\n" + (0..points.maxOf { it.y }).joinToString("\n") { y ->
            (0..points.maxOf { it.x }).joinToString("") { x ->
                if (points.contains(Point(x, y))) "#" else "."
            }
        }
    }

    private fun String.parseDay13(): Pair<Set<Point>, List<Pair<String, String>>> {
        val (pointsInput, foldsInput) = splitInTwo("\n\n") { it.split(Regex("\n"))}
        val points = pointsInput.map { p -> Point(p.splitInTwo(",") { it.toInt() }) }.toSet()
        val folds = foldsInput.map { f -> f.removePrefix("fold along ").splitInTwo("=") }
        return Pair(points, folds)
    }

    private fun Set<Point>.fold(axis: String, value: Int): Set<Point> {
        return map { point ->
            when(axis) {
                "x" -> {
                    if(point.x > value)
                        Point(point.x - 2 * (point.x - value), point.y)
                    else point
                }
                "y" -> {
                    if(point.y > value)
                        Point(point.x, point.y - 2 * (point.y - value))
                    else point
                }
                else -> point
            }
        }.toSet()
    }


}