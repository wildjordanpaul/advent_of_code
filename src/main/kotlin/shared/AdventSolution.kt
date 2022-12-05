package shared

import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

abstract class AdventSolution(
        val testCases1: Map<String, Any> = mapOf(),
        val testCases2: Map<String, Any> = mapOf(),
        var input1: String = "",
        var input2: String = input1,
        val pullInputFromNorthPole: Boolean = true,
        val trimInput: Boolean = false
) {

    constructor(
        testCases1: Map<String, Any>,
        testResult2: Any,
        trimInput: Boolean = false
    ): this(
        testCases1,
        mapOf(testCases1.keys.first() to testResult2),
        trimInput = trimInput
    )

    private val day by lazy { javaClass.name.split(".").last().replace(Regex("[^0-9]"), "").toInt() }
    private val year by lazy { 2000 + javaClass.name.split(".").first().replace(Regex("[^0-9]"), "").toInt() }
    private val puller by lazy  { AdventPuller(trimInput) }
    private val pusher by lazy  { AdventPusher() }

    init {
        if (pullInputFromNorthPole) {
            input1 = puller.pull(day, year)
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

    fun solve(
        skip1: Boolean = false,
        skip2: Boolean = false,
        push1: Boolean = false,
        push2: Boolean = false
    ) {
        println("RUNNING ${this.javaClass.name}")

        var passedTests = 0
        if(!skip1) {
            testCases1.forEach { (input, output) ->
                val solution = solveProblem1(input)
                if(solution != null)
                    assertEquals(output.toString(), solution.toString())
                passedTests++
            }
        }

        if(!skip2) {
            testCases2.forEach { (input, output) ->
                val solution = solveProblem2(input)
                if(solution != null)
                    assertEquals(output.toString(), solution.toString())
                passedTests++
            }
        }

        println("PASSED ALL TESTS: $passedTests")

        if(!skip1 && input1.isNotBlank()) {
            var solution: Any?
            val ms = measureTimeMillis {
                solution = solveProblem1(input1)
            }
            println("---\nSolution 1: $solution")
            println("Solved in $ms ms")
            if (push1 && solution != null) {
                pusher.push(day, year, 1, solution)
            }
        }
        if(!skip2 && input2.isNotBlank()) {
            var solution: Any?
            val ms = measureTimeMillis {
                solution = solveProblem2(input2)
            }
            println("---\nSolution 2: $solution")
            println("Solved in $ms ms")
            if (push2 && solution != null) {
                pusher.push(day, year, 2, solution)
            }
        }
    }
}