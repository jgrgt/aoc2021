package days

class Day3 : Day(3) {
    override fun partOne(): Any {
        return p1(inputList)
    }

    override fun partTwo(): Any {
        return p2(inputList)
    }

    fun p1(l: List<String>): Int {
        val total = l.size
        val first = l[0]
        val amountOfBits = first.length
        val ones = mutableListOf<Int>()

        repeat(amountOfBits) {
            ones.add(0)
        }

        l.map {
            it.forEachIndexed { i, s ->
                when (s) {
                    '1' -> ones[i]++
                    '0' -> {
                    }
                    else -> error("Unexpected character $s")
                }
            }
        }

        var (gamma, epsilon) = buildGammaAndEpsilon(ones, total)

        return gamma * epsilon
    }

    fun p2(l: List<String>): Int {
        val oxString = oxygen(l)
        val ox = oxString.toInt(2)
        val co2String = co2scrubber(l)
        val co2 = co2String.toInt(2)
        return ox * co2
    }

    fun buildGammaAndEpsilon(
        ones: List<Int>,
        total: Int
    ): Pair<Int, Int> {
        var gamma = 0
        var epsilon = 0
        ones.forEach { amountOfOnes ->
            gamma = gamma.shl(1)
            epsilon = epsilon.shl(1)
            if (amountOfOnes > (total / 2)) {
                gamma += 1
            } else {
                epsilon += 1
            }
        }
        return Pair(gamma, epsilon)
    }

    fun oxygen(l: List<String>, index: Int = 0): String {
        if (l.size == 1) {
            return l[0]
        }

        if (index >= l[0].length) {
            return l[0]
        }
        val (zeroes, ones) = l.partition {
            it[index] == '0'
        }

        return if (zeroes.size > ones.size) {
            oxygen(zeroes, index + 1)
        } else {
            oxygen(ones, index + 1)
        }
    }

    fun co2scrubber(l: List<String>, index: Int = 0): String {
        if (l.size == 1) {
            return l[0]
        }

        if (index >= l[0].length) {
            return l[0]
        }
        val (zeroes, ones) = l.partition {
            it[index] == '0'
        }

        return if (zeroes.size <= ones.size) {
            co2scrubber(zeroes, index + 1)
        } else {
            co2scrubber(ones, index + 1)
        }
    }
}

