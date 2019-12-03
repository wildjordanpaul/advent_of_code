package advent18

import shared.AdventSolution
import kotlin.math.abs

data class D6Point(
    val id: Char,
    val x: Int,
    val y: Int
)

fun main(args: Array<String>) = object : AdventSolution(
    mapOf(
        "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9" to "E: 5, 5 = 17"
    ),
    mapOf(
        "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9" to "72"
    ),
    "267, 196\n76, 184\n231, 301\n241, 76\n84, 210\n186, 243\n251, 316\n265, 129\n142, 124\n107, 134\n265, 191\n216, 226\n67, 188\n256, 211\n317, 166\n110, 41\n347, 332\n129, 91\n217, 327\n104, 57\n332, 171\n257, 287\n230, 105\n131, 209\n110, 282\n263, 146\n113, 217\n193, 149\n280, 71\n357, 160\n356, 43\n321, 123\n272, 70\n171, 49\n288, 196\n156, 139\n268, 163\n188, 141\n156, 182\n199, 242\n330, 47\n89, 292\n351, 329\n292, 353\n290, 158\n167, 116\n268, 235\n124, 139\n116, 119\n142, 259"
) {



    private fun String.toGrid(): List<D6Point> {
        var id = 'A'
        return split("\n").map {
            val points = it.split(", ")
            D6Point(id++, points[0].toInt(), points[1].toInt())
        }
    }

    override fun solveProblem1(input: String): String {
        val grid = input.toGrid()
        val xs = grid.map { it.x }
        val ys = grid.map { it.y }
        val invalidX = listOf( xs.min(), xs.max() )
        val invalidY = listOf( ys.min(), ys.max() )
        val counter = mutableMapOf<D6Point, Int>()
        val invalidIDs = mutableSetOf<Char>()
        (ys.min()!! .. ys.max()!!).forEach { y ->
            (xs.min()!! .. xs.max()!!).forEach { x ->
                val distances = grid.map { p -> p to abs(p.x-x) + abs(p.y-y) }.toMap()
                val minDistance = distances.minBy { it.value }!!.value
                val minDistances = distances.filter { it.value == minDistance }
                if(minDistances.size == 1) {
                    val point = minDistances.entries.first().key
                    print("${point.id} ")
                    val currentCount = counter[point] ?: 0
                    counter[point] = currentCount + 1
                    if(invalidY.contains(y) || invalidX.contains(x)) {
                        invalidIDs.add(point.id)
                    }
                } else {
                    print(". ")
                }
            }
            println()
        }
        val best = counter.entries.filter {
            !invalidIDs.contains(it.key.id)
        }.maxBy { it.value }!!
        return "${best.key.id}: ${best.key.x}, ${best.key.y} = ${best.value}"
    }

    override fun solveProblem2(input: String): String {
        val grid = input.toGrid()
        val xs = grid.map { it.x }
        val ys = grid.map { it.y }
        var counter = 0
        (ys.min()!! .. ys.max()!!).forEach y@{ y ->
            (xs.min()!! .. xs.max()!!).forEach x@{ x ->
                val sumDistance = grid.sumBy { p -> abs(p.x-x) + abs(p.y-y) }
                if(sumDistance < 10000) counter ++
            }
        }
        return counter.toString()
    }

}.solve()