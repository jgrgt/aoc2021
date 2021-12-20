package days

import util.MutableMatrix

class Day20 : Day(20) {
    override fun runPartOne(lines: List<String>): Any {
        val mapping = lines[0].map { c ->
            when (c) {
                '#' -> 1
                '.' -> 0
                else -> error("Invalid character $c")
            }
        }
        val matrix = MutableMatrix.fromSingleDigits(lines.drop(2)) { c ->
            when (c) {
                '#' -> 1
                '.' -> 0
                else -> error("Invalid character $c")
            }
        }
        val enhancedMatrix = enhance(matrix, mapping)
        enhancedMatrix.print { false }
        println()
        val doubleEnhancedMatrix = enhance(enhancedMatrix, mapping)
        doubleEnhancedMatrix.print { false }
        return doubleEnhancedMatrix.count { it ==  1}
    }

    fun enhance(matrix: MutableMatrix<Int>, mapping: List<Int>): MutableMatrix<Int> {
        val oldMatrix = matrix.surround(0).surround(0) // Surround with a double empty border

        val newMatrix = oldMatrix.clone()
        newMatrix.forEachPoint { p ->
            if (oldMatrix.isNotOnEdge(p)) {
                oldMatrix.window(p) { m ->
                    val bits = m.items[0] + m.items[1] + m.items[2]
                    check(bits.size == 9)
                    val index = bits.joinToString("").toInt(2)
                    newMatrix.set(p, mapping[index])
                }
            }
        }
        return newMatrix
    }
}
