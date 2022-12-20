package advent22

import shared.*
import java.util.*

class Day16 : AdventSolution(
    mapOf("""
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent() to 1651),
    1
) {
    val rates = mutableMapOf<String, Int>()
    val edges = mutableMapOf<String, List<String>>()
    override fun solveProblem1(input: String): Any? {
        rates.clear()
        edges.clear()
        input.splitLines("\n").forEach { line ->
            val (rawFlow, routes) = line.splitInTwo(";")
            val (valveRaw, rate) = rawFlow.splitInTwo(""" has flow rate=""")
            val valve = valveRaw.split(" ").last()
            rates[valve] = rate.toInt()
            edges[valve] = routes.split(Regex("valves? ")).last().split(", ")
        }

        val distances = rates.keys.flatMap { v1 ->
            rates.keys.mapNotNull {v2 ->
                val key = "$v1-$v2"
                if(v1 == v2)
                    null
                else key to calculateTravelTime(v1, v2)
            }
        }.toMap()

        var current = "AA"
        var flow = 0
        val open = mutableSetOf<String>()
        var minute = 0
        var movingFor = 0
        loop@ while(minute < 30) {
            flow += open.sumOf { rates[it]!! }
            if(movingFor > 0) {
                movingFor -= 1
            } else {
                val valueOfOpening = if(open.contains(current)) 0 else (30-minute) * rates[current]!!
                val best = rates.keys.mapNotNull { other ->
                    if(open.contains(other)) return@mapNotNull null
                    val dist = distances["$current-$other"]
                    if(dist != null && dist < 30-minute)
                        other to dist
                    else null
                }.maxByOrNull { (other, dist) -> (30-minute-dist-1) * rates[other]!! }
                if(best != null) {
                    val (bestNeighbor, dist) = best
                    val moveValue = (30-minute-dist-1) * rates[bestNeighbor]!!
                    if(valueOfOpening > moveValue) {
                        open.add(current)
                    } else {
                        movingFor = dist
                        current = bestNeighbor
                    }
                } else if(valueOfOpening > 0) {
                    open.add(current)
                }
            }
            minute += 1
        }
        // 560

        return flow
    }

    private data class ValveDist(val valve: String, val dist: Int)

    private fun calculateTravelTime(start: String, end: String): Int? {
        val distCache = mutableMapOf<String, Int>()
        val queue = PriorityQueue<ValveDist>(compareBy { it.dist })
        queue.add(ValveDist(start, 0))

        while(queue.isNotEmpty()) {
            val current = queue.poll()
            edges[current.valve]!!.forEach { next ->
                val cachedDist = distCache[next]
                val newDist = current.dist + 1
                if(cachedDist == null || newDist < cachedDist) {
                    distCache[next] = newDist
                    queue.add(ValveDist(next, newDist))
                }
            }
        }

        return distCache[end]
    }

    override fun solveProblem2(input: String): Any? {
        return null
    }
}