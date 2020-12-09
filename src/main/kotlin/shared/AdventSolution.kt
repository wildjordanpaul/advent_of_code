package shared

import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

abstract class AdventSolution(
        val testCases1: Map<String, Any> = mapOf(),
        val testCases2: Map<String, Any> = mapOf(),
        var input1: String = "",
        var input2: String = input1,
        val pullInputFromNorthPole: Boolean = false
) {

    init {
        if (pullInputFromNorthPole) {
            val day = javaClass.name.split(".").last().replace(Regex("[^0-9]"), "").toInt()
            input1 = AdventPuller().pull(day)
            input2 = input1
        }
    }

    companion object {
        fun loadInput(path: String): String {
            return object {}.javaClass.getResource(path).readText()
        }
    }

    abstract fun solveProblem1(input: String): Any?
    abstract fun solveProblem2(input: String): Any?

    fun solve(skip1: Boolean = false, skip2: Boolean = false) {
        println("RUNNING ${this.javaClass.name}")

        var passedTests = 0
        if(!skip1) {
            testCases1.forEach { (input, output) ->
                assertEquals(output.toString(), solveProblem1(input).toString())
                passedTests++
            }
        }

        if(!skip2) {
            testCases2.forEach { (input, output) ->
                assertEquals(output.toString(), solveProblem2(input).toString())
                passedTests++
            }
        }

        println("PASSED ALL TESTS: $passedTests")

        if(!skip1) {
            val ms = measureTimeMillis {
                println("---\nSolution 1: ${solveProblem1(input1)}")
            }
            println("Solved in $ms ms")
        }
        if(!skip2) {
            val ms = measureTimeMillis {
                println("---\nSolution 2: ${solveProblem2(input2)}")
            }
            println("Solved in $ms ms")
        }
    }
}