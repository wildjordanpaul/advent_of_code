package advent22

import shared.AdventSolution
import shared.splitInTwo
import java.math.BigInteger

class Day11 : AdventSolution(
    mapOf("""
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent() to 10605),
    2713310158
) {
    override fun solveProblem1(input: String): Any? {
        return calculateWorryLevels(input, 20)
    }

    override fun solveProblem2(input: String): Any? {
        return calculateWorryLevels(input, 10000, false)
    }

    private fun calculateWorryLevels(input: String, rounds: Int, managingWorry: Boolean = true): BigInteger {
        val monkeys = input.split("\n\n").map(::toMonkey).associateBy { it.id }
        val lcd = monkeys.map { it.value.divider }.fold(BigInteger.ONE) { acc, next -> acc.times(next) }
        repeat(rounds) { _ ->
            monkeys.values.forEach { monkey ->
                monkey.inspectItems(managingWorry, lcd).forEach { (id, items) ->
                    monkeys.getValue(id).add(items)
                }
            }
        }
        val topTwo = monkeys.values.map { it.inspectionCount }.sorted().takeLast(2)
        return topTwo.first().times(topTwo.last())
    }

    private fun toMonkey(input: String): Monkey {
        val lines = input.split("\n")
        val id = lines[0].split(" ").last().dropLast(1)
        val items = lines[1].split(": ").last()
            .split(Regex("[ ,]"))
            .filter(String::isNotBlank)
            .map { Item(it.toBigInteger()) }
            .toMutableList()

        val (opCode, opAmountString) = lines[2].split("= old ").last().splitInTwo(" ")
        val op: (BigInteger) -> BigInteger = { old ->
            val opAmount = if(opAmountString == "old") old else opAmountString.toBigInteger()
            when(opCode) {
                "*" -> old * opAmount
                "+" -> old + opAmount
                "-" -> old - opAmount
                "/" -> old / opAmount
                else -> throw RuntimeException("wat?")
            }
        }

        val divider = lines[3].split(" ").last().toBigInteger()

        val trueMonkey = lines[4].split(" ").last()
        val falseMonkey = lines[5].split(" ").last()

        return Monkey(id, items, op, divider, trueMonkey, falseMonkey)
    }

    private class Monkey(
        val id: String,
        val items: MutableList<Item>,
        val op: (BigInteger) -> BigInteger,
        val divider: BigInteger,
        val trueMonkey: String,
        val falseMonkey: String
    ) {

        var inspectionCount: BigInteger = BigInteger.ZERO

        fun inspectItems(managingWorry: Boolean, lcd: BigInteger): Map<String, List<Item>> {
            val thrown = items.groupBy { inspect(it, managingWorry, lcd) }
            items.clear()
            return thrown
        }

        fun inspect(item: Item, managingWorry: Boolean, lcd: BigInteger): String {
            inspectionCount = inspectionCount.inc()
            item.worryLevel = op(item.worryLevel).mod(lcd)
            if(managingWorry) item.worryLevel = item.worryLevel.divide(BigInteger("3"))
            val pass = item.worryLevel.mod(divider) == BigInteger.ZERO
            return if(pass) trueMonkey else falseMonkey
        }

        fun add(newItems: List<Item>) {
            items.addAll(newItems)
        }
    }

    private data class Item(var worryLevel: BigInteger)
}