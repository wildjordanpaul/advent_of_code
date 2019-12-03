package advent18

import shared.AdventSolution

fun main(args: Array<String>) = object : AdventSolution(
    mapOf(
        "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin." to "CABDFE"
    ),
    mapOf(
        "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin." to "253"
    ),
    "Step F must be finished before step Q can begin.\nStep A must be finished before step K can begin.\nStep K must be finished before step R can begin.\nStep D must be finished before step X can begin.\nStep L must be finished before step T can begin.\nStep V must be finished before step W can begin.\nStep J must be finished before step N can begin.\nStep B must be finished before step W can begin.\nStep X must be finished before step C can begin.\nStep W must be finished before step I can begin.\nStep Q must be finished before step P can begin.\nStep E must be finished before step M can begin.\nStep C must be finished before step N can begin.\nStep U must be finished before step O can begin.\nStep O must be finished before step R can begin.\nStep N must be finished before step Z can begin.\nStep R must be finished before step I can begin.\nStep G must be finished before step H can begin.\nStep T must be finished before step H can begin.\nStep M must be finished before step P can begin.\nStep Y must be finished before step I can begin.\nStep S must be finished before step Z can begin.\nStep I must be finished before step H can begin.\nStep H must be finished before step P can begin.\nStep P must be finished before step Z can begin.\nStep Y must be finished before step P can begin.\nStep A must be finished before step O can begin.\nStep V must be finished before step O can begin.\nStep G must be finished before step Y can begin.\nStep K must be finished before step B can begin.\nStep I must be finished before step P can begin.\nStep D must be finished before step L can begin.\nStep A must be finished before step P can begin.\nStep O must be finished before step T can begin.\nStep F must be finished before step C can begin.\nStep M must be finished before step S can begin.\nStep V must be finished before step Q can begin.\nStep G must be finished before step I can begin.\nStep O must be finished before step I can begin.\nStep N must be finished before step I can begin.\nStep E must be finished before step O can begin.\nStep N must be finished before step S can begin.\nStep J must be finished before step H can begin.\nStep C must be finished before step P can begin.\nStep E must be finished before step N can begin.\nStep T must be finished before step P can begin.\nStep A must be finished before step G can begin.\nStep A must be finished before step V can begin.\nStep C must be finished before step H can begin.\nStep A must be finished before step Y can begin.\nStep E must be finished before step U can begin.\nStep T must be finished before step Y can begin.\nStep Q must be finished before step S can begin.\nStep Y must be finished before step S can begin.\nStep E must be finished before step P can begin.\nStep N must be finished before step T can begin.\nStep T must be finished before step M can begin.\nStep Q must be finished before step M can begin.\nStep H must be finished before step Z can begin.\nStep D must be finished before step Y can begin.\nStep J must be finished before step R can begin.\nStep U must be finished before step R can begin.\nStep K must be finished before step N can begin.\nStep A must be finished before step W can begin.\nStep A must be finished before step H can begin.\nStep X must be finished before step G can begin.\nStep V must be finished before step J can begin.\nStep W must be finished before step C can begin.\nStep I must be finished before step Z can begin.\nStep V must be finished before step H can begin.\nStep R must be finished before step H can begin.\nStep U must be finished before step N can begin.\nStep O must be finished before step Z can begin.\nStep X must be finished before step S can begin.\nStep E must be finished before step G can begin.\nStep W must be finished before step U can begin.\nStep U must be finished before step G can begin.\nStep D must be finished before step Z can begin.\nStep E must be finished before step R can begin.\nStep L must be finished before step B can begin.\nStep B must be finished before step R can begin.\nStep G must be finished before step T can begin.\nStep F must be finished before step K can begin.\nStep R must be finished before step S can begin.\nStep J must be finished before step Z can begin.\nStep Q must be finished before step U can begin.\nStep X must be finished before step O can begin.\nStep F must be finished before step I can begin.\nStep W must be finished before step R can begin.\nStep W must be finished before step Y can begin.\nStep M must be finished before step Y can begin.\nStep S must be finished before step I can begin.\nStep F must be finished before step O can begin.\nStep C must be finished before step Y can begin.\nStep N must be finished before step G can begin.\nStep O must be finished before step S can begin.\nStep Q must be finished before step O can begin.\nStep K must be finished before step T can begin.\nStep X must be finished before step Z can begin.\nStep L must be finished before step N can begin.\nStep S must be finished before step P can begin."
) {

    val NUMBER_OF_WORKERS = 5

    fun String.parseCase(): D7Case {
        val (r1, r2) = """Step (\w+) must be finished before step (\w+) can begin"""
            .toRegex()
            .find(this)!!
            .destructured
        return D7Case(r2.toCharArray()[0], r1.toCharArray()[0])
    }

    private fun String.toGraph(): MutableMap<Char, MutableSet<Char>> {
        val cases = split("\n").map { it.parseCase() }
        val graph = mutableMapOf<Char, MutableSet<Char>>()
        cases.forEach { case ->
            val set = graph[case.letter] ?: mutableSetOf()
            set.add(case.dependency)
            graph[case.letter] = set

            val depSet = graph[case.dependency] ?: mutableSetOf()
            graph[case.dependency] = depSet
        }
        return graph
    }

    private fun MutableMap<Char, MutableSet<Char>>.order(): String {
        val answer = mutableListOf<Char>()
        while(answer.size != size) {
            val possibles = filter { (letter, deps) ->
                !answer.contains(letter) && (deps.isEmpty() || deps.all { answer.contains(it) })
            }
            answer.add(possibles.keys.sorted().first())
        }
        return answer.joinToString("")
    }

    override fun solveProblem1(input: String): String {
        val graph = input.toGraph()
        return graph.order()
    }

    override fun solveProblem2(input: String): String {
        val graph = input.toGraph()
        val completed = mutableListOf<Char>()
        val inProgress = mutableListOf<Char>()
        val workers = (0 until NUMBER_OF_WORKERS).map { id -> D7Worker(id) }
        var seconds = 0
        while(completed.size < graph.size) {
            println()
            print("Second: $seconds")
            workers.filter { it.canAssign }.forEach { worker ->
                val possibles = graph.filter { (letter, deps) ->
                    !completed.contains(letter) && !inProgress.contains(letter) && (deps.isEmpty() || deps.all { completed.contains(it) })
                }
                val popped = possibles.keys.sorted().firstOrNull()
                if(popped != null) {
                    inProgress.add(popped)
                    worker.assign(popped)
                }
            }

            workers.forEach {
                print(", Worker ${it.id}: ${it.currentLetter}")
                val finished = it.doWork()
                if(finished != null) {
                    inProgress.remove(finished)
                    completed.add(finished)
                }
            }
            print(", Completed: ${completed.joinToString(",")}")
            seconds += 1
        }
        println()
        return seconds.toString()
    }

}.solve()

data class D7Worker(
    val id: Int,
    var currentLetter: Char? = null,
    var secondsLeft: Int = 0
) {
    val canAssign: Boolean
        get() = currentLetter == null

    fun assign(letter: Char) {
        currentLetter = letter
        secondsLeft = (letter - 'A') + BASE_SECONDS
    }

    fun doWork(): Char? {
        if(currentLetter != null) {
            secondsLeft --
            if(secondsLeft == 0) {
                val finished = currentLetter
                currentLetter = null
                return finished
            }
        }

        return null
    }

    companion object {
        const val BASE_SECONDS = 61
    }
}

data class D7Case(val letter: Char, val dependency: Char)