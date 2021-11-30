package advent18

import shared.AdventSolution
import java.lang.Integer.max

fun main() = object : AdventSolution(
    mapOf(
        "18" to "33,45",
        "42" to "21,61"
    ),
    mapOf(
        "18" to "90,269,16",
        "42" to "232,251,12"
    ),
    "7139"
) {

    fun String.generateGrid(): List<D11Cell> {
        val serialNumber = toInt()
        val grid = mutableListOf<D11Cell>()
        (1..300).forEach { y ->
            (1..300).forEach { x ->
                grid.add(D11Cell(serialNumber, x, y))
            }
        }
        return grid
    }

    override fun solveProblem1(input: String): String {
        val max = input.generateGrid()
            .filter { it.x < 298 && it.y < 298 }
            .maxByOrNull { it.totalPower(3) }!!
        return "${max.x},${max.y}"
    }

    override fun solveProblem2(input: String): String {
        val grid = input.generateGrid()
        val max = grid.maxByOrNull { it.bestSize.second }!!
        return "${max.x},${max.y},${max.bestSize.first}"
    }

}.solve()


/**
Find the fuel cell's rack ID, which is its X coordinate plus 10.
Begin with a power level of the rack ID times the Y coordinate.
Increase the power level by the value of the grid serial number (your puzzle input).
Set the power level to itself multiplied by the rack ID.
Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
Subtract 5 from the power level.
 */
class D11Cell(
    val serialNumber: Int,
    val x: Int,
    val y: Int,
    val rackID: Int = x+10,
    var powerLevel: Int = ((rackID * y + serialNumber) * rackID).hundredsDigit() - 5
) {

    fun totalPower(size: Int): Int {
        return powerLevel + (1 until size*size).sumOf {
            val tX = x + it%size
            val tY = y + it/size
            val tR = tX+10
            ((tR * tY + serialNumber) * tR).hundredsDigit() - 5
        }
    }

    fun totalPowerPair(size: Int) = Pair(size, totalPower(size))

    val bestSize by lazy {
        val base = max(x, y)
        val max = 300-base
        var bestSoFar = totalPowerPair(2)
        var downCount = 0
        var last = bestSoFar
        (3..max).forEach size@{ size ->
            val next = totalPowerPair(size)
            if(bestSoFar.second < next.second)
                bestSoFar = next

            if(next.second < last.second) downCount += 1
            else downCount = 0

            if(downCount > 5)
                return@lazy bestSoFar
            last = next
        }
        bestSoFar
    }

    companion object {
        fun Int.hundredsDigit() = toString().reversed().getOrElse(2) { '0' }.toString().toInt()
    }

}