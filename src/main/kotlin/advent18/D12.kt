package advent18

import shared.AdventSolution

fun main(args: Array<String>) = object : AdventSolution(
        mapOf(
            """
                initial state: #..#.#..##......###...###
                ...## => #
                ..#.. => #
                .#... => #
                .#.#. => #
                .#.## => #
                .##.. => #
                .#### => #
                #.#.# => #
                #.### => #
                ##.#. => #
                ##.## => #
                ###.. => #
                ###.# => #
                ####. => #
            """.trimIndent() to "325"
        ),
        mapOf(

        ),
        """
            initial state: #.##.#.##..#.#...##...#......##..#..###..##..#.#.....##..###...#.#..#...######...#####..##....#..###
            ##.## => .
            ##... => #
            ..#.# => #
            #.... => .
            #..#. => #
            .#### => .
            .#..# => .
            .##.# => .
            #.##. => #
            ####. => .
            ..##. => .
            ##..# => .
            .#.## => #
            .#... => .
            .##.. => #
            ..#.. => #
            #..## => #
            #.#.. => #
            ..### => #
            ...#. => #
            ###.. => .
            ##.#. => #
            #.#.# => #
            ##### => #
            ....# => .
            #.### => .
            .#.#. => #
            .###. => #
            ...## => .
            ..... => .
            ###.# => #
            #...# => .
        """.trimIndent()
) {
    val allRules = setOf("##.##","##...","..#.#","#....","#..#.",".####",".#..#",".##.#","#.##.","####.","..##.","##..#",".#.##",".#...",".##..","..#..","#..##","#.#..","..###","...#.","###..","##.#.","#.#.#","#####","....#","#.###",".#.#.",".###.","...##",".....","###.#","#...#")

    private fun String.toRule(): D13Rule {
        val (i1, i2) = """(.....) => (.)""".toRegex().find(this)!!.destructured
        return D13Rule(i1, i2)
    }

    private fun solve(input: String, iterations: Long): String {
        val inputs = input.split("\n")
        var currentState = inputs[0].split(' ').last()
        val rules = inputs.takeLast(inputs.size-1).map { it.toRule() }.toMutableList()
        (allRules - rules.map { it.input }).forEach {
            rules.add(D13Rule(it, "."))
        }
        var initialIndex = 0

        rules.removeIf { it.input[2].toString() == it.result }

        var prevCounter = 0
        var diffCounter = 0
        var diffSameCounter = 0

        (1..iterations).forEach { iteration ->
            if(currentState.take(5).contains("#")) {
                currentState = ".....$currentState"
                initialIndex -= 5
            }

            if(currentState.takeLast(5).contains("#"))
                currentState = "$currentState....."

            var nextState = currentState
            rules.forEach { rule ->
                var currentMatch = rule.matcher.find(currentState)

                while(currentMatch != null) {
                    val i: Int = currentMatch.range.first+2
                    nextState = nextState.replaceRange(i, i+1, rule.result)
                    currentMatch = rule.matcher.find(currentState, i-1)
                }
            }
//            println("$iteration $nextState")
            currentState = nextState

            var counter = 0
            currentState.forEachIndexed { index, c ->
                if(c == '#') counter += initialIndex+index
            }

            val newDiffCounter = counter - prevCounter
            if(newDiffCounter == diffCounter)
                diffSameCounter++
            else diffSameCounter = 0

            diffCounter = newDiffCounter
            prevCounter = counter

            if(diffSameCounter > 20) {
                return@solve (counter + (iterations-iteration)*diffCounter).toString()
            }
        }

        var counter = 0
        currentState.forEachIndexed { index, c ->
            if(c == '#') counter += initialIndex+index
        }

        return "$counter"
    }

    override fun solveProblem1(input: String): String {
        return solve(input, 20)
    }

    override fun solveProblem2(input: String): String {
        return solve(input, 50000000000)
    }

}.solve()

data class D13Rule(
    val input: String,
    val result: String = input,
    val matcher: Regex = input.replace(""".""","""\.""").toRegex()
)