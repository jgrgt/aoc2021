package days

class Day6 : Day(6) {
    override fun partOne(): Any {
        return p1(inputList, 80)
    }

    fun p1(inputList: List<String>, days: Int): Long {
        val times = inputList[0].split(",").map { it.toInt() }
        val startPopulation = Population(
            age0 = times.count { it == 0 }.toLong(),
            age1 = times.count { it == 1 }.toLong(),
            age2 = times.count { it == 2 }.toLong(),
            age3 = times.count { it == 3 }.toLong(),
            age4 = times.count { it == 4 }.toLong(),
            age5 = times.count { it == 5 }.toLong(),
            age6 = times.count { it == 6 }.toLong(),
            age7 = times.count { it == 7 }.toLong(),
            age8 = times.count { it == 8 }.toLong(),
        )
        return doNextDays(startPopulation, days).size
    }

    private fun doNextDays(population: Population, days: Int): Population {
        if (days == 0) {
            return population
        }

        return doNextDays(population.nextDay(), days - 1)
    }


    override fun partTwo(): Any {
        return p1(inputList, 256)
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

data class Population(
    val age0: Long,
    val age1: Long,
    val age2: Long,
    val age3: Long,
    val age4: Long,
    val age5: Long,
    val age6: Long,
    val age7: Long,
    val age8: Long,
) {
    val size = age0 + age1 + age2 + age3 + age4 + age5 + age6 + age7 + age8

    fun nextDay(): Population {
        return Population(
            age0 = age1,
            age1 = age2,
            age2 = age3,
            age3 = age4,
            age4 = age5,
            age5 = age6,
            age6 = age7 + age0,
            age7 = age8,
            age8 = age0,
        )
    }
}
