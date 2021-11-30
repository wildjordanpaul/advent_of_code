package advent20

import shared.AdventSolution
import shared.splitInTwo

class Day19 : AdventSolution(
    mapOf("""
        0: 4 1 5
        1: 2 3 | 3 2
        2: 4 4 | 5 5
        3: 4 5 | 5 4
        4: "a"
        5: "b"
        
        ababbb
        bababa
        abbbab
        aaabbb
        aaaabbb""".trimIndent() to 2
    ),
    mapOf("""
        42: 9 14 | 10 1
        9: 14 27 | 1 26
        10: 23 14 | 28 1
        1: "a"
        11: 42 31
        5: 1 14 | 15 1
        19: 14 1 | 14 14
        12: 24 14 | 19 1
        16: 15 1 | 14 14
        31: 14 17 | 1 13
        6: 14 14 | 1 14
        2: 1 24 | 14 4
        0: 8 11
        13: 14 3 | 1 12
        15: 1 | 14
        17: 14 2 | 1 7
        23: 25 1 | 22 14
        28: 16 1
        4: 1 1
        20: 14 14 | 1 15
        3: 5 14 | 16 1
        27: 1 6 | 14 18
        14: "b"
        21: 14 1 | 1 14
        25: 1 1 | 1 14
        22: 14 14
        8: 42
        26: 14 22 | 1 20
        18: 15 15
        7: 14 5 | 1 21
        24: 14 1

        abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
        bbabbbbaabaabba
        babbbbaabbbbbabbbbbbaabaaabaaa
        aaabbbbbbaaaabaababaabababbabaaabbababababaaa
        bbbbbbbaaaabbbbaaabbabaaa
        bbbababbbbaaaaaaaabbababaaababaabab
        ababaaaaaabaaab
        ababaaaaabbbaba
        baabbaaaabbaaaababbaababb
        abbbbabbbbaaaababbbbbbaaaababb
        aaaaabbaabaaaaababaa
        aaaabbaaaabbaaa
        aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
        babaaabbbaaabaababbaabababaaab
        aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
    """.trimIndent() to 12
    ),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        val (rules, messages) = input.splitInTwo("\n\n")
        val ruleMap = rules.split("\n").associate { rule ->
            val (i, ruleDef) = rule.splitInTwo(": ")
            i.toInt() to ruleDef.toRuleDefinition()
        }

        val ruleZero = ruleMap.evalRule(0).toRegex()

        return messages.split("\n").count { message ->
            ruleZero.matches(message)
        }
    }

    override fun solveProblem2(input: String): Any {
        val (rules, messages) = input.splitInTwo("\n\n")
        val ruleMap = rules.split("\n").associate { rule ->
            val (i, ruleDef) = rule.splitInTwo(": ")

            val value: List<List<Any>> = when (i.toInt()) {
                8 -> listOf(listOf(42), listOf(42, 8))
                11 -> listOf(listOf(42, 31), listOf(42, 11, 31))
                else -> ruleDef.toRuleDefinition()
            }

            i.toInt() to value
        }

        val ruleZero = ruleMap.evalRule(0, true).toRegex()

        return messages.split("\n").count { message ->
            ruleZero.matches(message)
        }
    }

    private fun String.toRuleDefinition(): List<List<Any>> {
        val pieces = mutableListOf<MutableList<Any>>()
        var currentPiece = mutableListOf<Any>()
        split(" ").forEach { piece ->
            when {
                """\d+""".toRegex().matches(piece) -> {
                    currentPiece.add(piece.toInt())
                }
                piece == "|" -> {
                    pieces.add(currentPiece)
                    currentPiece = mutableListOf()
                }
                else -> {
                    currentPiece.add(piece.replace("\"".toRegex(), ""))
                }
            }
        }
        pieces.add(currentPiece)
        return pieces
    }

    private fun Map<Int, List<List<Any>>>.evalRule(i: Int, part2: Boolean = false): String {
        val evaluated = this[i]!!.joinToString("|") { piece ->
            piece.joinToString("") { element ->
                when(element) {
                    is Int -> {
                        if(part2) {
                            when(element) {
                                8 -> {
                                     "${evalRule(42)}+"
                                }
                                11 -> {
                                    val r1 = evalRule(42)
                                    val r2 = evalRule(31)
                                    val lazyRegex = (1..5).joinToString("|") {
                                        "${r1.repeat(it)}${r2.repeat(it)}"
                                    }
                                    "($lazyRegex)"
                                }
                                else -> evalRule(element)
                            }
                        } else evalRule(element)
                    }
                    is String -> element
                    else -> ""
                }
            }
        }
        return if(evaluated.contains("|")) "($evaluated)" else evaluated
    }
}