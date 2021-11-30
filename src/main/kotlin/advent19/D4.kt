package advent19

import shared.AdventSolution

class D4 : AdventSolution(
    mapOf(

    ),
    mapOf(

    ),
    "382345-843167"
) {
    override fun solveProblem1(input: String): Any {
        return countPossiblePasswords(input)
    }

    override fun solveProblem2(input: String): Any {
        return countPossiblePasswords(input, true)
    }

    private fun countPossiblePasswords(input: String, pairsOnly: Boolean = false): Int {
        val numbers = input.split('-').map(String::toInt)
        var pwCount = 0
        (numbers.first()..numbers.last()).forEach { i ->
            val ca = i.toString().toCharArray()
            if(!ca.hasAdjacentDuplication(pairsOnly)) return@forEach
            if(ca.sorted().joinToString("") != i.toString()) return@forEach
            pwCount++
        }
        return pwCount
    }

    private fun CharArray.hasAdjacentDuplication(pairsOnly: Boolean = false): Boolean {
        var current: Char?
        var last: Char? = null
        val iter = iterator()
        while(iter.hasNext()) {
            current = iter.next()
            last = if(current == last) {
                if(!pairsOnly) return true
                var next = if(iter.hasNext()) iter.next() else null
                if(next != current) return true
                while(iter.hasNext() && next == current)
                    next = iter.next()
                next
            } else {
                current
            }
        }
        return false
    }

}
