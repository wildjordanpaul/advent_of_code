package advent22

import shared.AdventSolution
import shared.splitLines
import java.math.BigInteger

class Day7 : AdventSolution(
    mapOf("""
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent() to 95437),
    24933642
) {
    override fun solveProblem1(input: String): Any? {
        return parseFileSystem(input).filter {
            it.name.isNotBlank() && it.size <= BigInteger("100000")
        }.sumOf { it.size }
    }

    override fun solveProblem2(input: String): Any? {
        val fs = parseFileSystem(input)

        val totalSize = BigInteger("70000000")
        val updateSize = BigInteger("30000000")
        val required = updateSize - (totalSize - fs.first().size)
        return fs.filter { it.size >= required }.minBy { it.size - required }.size
    }

    private fun parseFileSystem(input: String): List<Dir> {
        val root = Dir("")
        var current: Dir = root
        val nodes = mutableListOf(root)

        input.splitLines().forEach { line ->
            val words = line.split(" ")
            when(words.first()) {
                "$" -> {
                    when(words[1]) {
                        "cd" -> {
                            when(words[2]) {
                                ".." -> current = current.parent!!
                                "/" -> current = root
                                else -> current = current.children.first { it.name == words[2] }
                            }
                        }
                        "ls" -> {}
                    }
                }
                "dir" -> {
                    val node = Dir(words[1], current)
                    if (current.addChild(node)) {
                        nodes.add(node)
                    }
                }
                else -> {
                    current.addFile(File(words[1], words[0].toBigInteger()))
                }
            }
        }
        return nodes
    }

    class Dir(val name: String, val parent: Dir? = null) {
        private val files = mutableListOf<File>()
        var size: BigInteger = BigInteger.ZERO
            private set
        val children = mutableListOf<Dir>()

        fun addChild(d: Dir): Boolean {
            return if (children.none { it.name == d.name }) {
                children.add(d)
                true
            } else false
        }

        fun addFile(file: File) {
            if (files.none { it.name == file.name }) {
                files.add(file)
                addNestedFile(file)
            }
        }

        private fun addNestedFile(file: File) {
            size = size.plus(file.size)
            parent?.addNestedFile(file)
        }
    }

    data class File(val name: String, val size: BigInteger)
}