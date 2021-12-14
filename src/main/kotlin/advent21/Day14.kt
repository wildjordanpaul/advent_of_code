package advent21

import shared.*
import java.math.BigInteger

class Day14 : AdventSolution(
    mapOf("""
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent() to 1588
    ),
    mapOf("""
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent() to "2188189693529"),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return slowSolve(input, 10)
    }

    override fun solveProblem2(input: String): Any? {
        return fastSolve(input, 40)
    }

    private fun slowSolve(input: String, steps: Int): Int {
        val (template, rawRules) = input.splitInTwo("\n\n")
        val rules = rawRules.split("\n").associate { it.splitInTwo(" -> ") }
        val newTemplate = (1..steps).fold(template) { prevTemplate, _ ->
            prevTemplate.runningFold("") { last, current ->
                rules.getOrDefault(last.lastOrNull().toString() + current, "") + current
            }.joinToString("")
        }

        val (min, max) = newTemplate.groupBy { it }.map{ it.value.count() }.sorted().ends()
        return max - min
    }

    private fun fastSolve(input: String, steps: Int): BigInteger {
        val (template, rawRules) = input.splitInTwo("\n\n")
        val rules: Map<String, String> = rawRules.split("\n").associate { it.splitInTwo(" -> ") }

        val countMap = mutableMapOf<String, BigInteger>()
        val firstChar = template.first()
        var prev = firstChar
        template.drop(1).forEach { current ->
            countMap.inc(prev.toString() + current.toString())
            prev = current
        }

        var firstPair = template.take(2)
        val lastMap = (1..steps).fold(countMap.toMap()) { lastMap, _ ->
            firstPair = firstPair.first().toString() + rules[firstPair]
            val newCountMap = lastMap.toMutableMap()
            lastMap.forEach { (pair, count) ->
                rules[pair]?.let { rule ->
                    newCountMap.subtract(pair, count)
                    listOf(
                        pair.first().toString() + rule,
                        rule + pair.last().toString()
                    ).forEach { newCountMap.add(it, count) }
                }
            }
            newCountMap.toMap()
        }

        val (min, max) = lastMap.map { it.key.last() }.toSet()
            .map { c ->
                val newCount = lastMap.filterKeys { it.endsWith(c) }.values.sum()
                if(c == firstChar) newCount.inc() else newCount
            }
            .sorted()
            .ends()
        return max.subtract(min)
    }
}