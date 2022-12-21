package advent22

import shared.AdventSolution
import shared.extractInt
import shared.splitInTwo
import shared.splitLines
import java.lang.RuntimeException
import java.util.LinkedList

class Day21 : AdventSolution(
    mapOf("""
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent() to 152),
    301
) {
    override fun solveProblem1(input: String): Any? {
        val numMap = mutableMapOf<String, Long>()
        val pointerMap = mutableMapOf<String, Pointer>()
        val isNumber = Regex("[0-9-]+")

        input.splitLines().forEach {
            val(k, v) = it.splitInTwo(": ")
            if(v.matches(isNumber))
                numMap[k] = v.extractInt().toLong()
            else {
                val parts = v.split(" ")
                pointerMap[k] = Pointer(parts[0], parts[2], parts[1])
            }
        }

        val queue = LinkedList<String>().also { it.add("root") }

        while(queue.isNotEmpty()) {
            val next = queue.peek()
            if(!numMap.containsKey(next)) {
                val p = pointerMap[next]!!
                val v1 = numMap[p.k1]
                if(v1 == null) queue.addFirst(p.k1)
                val v2 = numMap[p.k2]
                if(v2 == null) queue.addFirst(p.k2)
                if(v1 != null && v2 != null) {
                    numMap[next] = when(p.op) {
                        "*" -> v1 * v2
                        "+" -> v1 + v2
                        "-" -> v1 - v2
                        "/" -> v1 / v2
                        else -> throw RuntimeException("Oops")
                    }
                    queue.pop()
                }
            } else {
                queue.pop()
            }
        }

        return numMap["root"]
    }

    override fun solveProblem2(input: String): Any? {
        val numMapTemp = mutableMapOf<String, Long>()
        val pointerMap = mutableMapOf<String, Pointer>()
        val isNumber = Regex("[0-9-]+")

        input.splitLines().forEach {
            val(k, v) = it.splitInTwo(": ")
            if(v.matches(isNumber))
                numMapTemp[k] = v.extractInt().toLong()
            else {
                val parts = v.split(" ")
                pointerMap[k] = Pointer(parts[0], parts[2], parts[1])
            }
        }
        val originalMap = numMapTemp.toMap()
        var i = 301L
        var firstPass = true

        while(true) {
            val numMap = originalMap.toMutableMap()
            numMap["humn"] = i
            val queue = LinkedList<String>().also { it.add("root") }

            while(queue.isNotEmpty()) {
                val next = queue.peek()
                if(!numMap.containsKey(next)) {
                    val p = pointerMap[next]!!
                    val v1 = numMap[p.k1]
                    if(v1 == null) queue.addFirst(p.k1)
                    val v2 = numMap[p.k2]
                    if(v2 == null) queue.addFirst(p.k2)
                    if(v1 != null && v2 != null) {
                        if(next == "root") {
                            println("$i ${v1-v2}")
                            if(v1 == v2) return i
                            else break
                        } else {
                            numMap[next] = when(p.op) {
                                "*" -> v1 * v2
                                "+" -> v1 + v2
                                "-" -> v1 - v2
                                "/" -> v1 / v2
                                else -> throw RuntimeException("Oops")
                            }
                        }

                        queue.pop()
                    }
                } else {
                    queue.pop()
                }
            }
            if(firstPass) {
                firstPass = false
                i = 3887609741101
            }
            i += 1
        }

        return "oops"
    }

    private data class Pointer(val k1: String, val k2: String, val op: String)
}