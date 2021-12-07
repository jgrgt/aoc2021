package days

import kotlin.math.abs

class Day7 : Day(7) {
    override fun runPartOne(lines: List<String>): Any {
        val horizontalPositions = lines[0].split(",").map { it.trim().toInt() }
        val median = horizontalPositions.median()
        return horizontalPositions.sumOf { abs(it - median) }
    }
}

fun List<Int>.median(): Int {
    val sorted = this.sorted()
    // simplified, does not average out if 2 options
    return sorted[this.size / 2]
}
