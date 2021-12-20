package days

import util.MutableMatrix
import util.Point

class Day20 : Day(20) {
    override fun runPartOne(lines: List<String>): Any {
        val mapping = parseMapping(lines[0])
        val matrix = parseMatrix(lines.drop(2)).surround(0)
        val enhancedMatrix = enhance(matrix, mapping)
        enhancedMatrix.printSep("") { false }
        println()
        println()
        println()
        val fixedEnhanced = enhancedMatrix
            .unsurround()
            .surround(mapping[0])
        fixedEnhanced.printSep("") { false }

        println()
        println()
        println()
        val doubleEnhancedMatrix = enhance(
            fixedEnhanced,
            mapping
        )
        doubleEnhancedMatrix.printSep("") { false }
        return doubleEnhancedMatrix.unsurround().count { it == 1 }
    }

    override fun runPartTwo(lines: List<String>): Any {
        val mapping = parseMapping(lines[0])
        val matrix = parseMatrix(lines.drop(2)).surround(0)
        var enhancedMatrix = enhance(matrix, mapping)
        repeat(49) {
            // first time is 0, should give 1
            val default = if (it % 2 == 0) {
                mapping[0]
            } else {
                0
            }
            val fixedEnhanced = enhancedMatrix
                .unsurround()
                .surround(default)
            enhancedMatrix = enhance(
                fixedEnhanced,
                mapping
            )
        }
        return enhancedMatrix.unsurround().count { it == 1 }
    }

    fun parseMatrix(lines: List<String>) =
        MutableMatrix.fromSingleDigits(lines) { c ->
            when (c) {
                '#' -> 1
                '.' -> 0
                else -> error("Invalid character $c")
            }
        }

    fun parseMapping(line: String) = line.map { c ->
        when (c) {
            '#' -> 1
            '.' -> 0
            else -> error("Invalid character $c")
        }
    }

    fun enhance(matrix: MutableMatrix<Int>, mapping: List<Int>): MutableMatrix<Int> {
        val newMatrix = matrix.clone()
        val default = matrix.get(Point(0, 0))
        println("Using default $default")
        matrix.forEachPoint { p ->
            matrix.windowOrDefault(p, default) { m ->
                val bits = m.items[0] + m.items[1] + m.items[2]
                check(bits.size == 9)
                val index = bits.joinToString("").toInt(2)
                val mapped = mapping[index]
                newMatrix.set(p, mapped)
            }
        }
        return newMatrix.surround(0)
    }
}
