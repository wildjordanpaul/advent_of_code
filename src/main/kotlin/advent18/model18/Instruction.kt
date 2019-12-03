package advent18.model18

enum class Instruction {
    ADDR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]] + register[input[1]]
            return output
        }
    },
    ADDI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]] + input[1]
            return output
        }
    },
    MULR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]] * register[input[1]]
            return output
        }
    },
    MULI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]] * input[1]
            return output
        }
    },
    BANR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]].and(register[input[1]])
            return output
        }
    },
    BANI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]].and(input[1])
            return output
        }
    },
    BORR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]].or(register[input[1]])
            return output
        }
    },
    BORI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]].or(input[1])
            return output
        }
    },
    SETR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = register[input[0]]
            return output
        }
    },
    SETI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = input[0]
            return output
        }
    },
    GTIR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = if(input[0] > register[input[1]]) 1 else 0
            return output
        }
    },
    GTRI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = if(register[input[0]] > input[1]) 1 else 0
            return output
        }
    },
    GTRR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = if(register[input[0]] > register[input[1]]) 1 else 0
            return output
        }
    },
    EQIR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = if(input[0] == register[input[1]]) 1 else 0
            return output
        }
    },
    EQRI {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = if(register[input[0]] == input[1]) 1 else 0
            return output
        }
    },
    EQRR {
        override fun execute(register: List<Int>, input: List<Int>): List<Int> {
            val output = register.toMutableList()
            output[input[2]] = if(register[input[0]] == register[input[1]]) 1 else 0
            return output
        }
    },
    ;

    abstract fun execute(register: List<Int>, input: List<Int>): List<Int>
}