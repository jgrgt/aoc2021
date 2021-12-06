package days

class Day6 : Day(6) {
    override fun partOne(): Any {
        return p1(inputList, 80)
    }

    fun p1(inputList: List<String>, days: Int): Int {
        val times = inputList[0].split(",").map { it.toInt() }
        return doNextDays(times, days).size
    }

    private fun doNextDays(times: List<Int>, days: Int): List<Int> {
        if (days == 0) {
            return times
        }

        return doNextDays(nextDay(times), days - 1)
    }


    override fun partTwo(): Any {
        return 0
    }

    fun nextDay(timers: List<Int>): List<Int> {
        val amountOfNewBorns = timers.count { it == 0 }
        val updated = timers.map {
            if (it == 0) {
                6
            } else {
                it - 1
            }
        }
        return updated + List(amountOfNewBorns) { 8 }
    }
}
