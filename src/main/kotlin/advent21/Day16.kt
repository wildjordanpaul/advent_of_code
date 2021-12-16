package advent21

import shared.AdventSolution
import shared.Point
import shared.shortestDistance
import shared.splitAt
import java.util.*

class Day16 : AdventSolution(
    mapOf(
        """8A004A801A8002F478""".trimIndent() to 16,
        """620080001611562C8802118E34""".trimIndent() to 12,
        """C0015000016115A2E0802F182340""".trimIndent() to 23,
        """A0016C880162017C3686B18A3D4780""".trimIndent() to 31,
    ),
    mapOf(
        "C200B40A82" to 3,
        "04005AC33890" to 54,
        "880086C3E88112" to 7,
        "CE00C43D881120" to 9,
        "D8005AC2A8F0" to 1,
        "F600BC2D8F" to 0,
        "9C005AC2F8F0" to 0,
        "9C0141080250320F1802104A08" to 1
    ),
    pullInputFromNorthPole = true
) {
    override fun solveProblem1(input: String): Any? {
        val (packet, _) =  input.toBits().parsePacket()
        return packet.sumVersions()
    }

    override fun solveProblem2(input: String): Any? {
        val (packet, _) =  input.toBits().parsePacket()
        return packet.value()
    }

    private fun String.toBits(): String {
        return map {
            when(it) {
                '0' -> "0000"
                '1' -> "0001"
                '2' -> "0010"
                '3' -> "0011"
                '4' -> "0100"
                '5' -> "0101"
                '6' -> "0110"
                '7' -> "0111"
                '8' -> "1000"
                '9' -> "1001"
                'A' -> "1010"
                'B' -> "1011"
                'C' -> "1100"
                'D' -> "1101"
                'E' -> "1110"
                'F' -> "1111"
                else ->  ""
            }
        }.joinToString("")
    }

    data class Packet(
        val version: Int,
        val typeID: Int,
        val literalValue: Long = 0L,
        val subPackets: List<Packet> = listOf()
    ) {
        fun sumVersions(): Int {
            return version + subPackets.sumOf(Packet::sumVersions)
        }

        fun value(): Long {
            return when(typeID) {
                4 -> literalValue
                0 -> subPackets.sumOf(Packet::value)
                1 -> subPackets.fold(1) { acc, sp -> acc * sp.value() }
                2 -> subPackets.minOf { it.value() }
                3 -> subPackets.maxOf { it.value() }
                5 -> if(subPackets.first().value() > subPackets.last().value()) 1L else 0L
                6 -> if(subPackets.first().value() < subPackets.last().value()) 1L else 0L
                7 -> if(subPackets.first().value() == subPackets.last().value()) 1L else 0L
                else -> 0L
            }
        }
    }

    private fun String.parsePacket(): Pair<Packet, String> {
        val version = substring(0..2).toInt(2)
        val typeID = substring(3..5).toInt(2)
        var rem = substring(6)
        return when(typeID) {
            4 -> {
                var lastStartBit = '1'
                var literalValue = ""
                while(lastStartBit == '1') {
                    val (next, nextRem) = rem.splitAt(5)
                    literalValue += next.takeLast(4)
                    lastStartBit = next.first()
                    rem = nextRem
                }
                return Packet(version, typeID, literalValue.toLong(2)) to rem
            }
            else -> {
                val lengthTypeID = rem.first()
                when(lengthTypeID) {
                    '0' -> {
                        val subPacketLength = rem.substring(1..15).toInt(2)
                        var nextString = rem.substring(16 until 16+subPacketLength)
                        val subPackets = mutableListOf<Packet>()
                        while(nextString.length > 2) {
                            val (packet, nextRem) = nextString.parsePacket()
                            nextString = nextRem
                            subPackets.add(packet)
                        }
                        Packet(version, typeID, subPackets = subPackets) to rem.substring(16+subPacketLength)
                    }
                    else -> {
                        val subPacketCount = rem.substring(1..11).toInt(2)
                        var nextString = rem.substring(12)
                        val subPackets = (0 until subPacketCount).map {
                            val (packet, nextRem) = nextString.parsePacket()
                            nextString = nextRem
                            packet
                        }
                        Packet(version, typeID, subPackets = subPackets) to nextString
                    }
                }

            }
        }
    }
}