package advent20

import shared.AdventSolution
import shared.splitInTwo

class Day4 : AdventSolution(
    mapOf("""
        ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
        byr:1937 iyr:2017 cid:147 hgt:183cm
        
        iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
        hcl:#cfa07d byr:1929
        
        hcl:#ae17e1 iyr:2013
        eyr:2024
        ecl:brn pid:760753108 byr:1931
        hgt:179cm
        
        hcl:#cfa07d eyr:2025 pid:166559648
        iyr:2011 ecl:brn hgt:59in
    """.trimIndent() to 2),
    mapOf("""
        eyr:1972 cid:100
        hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

        iyr:2019
        hcl:#602927 eyr:1967 hgt:170cm
        ecl:grn pid:012533040 byr:1946

        hcl:dab227 iyr:2012
        ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

        hgt:59cm ecl:zzz
        eyr:2038 hcl:74454a iyr:2023
        pid:3556412378 byr:2007
        
        pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
        hcl:#623a2f

        eyr:2029 ecl:blu cid:129 byr:1989
        iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

        hcl:#888785
        hgt:164cm byr:2001 iyr:2015 cid:88
        pid:545766238 ecl:hzl
        eyr:2022

        iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
    """.trimIndent() to 4),
    input1 = loadInput("/advent20/D4_input1.txt")
) {

    companion object {
        val REQUIRED_KEYS = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    }

    override fun solveProblem1(input: String): Any {
        return input.split("\n\n").count { passport ->
            passport
                .split("\n", " ")
                .map { it.split(":").first() }
                .containsAll(REQUIRED_KEYS)
        }
    }

    override fun solveProblem2(input: String): Any {
        return input.split("\n\n").count { passport ->
            passport
                .split("\n", " ")
                .associate { it.splitInTwo(":") }
                .isValid()
        }
    }

    private fun Map<String, String>.isValid(): Boolean {
        return keys.containsAll(REQUIRED_KEYS) &&
                get("byr")!!.isValidByr() &&
                get("iyr")!!.isValidIyr()  &&
                get("eyr")!!.isValidEyr() &&
                get("hgt")!!.isValidHeight() &&
                get("hcl")!!.isValidHairColor() &&
                get("ecl")!!.isValidEyeColor() &&
                get("pid")!!.isValidPid()
    }

    private fun String.isValidByr() = toInt() in 1920..2002
    private fun String.isValidIyr() = toInt() in 2010..2020
    private fun String.isValidEyr() = toInt() in 2020..2030

    private fun String.isValidHeight(): Boolean {
        return when {
            endsWith("cm") ->
                removeSuffix("cm").toInt() in 150..193
            endsWith("in") ->
                removeSuffix("in").toInt() in 59..76
            else -> false
        }
    }

    private fun String.isValidHairColor() = "#[0-9a-f]{6}".toRegex().matches(this)
    private fun String.isValidEyeColor() = this in "amb blu brn gry grn hzl oth".split(" ")
    private fun String.isValidPid() = "[0-9]{9}".toRegex().matches(this)

}