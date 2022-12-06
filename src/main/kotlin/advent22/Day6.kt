package advent22

import shared.AdventSolution

class Day6 : AdventSolution(
    mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 7,
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
        "nppdvjthqldpwncqszvftbrmjlhg" to 6,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
    ),
    mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
        "nppdvjthqldpwncqszvftbrmjlhg" to 23,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26,
    )
) {
    override fun solveProblem1(input: String): Any? {
        return input.indexOfMarker(4)
    }

    override fun solveProblem2(input: String): Any? {
        return input.indexOfMarker(14)
    }

    private fun String.indexOfMarker(n: Int): Int {
        val q = mutableListOf<Char>()
        return indexOfFirst { l ->
            q.add(l)
            if(q.size > n) q.removeFirst()
            q.toSet().size == n
        } + 1
    }
}