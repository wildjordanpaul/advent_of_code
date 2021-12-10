package advent21

import shared.AdventSolution
import java.math.BigInteger

class Day10 : AdventSolution(
    mapOf("""
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent() to 26397),
    mapOf("""
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent() to 288957),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return input.split(Regex("\n")).sumOf {
            when (it.firstInvalidChar()) {
                ')' -> 3L
                ']' -> 57L
                '}' -> 1197L
                '>' -> 25137L
                else -> 0L
            }
        }
    }

    override fun solveProblem2(input: String): Any? {
        return input.split(Regex("\n")).mapNotNull {
            it.remainingChars()?.fold(BigInteger.ZERO) { bi, char ->
                bi.times(BigInteger.valueOf(5L)).add(when(char) {
                    ')' -> BigInteger.ONE
                    ']' -> BigInteger.TWO
                    '}' -> BigInteger.valueOf(3L)
                    '>' -> BigInteger.valueOf(4L)
                    else -> BigInteger.ZERO
                })
            }
        }.sorted().run { get(count() / 2) }
    }

    private fun String.firstInvalidChar(): Char? {
        val s = mutableListOf<Char>()
        forEach {
            when(it) {
                '(' -> s.add(')')
                '[' -> s.add(']')
                '{' -> s.add('}')
                '<' -> s.add('>')
                else -> {
                    if(s.last() != it) return it
                    else s.removeLast()
                }
            }
        }
        return null
    }

    private fun String.remainingChars(): List<Char>? {
        val s = mutableListOf<Char>()
        forEach {
            when(it) {
                '(' -> s.add(')')
                '[' -> s.add(']')
                '{' -> s.add('}')
                '<' -> s.add('>')
                else -> {
                    if(s.last() != it) return null
                    else s.removeLast()
                }
            }
        }
        return s.reversed()
    }
}