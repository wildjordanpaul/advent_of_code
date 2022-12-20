package advent17

import shared.AdventSolution
import shared.Point
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

class Day11 : AdventSolution(
    mapOf(
        "ne,ne,ne" to 3,
        "ne,ne,sw,sw" to 0,
        "ne,ne,s,s" to 2,
        "se,sw,se,sw,sw" to 3
    ),
    1
) {
    override fun solveProblem1(input: String): Any? {
        val dirs = input.trim().split(",").filter(String::isNotBlank)
        val map = dirs.groupBy { it }.mapValues { it.value.size }.toMutableMap()
        val map2 = map.mapValues { (dir, count) ->
            val steps = when(dir) {
                "ne" -> count - (map["sw"] ?: 0)
                "n" -> count - (map["s"] ?: 0)
                "nw" -> count - (map["se"] ?: 0)
                "sw" -> count - (map["ne"] ?: 0)
                "s" -> count - (map["n"] ?: 0)
                "se" -> count - (map["nw"] ?: 0)
                else -> 0
            }
            if(steps > 0) steps else 0
        }
        val map3 = map2.mapValues { (dir, count) ->
            val steps = when(dir) {
                "ne" -> count - (map["s"] ?: 0)
                "nw" -> count - (map["s"] ?: 0)
                "sw" -> count - (map["n"] ?: 0)
                "se" -> count - (map["n"] ?: 0)
                else -> count
            }
            if(steps > 0) steps else 0
        }
        val map4 = map3.mapValues { (dir, count) ->
            val steps = when(dir) {
                "ne" -> count - (map["nw"] ?: 0)
                "se" -> count - (map["sw"] ?: 0)
                else -> count
            }
            if(steps > 0) steps else 0
        }
        return map4.values.sum()
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }
}