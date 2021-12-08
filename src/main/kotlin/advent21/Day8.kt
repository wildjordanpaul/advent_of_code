package advent21

import shared.AdventSolution
import shared.splitInTwo

class Day8 : AdventSolution(
    mapOf("""
        be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
        fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
        fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
        aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
        fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
        dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
        bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
        egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
        gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """.trimIndent() to 26),
    mapOf("""
        be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
        fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
        fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
        aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
        fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
        dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
        bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
        egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
        gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """.trimIndent() to 61229),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        return input.split(Regex("\n")).flatMap { line ->
            line.split("|").last().trim().split(" ").filter {
                listOf(2, 3, 4, 7).contains(it.length)
            }
        }.count()
    }

    override fun solveProblem2(input: String): Any? {
        return input.split(Regex("\n")).sumOf { line ->
            val (digits, output) = line.splitInTwo("|") { it.trim() }
            val lookup = digits.split(" ").toLookupMap()
            output.split(" ").map { lookup[it.sorted()] }.joinToString("").toInt()
        }
    }

    private fun List<String>.toLookupMap(): Map<String, String> {
        val one = first { it.length == 2 }
        val seven = first { it.length == 3 }
        val four = first { it.length == 4 }
        val eight = first { it.length == 7 }

        val six = first { it.length == 6 && !it.containsAll(one) }
        val nine = first { it.length == 6 && it.containsAll(four) }
        val zero = first { it.length == 6 && it != six && it != nine }

        val three = first { it.length == 5 && it.containsAll(one) }
        val five = first { it.length == 5 && six.containsAll(it) }
        val two = first { it.length == 5 && it != five && it != three }

        return mapOf(
            zero to "0",
            one to "1",
            two to "2",
            three to "3",
            four to "4",
            five to "5",
            six to "6",
            seven to "7",
            eight to "8",
            nine to "9"
        ).mapKeys { it.key.sorted() }
    }

    private fun String.sorted() = toCharArray().sorted().joinToString("")

    private fun String.containsAll(s2: String) = toList().containsAll(s2.toList())

}