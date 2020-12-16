package advent20

import shared.AdventSolution
import shared.splitInTwo

class Day16 : AdventSolution(
    mapOf(
        """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50

            your ticket:
            7,1,14

            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12
        """.trimIndent() to 71
    ),
    mapOf(),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        val sections = input.split("\n\n")
        val ruleMap = sections[0].split("\n").map { rule ->
            val (name, ruleValue) = rule.splitInTwo(": ")
            name to ruleValue.split(" or ").map { range ->
                val (start, end) = range.splitInTwo("-")
                (start.toInt()..end.toInt())
            }
        }.toMap()
        val badValues = sections[2].split("\n").flatMap { nearbyTicket ->
            val values = nearbyTicket.split(",").mapNotNull(String::toIntOrNull)
            values.filter { value ->
                ruleMap.values.none { ranges ->
                    ranges.any { range -> value in range }
                }
            }
        }
        return badValues.sum()
    }

    override fun solveProblem2(input: String): Any {
        val sections = input.split("\n\n")

        val ruleMap = sections[0].split("\n").map { rule ->
            val (name, ruleValue) = rule.splitInTwo(": ")
            name to ruleValue.split(" or ").map { range ->
                val (start, end) = range.splitInTwo("-")
                (start.toInt()..end.toInt())
            }
        }.toMap()

        val validTickets = sections[2].split("\n").mapNotNull { nearbyTicket ->
            val values = nearbyTicket.split(",").mapNotNull(String::toIntOrNull)
            val isValid = values.isNotEmpty() && values.all { value ->
                ruleMap.values.any { ranges ->
                    ranges.any { range -> value in range }
                }
            }
            if(isValid) {
                values
            } else null
        }

        val potentialsMap = ruleMap.map { (ruleName, ranges) ->
            val validIndexMap = mutableMapOf<Int, Int>()
            validTickets.forEach { ticket ->
                ticket.forEachIndexed { i, value ->
                    if(ranges.any { range -> value in range }) {
                        validIndexMap[i] = validIndexMap.getOrDefault(i, 0) + 1
                    }
                }
            }
            val potentials = validIndexMap.filter { it.value == validTickets.count() }.keys
            ruleName to potentials
        }.sortedBy { it.second.size }

        val indexMap = mutableMapOf<String, Int>()
        while (indexMap.size != ruleMap.size) {
            potentialsMap.forEach { (ruleName, potentials) ->
                val validPotentials = potentials.filter { potential ->
                    !indexMap.values.contains(potential)
                }
                if(validPotentials.size == 1) {
                    indexMap[ruleName] = validPotentials.first()
                }
            }
        }

        val myTicket = sections[1].split("\n").last().split(",").map(String::toLong)

        return indexMap
            .filter { it.key.startsWith("departure") }
            .values.map { i -> myTicket[i] }
            .reduce { acc, value -> value * acc }
    }

}