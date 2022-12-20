package advent17

import shared.AdventSolution
import shared.splitInTwo
import java.lang.RuntimeException

class Day8 : AdventSolution(
    mapOf("""
        b inc 5 if a > 1
        a inc 1 if b < 5
        c dec -10 if a >= 1
        c inc -20 if c == 10
    """.trimIndent() to 1),
    10
) {
    override fun solveProblem1(input: String): Any? {
        val reg = mutableMapOf<String, Int>()
        input.split("\n").filter(String::isNotBlank).forEach { line ->
            val (s1, s2) = line.splitInTwo(" if ")
            val instruction = s1.split(" ")
            val condition = s2.split(" ")

            if(when(condition[1]){
                ">" -> reg[condition[0]] ?: 0 > condition[2].toInt()
                "<" -> reg[condition[0]] ?: 0 < condition[2].toInt()
                "==" -> reg[condition[0]] ?: 0 == condition[2].toInt()
                ">=" -> reg[condition[0]] ?: 0 >= condition[2].toInt()
                "<=" -> reg[condition[0]] ?: 0 <= condition[2].toInt()
                "!=" -> reg[condition[0]] ?: 0 != condition[2].toInt()
                else -> false
            }) {
                when(instruction[1]) {
                    "inc" -> reg[instruction[0]] = (reg[instruction[0]] ?: 0) + instruction[2].toInt()
                    "dec" -> reg[instruction[0]] = (reg[instruction[0]] ?: 0) - instruction[2].toInt()
                }
            }
        }
        return reg.values.max()
    }

    override fun solveProblem2(input: String): Any? {
        val reg = mutableMapOf<String, Int>()
        var max = 0
        input.split("\n").filter(String::isNotBlank).forEach { line ->
            val (s1, s2) = line.splitInTwo(" if ")
            val instruction = s1.split(" ")
            val condition = s2.split(" ")

            if(when(condition[1]){
                    ">" -> reg[condition[0]] ?: 0 > condition[2].toInt()
                    "<" -> reg[condition[0]] ?: 0 < condition[2].toInt()
                    "==" -> reg[condition[0]] ?: 0 == condition[2].toInt()
                    ">=" -> reg[condition[0]] ?: 0 >= condition[2].toInt()
                    "<=" -> reg[condition[0]] ?: 0 <= condition[2].toInt()
                    "!=" -> reg[condition[0]] ?: 0 != condition[2].toInt()
                    else -> false
                }) {
                val next = when(instruction[1]) {
                    "inc" ->  (reg[instruction[0]] ?: 0) + instruction[2].toInt()
                    "dec" -> (reg[instruction[0]] ?: 0) - instruction[2].toInt()
                    else -> throw RuntimeException("hey")
                }
                if(next > max) max = next
                reg[instruction[0]] = next
            }
        }
        return max
    }
}