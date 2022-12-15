package advent22

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import shared.AdventSolution
import shared.Point
import shared.splitInTwo
import toRangeSet
import java.math.BigInteger
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

class Day15 : AdventSolution(
    mapOf("""
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent() to 26),
    56000011
) {
    override fun solveProblem1(input: String): Any? {
        val map = input.toSensorMap()
        val y = if(map.size <= 14) 10 else 2_000_000
        val beacons = map.values.toSet()
        val distMap = map.mapValues { (k,v) -> k.manhattanDistance(v) }
        val minX = distMap.minOf { it.key.x - it.value } - 1
        val maxX = distMap.maxOf { it.key.x + it.value } + 1
        val notBeacons = (minX..maxX).filter { x ->
            val p = Point(x, y)
            !beacons.contains(p) && distMap.any { (k, v) -> k.manhattanDistance(p) <= v }
        }
        return notBeacons.size
    }

    override fun solveProblem2(input: String): Any? {
        if(input.isEmpty()) return 1
        val map = input.toSensorMap()
        val distMap = map.mapValues { (k,v) -> k.manhattanDistance(v) }
        val max = if(map.size <= 14) 20 else 4_000_000
        (0..max).forEach { y ->
            val ranges = distMap.mapNotNull { (p, d) ->
                val deltaY = (p.y - y).absoluteValue
                val deltaX = (d - deltaY).absoluteValue
                if (deltaY > d) null
                else (max(0, p.x - deltaX))..min(max, p.x + deltaX)
            }.toRangeSet()
            ranges.gaps().firstOrNull()?.let {
                val x = it.start
                return x.toBigInteger().times(BigInteger("4000000")).plus(y.toBigInteger())
            }
        }

        return -1
    }

    private fun String.toSensorMap(): Map<Point, Point> {
        return split("\n").filter(String::isNotBlank).associate { line ->
            val (sensor, beacon) = line.splitInTwo(":") { part ->
                Point(part.splitInTwo(",") { part2 ->
                    part2.replace(Regex("[^0-9\\-]"), "").toInt()
                })
            }
            sensor to beacon
        }
    }
}