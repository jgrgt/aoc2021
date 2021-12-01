package days

class Day1 : Day(1) {

    override fun partOne(): Any {
        val depths = inputList.map { it.toInt() }
        val increases = DepthIncreaseCounter.count(depths)
        println("Increases: $increases")
        return increases
    }

    override fun partTwo(): Any {
        val depths = inputList.map { it.toInt() }
        val increases = DepthIncreaseCounter.count(smoother(depths))
        println("Increases: $increases")
        return increases
    }
}

fun smoother(depths: List<Int>): List<Int> {
    return depths.windowed(3, 1, false).map { it.sum() }
}

data class DepthIncreaseCounter(val count: Int = 0, val previous: Int) {
    companion object {
        fun count(depths: List<Int>): Int {
            if (depths.isEmpty() || depths.size == 1) {
                return 0
            }

            val start = DepthIncreaseCounter(0, depths[0])
            val acc = depths.drop(1).fold(start) { acc, d ->
                acc.add(d)
            }

            return acc.count
        }
    }

    private fun add(depth: Int): DepthIncreaseCounter {
        return if (depth > previous) {
            copy(count = count + 1, previous = depth)
        } else {
            copy(count = count, previous = depth)
        }
    }
}
