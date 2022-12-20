package advent22

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import shared.*
import java.util.*
import kotlin.math.max

class Day19 : AdventSolution(
    mapOf("""
        Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
        Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
    """.trimIndent() to 33),
    1
) {
    override fun solveProblem1(input: String): Any? {
        val blueprints = input.splitLines().map { line ->
            val (p1, p2) = line.splitInTwo(":")
            val id = p1.extractInt()
            val costs = p2.split(".")
            val oreRobotCost = costs[0].extractInt()
            val clayRobotCost = costs[1].extractInt()
            val obsidianCosts = costs[2].splitInTwo("and") { it.extractInt() }
            val geodeCosts = costs[3].splitInTwo("and") { it.extractInt() }
            Blueprint(
                id,
                Resources(ore = oreRobotCost),
                Resources(ore = clayRobotCost),
                Resources(ore = obsidianCosts.first, clay = obsidianCosts.second),
                Resources(ore = geodeCosts.first, obsidian = geodeCosts.second)
            )
        }


        return runBlocking(Dispatchers.Default) {
            val best = blueprints.parallelMap { blueprint ->
                var bestAt = (0..24).associateWith { 0 }.toMutableMap()
                val queue = LinkedHashSet<Pair<Int, CaveState>>().also{ it.add(0 to CaveState()) }
                while(queue.isNotEmpty()) {
                    val (minute, state) = queue.pop()!!
                    val nextMin = minute + 1
                    val next = state.cycle(blueprint)
                    bestAt[nextMin] = max(next.maxOf { it.resources.geodes }, bestAt[nextMin]!!)
                    if(nextMin < 24)
                        queue.addAll(next.map { nextMin to it })

                }
                println("Finished blueprint ${blueprint.id}: ${bestAt[24]}")
                bestAt[24]!! * blueprint.id
            }
            best.sum()
        }


    }

    override fun solveProblem2(input: String): Any? {
        return null
    }

    private data class Blueprint(
        val id: Int,
        val oreRobotCost: Resources,
        val clayRobotCost: Resources,
        val obsidianRobotCost: Resources,
        val geodeRobotCost: Resources
    )

    private data class Resources(
        val ore: Int = 0,
        val clay: Int = 0,
        val obsidian: Int = 0,
        val geodes: Int = 0
    ) {
        operator fun compareTo(other: Resources): Int {
            return if(ore == other.ore && clay == other.clay && obsidian == other.obsidian && geodes == other.geodes)
                0
            else if(ore >= other.ore && clay >= other.clay && obsidian >= other.obsidian)
                1
            else -1
        }

        operator fun minus(other: Resources?): Resources {
            if(other == null) return this
            return Resources(ore - other.ore, clay - other.clay, obsidian - other.obsidian, geodes)
        }

        operator fun plus(other: Resources?): Resources {
            if(other == null) return this
            return Resources(ore + other.ore, clay + other.clay, obsidian + other.obsidian, geodes + other.geodes)
        }
    }

    private data class CaveState(
        val resources: Resources = Resources(),
        val robots: Resources = Resources(1)
    ) {

        fun cycle(blueprint: Blueprint): List<CaveState> {
            val options = mutableListOf<CaveState>()

            if(resources >= blueprint.obsidianRobotCost) {
                options.add(CaveState(
                    resources - blueprint.obsidianRobotCost + robots,
                    robots + Resources(obsidian = 1)
                ))
            }

            if(resources >= blueprint.clayRobotCost) {
                options.add(CaveState(
                    resources - blueprint.clayRobotCost + robots,
                    robots + Resources(clay = 1)
                ))
            }

            if(resources >= blueprint.oreRobotCost) {
                options.add(CaveState(
                    resources - blueprint.oreRobotCost + robots,
                    robots + Resources(ore = 1)
                ))
            }

            options.add(CaveState(resources + robots, robots))

            return options
        }

        fun nextState(costs: Resources, newRobots: Resources?) = CaveState(
            resources - costs + robots,
            robots + newRobots
        )
    }



}