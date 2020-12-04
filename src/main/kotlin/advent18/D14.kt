package advent18

import shared.AdventSolution

fun main() = object : AdventSolution(
        mapOf(
            "9" to "5158916779",
            "5" to "0124515891",
            "18" to "9251071085",
            "2018" to "5941429882"
        ),
        mapOf(

            "5158916779" to "9",
            "0124515891" to "5",
            "9251071085" to "18",
            "5941429882" to "2018"

        ),
        "681901"
) {

    override fun solveProblem1(input: String): String {
        val steps = input.toInt()
        val scores = mutableListOf(3,7)
        var elf1 = 0
        var elf2 = 1
        while(scores.size < steps + 10) {
            (scores[elf1] + scores[elf2]).toString().forEach { scores.add(it.toString().toInt()) }
            elf1 = (elf1 + scores[elf1] + 1) % scores.size
            elf2 = (elf2 + scores[elf2] + 1) % scores.size
        }

        return scores.subList(steps, steps+10).joinToString("")
    }

    override fun solveProblem2(input: String): String {
        val scores = mutableListOf(3,7)
        var elf1 = 0
        var elf2 = 1

        val start = 1000000
        var iters = start

        while(true) {
            iters -= 1
            if(iters == 0) {
                val i = scores.joinToString("").indexOf(input)
                if(i != -1) return i.toString()
                iters = start
            }
            (scores[elf1] + scores[elf2]).toString().forEach { scores.add(it.toString().toInt()) }
            elf1 = (elf1 + scores[elf1] + 1) % scores.size
            elf2 = (elf2 + scores[elf2] + 1) % scores.size
        }
    }

}.solve()

