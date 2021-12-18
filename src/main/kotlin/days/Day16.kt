package days

import java.math.BigInteger
import java.util.*

class Day16 : Day(16) {

    data class Packet(
        // header
        val version: Int, // 3 bits
        val typeId: PacketType, // 3 bits
        //body
        val body: Any,
    ) {
        companion object {
            fun parse(hex: String): Packet {
                val firstByte = hex.substring(0..1).toInt(16)
                // 12345678
                // VVVTTT??
                val version = firstByte.shr(5)
                val type = firstByte.shr(2) and (4 + 2 + 1)
                return when (type) {
                    PacketType.LITERAL.tag -> Packet(version, PacketType.LITERAL, parseLiteral(hex, 3 + 3))
                    else -> error("Unknown packet type $type")
                }
            }

            private fun parseLiteral(hex: String, offset: Int): Int {
                val bitString = hex.map { toBitString(it) }.joinToString("")
//                val bigInt = BigInteger(hex, 16)
//                val bitString = toBitString { bigInt.testBit(it) }
                val interestingBit = bitString.substring(offset)
                val chunked = interestingBit.chunked(5)
                val parts = chunked.takeWhile { it[0] == '0'}.map { it.substring(1)}
                val allParts = parts + chunked[parts.size].substring(1)
                val joined = allParts.joinToString("")
                return joined.toInt(2)
//                val bi = BigInteger(hex, 16)
//                val bytes = hex.chunked(2).map { chunk ->
//                    chunk.toInt(16).toByte()
//                }.toByteArray()
//                val all = BitSet.valueOf(bytes)
//                val literalValue = all.getAll(offset)
//                val chunkSequence = literalValue.chunked(5)
//                val parts = chunkSequence.takeWhile { bs -> bs[0] }.toList()
//                val allParts = parts + chunkSequence.drop(parts.size).first()
//                val value = allParts.map { bs -> bs.getAll(1) }.fold(0) { acc, bs ->
//                    acc.shl(bs.size()) or bs.toByteArray()[0].toInt()
//                }
            }
        }
    }

    enum class PacketType(val tag: Int) {
        LITERAL(4),
    }
}

private fun BitSet.chunked(size: Int): Sequence<BitSet> {
    val bs = this
    return sequence {
        var start = 0
        var end = size
        while (end < bs.size()) {
            yield(bs.get(start, end))
            start = end + 1
            end = start + size
        }
    }
}

private fun toBitString(h: Char) : String {
    return when(h) {
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
        else -> error("Invalid hex char")
    }

}

private fun toBitString(aBitTester: (Int) -> Boolean): String {
    val sb = StringBuilder()
    for (i in 0..15) {
        sb.append(if (aBitTester.invoke(i)) "1" else "0")
    }
    return sb.toString()
}


private fun BitSet.getAll(offset: Int): BitSet {
    return get(offset, size() - 1)
}

