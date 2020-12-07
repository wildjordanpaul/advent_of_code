package advent20

import shared.AdventSolution
import shared.splitInTwo

class Day7 : AdventSolution(
    mapOf("""
        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.
    """.trimIndent() to 4),
    mapOf("""
        shiny gold bags contain 2 dark red bags.
        dark red bags contain 2 dark orange bags.
        dark orange bags contain 2 dark yellow bags.
        dark yellow bags contain 2 dark green bags.
        dark green bags contain 2 dark blue bags.
        dark blue bags contain 2 dark violet bags.
        dark violet bags contain no other bags.
    """.trimIndent() to 126),
    input1 = loadInput("/advent20/D7_input1.txt")
) {

    data class BagRule(val bag: String, val count: Int)

    override fun solveProblem1(input: String): Any {
        val bagMap = input.toBagMap()
        val bagSet = mutableSetOf<String>()
        var needsAnotherPass = true
        while(needsAnotherPass) {
            needsAnotherPass = false
            bagMap.forEach { (bag, subBags) ->
                if(!bagSet.contains(bag) && subBags.any { it.bag == "shiny gold" || bagSet.contains(it.bag) }) {
                    bagSet.add(bag)
                    needsAnotherPass = true
                }
            }
        }
        return bagSet.size
    }

    override fun solveProblem2(input: String): Any {
        return input.toBagMap().countBagsIn("shiny gold")
    }

    private fun String.toBagMap() : Map<String, List<BagRule>> {
        val bagMap = mutableMapOf<String, List<BagRule>>()
        split("\n").forEach { rule ->
            val (bag, contains) = rule.splitInTwo(" bags contain ")
            bagMap[bag] = contains.split(",").mapNotNull {
                val r = """(\d+) ([\w\s]+) bag""".toRegex()
                if (it != "no other bags.") {
                    val (number, subBag) = r.find(it)!!.destructured
                    BagRule(subBag, number.toInt())
                } else null
            }
        }
        return bagMap
    }

    private fun Map<String, List<BagRule>>.countBagsIn(bag: String): Int {
        return get(bag)?.sumBy {
            it.count * (1 + countBagsIn(it.bag))
        } ?: 0
    }

}