package advent21

import shared.AdventSolution
import shared.Point
import shared.splitInTwo
import shared.splitInts
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

class Day18 : AdventSolution(
    mapOf(
        """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent() to 4140
    ),
    mapOf(
        """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent() to 3993
    ),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val lines = input.split("\n")
        val firstLine = lines.first()
        val result = lines.drop(1).fold(firstLine) { last, line ->
            var next = "[$last,$line]"
            var reduced = next.reduce()
            while(next != reduced) {
                next = reduced
                reduced = next.reduce()
            }
            next
        }
        val node = result.split(Regex("""(?<=[\d\[\],])|(?=[\d\[\],])""")).toNode().first.left as Node
        return node.magnitude()
    }

    override fun solveProblem2(input: String): Any? {
        val lines = input.split("\n")
        return lines.maxOf { line1 ->
            lines.maxOf { line2 ->
                if(line1 != line2) {
                    var next = "[$line1,$line2]"
                    var reduced = next.reduce()
                    while(next != reduced) {
                        next = reduced
                        reduced = next.reduce()
                    }
                    val node = next.split(Regex("""(?<=[\d\[\],])|(?=[\d\[\],])""")).toNode().first.left as Node
                    node.magnitude()
                } else {
                    0L
                }
            }
        }
    }

    private fun String.reduce(): String {
        var newString = ""
        var nextNum: Int? = null
        var exploded: Boolean = false
        trim().split(Regex("""(?<=(\[\d\d?,\d\d?\]))|(?=(\[\d\d?,\d\d?\]))""")).forEach { token ->
            val numToAdd = nextNum
            val nextToken = if(exploded) {
                if(numToAdd != null) {
                    val revised = token.addToFirstNumber(numToAdd)
                    if(revised != token)
                        nextNum = null
                    revised
                } else {
                    token
                }
            } else if(token.matches(Regex("""\[\d\d?,\d\d?\]"""))) {
                val (left, right) = token.replace(Regex("""\[|\]"""), "").splitInTwo(",") { t -> t.toInt() }
                if(newString.count('[') - newString.count(']') >= 4) {
                    newString = newString.addToLastNumber(left)
                    exploded = true
                    nextNum = right
                    "0"
                } else {
                    token
                }
            } else {
                token
            }
            newString += nextToken
        }
        if (!exploded) {
            newString = ""
            var splat = false
            trim().split(Regex("""(?<=(\d\d))|(?=(\d\d))""")).forEach { token ->
                val nextToken = if (!splat && token.matches(Regex("""\d\d"""))) {
                    val nextInt = token.toInt()
                    splat = true
                    "[${nextInt / 2},${ceil(nextInt / 2.0).toInt()}]"
                } else {
                    token
                }
                newString += nextToken
            }
        }

        return newString
    }

    fun String.addToFirstNumber(number: Int): String {
        val firstNumber = splitInts(Regex("[^-0-9]+")).firstOrNull()
        return if(firstNumber != null) {
            val i = indexOf(firstNumber.toString())
            substring(0, i) + (firstNumber + number).toString() + substring(i + firstNumber.toString().length)
        } else {
            this
        }
    }

    fun String.addToLastNumber(number: Int): String {
        val lastNumber = splitInts(Regex("[^-0-9]+")).lastOrNull()
        return if(lastNumber != null) {
            val i = lastIndexOf(lastNumber.toString())
            substring(0, i) + (lastNumber + number).toString() + substring(i + lastNumber.toString().length)
        } else {
            this
        }
    }

    fun String.count(c: Char) = count { it == c }

    data class Node(val left: Any?, val right: Any?) {
        fun magnitude(): Long {
            return 3 * magnitude(left) + 2 * magnitude(right)
        }

        fun magnitude(n: Any?): Long {
            return when(n) {
                is Int -> n.toLong()
                is Node -> n.magnitude()
                else -> 0L
            }
        }
    }

    fun List<String>.toNode(startingAt: Int = 0): Pair<Node, Int> {
        var onRight = false
        var left: Any? = null
        var right: Any? = null
        var index = startingAt
        while(index < size) {
            when(val token = get(index)) {
                "," -> {
                    onRight = true
                }
                "[" -> {
                    val (next, nextIndex) = toNode(index + 1)
                    index = nextIndex
                    if(onRight) right = next else left = next
                }
                "]" -> {
                    return Node(left, right) to index
                }
                "" -> {}
                else -> {
                    val next = token.toInt()
                    if(onRight) right = next else left = next
                }
            }
            index += 1
        }
        return Node(left, right) to 0
    }
}