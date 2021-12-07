package days

import kotlin.math.abs

class Day7 : Day(7) {
    override fun runPartOne(lines: List<String>): Any {
        val horizontalPositions = lines[0].split(",").map { it.trim().toInt() }
        val median = horizontalPositions.median()
        return horizontalPositions.sumOf { abs(it - median) }
    }

    override fun runPartTwo(lines: List<String>): Any {
        val horizontalPositions = lines[0].split(",").map { it.trim().toInt() }
        val min = horizontalPositions.minOrNull()!!
        val max = horizontalPositions.maxOrNull()!!
        val costs = (min..max).map { target ->
            calculateCost(target, horizontalPositions)
        }
        return costs.minOrNull()!!
    }

    private fun calculateCost(target: Int, horizontalPositions: List<Int>): Int {
        return horizontalPositions.sumOf { pos ->
            cost(abs(target - pos))
        }
    }

    private fun cost(steps: Int): Int {
        if (steps == 0) {
            return 0
        }
        return steps + cost(steps - 1)
    }
}

fun List<Int>.median(): Int {
    val sorted = this.sorted()
    // simplified, does not average out if 2 options
    return sorted[this.size / 2]
}
