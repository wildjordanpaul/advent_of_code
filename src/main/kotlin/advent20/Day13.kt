package advent20

import shared.AdventSolution

class Day13 : AdventSolution(
    mapOf("""
        939
        7,13,x,x,59,x,31,19
    """.trimIndent() to 295),
    mapOf(
    """
        939
        7,13,x,x,59,x,31,19
    """.trimIndent() to 1068781,
    """
        1
        17,x,13,19
    """.trimIndent() to 3417,
    """
        1
        67,7,59,61
    """.trimIndent() to 754018,
    """
        1
        67,x,7,59,61
    """.trimIndent() to 779210,
    """
        1
        67,7,x,59,61
    """.trimIndent() to 1261476,
"""
        1
        1789,37,47,1889
    """.trimIndent() to 1202161486),
    pullInputFromNorthPole = true
) {

    override fun solveProblem1(input: String): Any {
        val lines = input.split("\n")
        val departure = lines[0].toInt()
        val busses = lines[1].split(",").filter{ it != "x" }.map(String::toInt)
        val bus = busses.minByOrNull { bus ->
            bus - (departure % bus)
        }!!
        val time = (departure / bus + 1) * bus
        return (time - departure) * bus
    }

    // Uses Chinese Remainder Theorem
    // see https://www.youtube.com/watch?v=zIFehsBHB8o
    override fun solveProblem2(input: String): Any {
        val lines = input.split("\n")
        val busses = lines[1].split(",").map{ if(it == "x") null else it.toInt() }

        val nList = busses.mapNotNull { bus -> bus?.toBigInteger() }
        val bList = busses.mapIndexedNotNull { i, bus -> bus?.let{ Math.floorMod(i * -1, it).toBigInteger() } }

        val bigN = nList.reduce { acc, n -> acc.times(n) }
        val bigNList = nList.map { bigN.divide(it) }
        val xList = bigNList.mapIndexed { i, bigNi -> bigNi.modInverse(nList[i]) }
        val products = bList.mapIndexed { i, b -> b.times(bigNList[i]).times(xList[i]) }
        val sum = products.reduce { acc, p -> acc.add(p) }
        return sum.mod(bigN)
    }

}