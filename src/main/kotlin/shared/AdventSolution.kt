package shared

import kotlin.test.assertEquals

abstract class AdventSolution(
        val testCases1: Map<String, Any> = mapOf(),
        val testCases2: Map<String, Any> = mapOf(),
        val input1: String,
        val input2: String = input1
) {

    abstract fun solveProblem1(input: String): Any?
    abstract fun solveProblem2(input: String): Any?

    fun solve(skip1: Boolean = false, skip2: Boolean = false) {
        var passedTests = 0
        if(!skip1) {
            testCases1.forEach { input, output ->
                assertEquals(output.toString(), solveProblem1(input).toString())
                passedTests++
            }
        }

        if(!skip2) {
            testCases2.forEach { input, output ->
                assertEquals(output.toString(), solveProblem2(input).toString())
                passedTests++
            }
        }

        println("PASSED ALL TESTS: $passedTests")

        if(!skip1)
            println("Solution 1: ${solveProblem1(input1)}")
        if(!skip2)
            println("Solution 2: ${solveProblem2(input2)}")
    }
}