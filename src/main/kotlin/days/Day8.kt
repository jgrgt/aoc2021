package days

class Day8 : Day(8) {
    // 0: abcefg
    // 1: cf
    // 2: acdeg
    // 3: acdfg
    // 4: bcdf
    // 5: abdfg
    // 6: abdefg
    // 7: acf
    // 8: abcdefg
    // 9: abcdfg
    val sevenDigit = mapOf(
          "abcefg" to 0,
          "cf" to 1,
          "acdeg" to 2,
          "acdfg" to 3,
          "bcdf" to 4,
          "abdfg" to 5,
          "abdefg" to 6,
          "acf" to 7,
          "abcdefg" to 8,
          "abcdfg" to 9,
    )
    override fun runPartOne(lines: List<String>): Any {
        val easyDigitSizes = listOf(2, 4, 3, 7)
        return lines.map { line ->
            val (_, digits) = line.split("|").map { it.trim() }
            val digitStrings = digits.split(" ").map { it.trim() }
            digitStrings.count { easyDigitSizes.contains(it.length) }
        }.sum()
    }

    override fun runPartTwo(lines: List<String>): Any {
        return lines.sumOf { line ->
            val (signal, digits) = line.split("|").map { it.trim() }
            val digitStrings = digits.split(" ").map { it.trim() }
            val mapping = decode(signal)
            val translated = digitStrings.map { translate(it, mapping) }
            1000 * translated[0] + 100 * translated[1] + 10 * translated[2] + translated[3]
        }
    }

    fun translate(digit: String, mapping: List<Char>) : Int {
        val key = digit.toCharArray().map { inverseMap(it, mapping) }.sorted().joinToString("")
        return sevenDigit[key]!!
    }

    fun inverseMap(c: Char, mapping: List<Char>) : Char {
        val index = mapping.indexOf(c)
        return Char('a'.code + index)
    }

    fun decode(signal: String): List<Char> {
        val signals = signal.split(" ").map { it.trim() }
        val possibilities = Possibilities()
        // 1 cf
        val one = signals.find { it.length == 2 }!!
        val oneChars = one.toCharArray().toList()
        possibilities.registerPossibilities('c', oneChars)
        possibilities.registerPossibilities('f', oneChars)

        // 4 bcdf
        val four = signals.find { it.length == 4 }!!
        val fourChars = four.toCharArray().toList()
        possibilities.registerPossibilities('b', fourChars)
        possibilities.registerPossibilities('c', fourChars)
        possibilities.registerPossibilities('d', fourChars)
        possibilities.registerPossibilities('f', fourChars)
        // 7 acf
        val seven = signals.find { it.length == 3 }!!
        val sevenChars = seven.toCharArray().toList()
        possibilities.registerPossibilities('a', sevenChars)
        possibilities.registerPossibilities('c', sevenChars)
        possibilities.registerPossibilities('f', sevenChars)

        // F is  the character that occurs 9 times
        val occurences = occurences(signals)
        val fIndex = occurences.indexOf(9)
        val chars = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
        val fMapping = chars[fIndex]
        possibilities.registerPossibilities('f', listOf(fMapping))
        // E is the character that occurs 4 times
        val eIndex = occurences.indexOf(4)
        val eMapping = chars[eIndex]
        possibilities.registerPossibilities('e', listOf(eMapping))
        // B is the character that occurs 6 times
        val bIndex = occurences.indexOf(6)
        val bMapping = chars[bIndex]
        possibilities.registerPossibilities('b', listOf(bMapping))
        // Now we should be able to figure it out
        return possibilities.solve()
    }

    fun occurences(signals: List<String>): List<Int> {
        val occurences = MutableList(7) { 0 }
        signals.forEach { signal ->
            signal.chars().forEach { c ->
                val index = c - 'a'.code
                occurences[index] = occurences[index] + 1
            }
        }
        return occurences.toList()
    }
}

class Possibilities {
    private val state = MutableList(7) {
        MutableList(7) { true } // true means possible
    }

    fun registerPossibilities(c: Char, options: List<Char>) {
        val inv = inverse(options)
        inv.forEach {
            disable(c, it)
        }
    }

    private fun disable(c: Char, o: Char) {
        disable(toIndex(c), toIndex(o))
    }

    private fun disable(c: Int, o: Int) {
        state[c][o] = false
    }

    private fun inverse(options: List<Char>): List<Char> {
        val candidates = mutableListOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
        candidates.removeAll(options)
        return candidates
    }

    private fun toIndex(c: Char): Int {
        return c.code - 'a'.code
    }

    fun solve(): List<Char> {
        repeat(7) { // 7 times should be enough to settle things down?
            checkRows()
            checkColumns()
        }
        return (0..6).map { c ->
            val o = state[c].indexOf(true)
            toChar(o)
        }
    }

    private fun checkColumns() {
        (0..6).forEach { i ->
            val mapping = (0..6).map { state[it][i] }
            val sure = mapping.count { it } == 1
            if (sure) {
                val c = mapping.indexOf(true)
                registerPossibilities(toChar(c), listOf(toChar(i)))
            }
        }
    }

    private fun toChar(c: Int): Char {
        return Char(c + 'a'.code)
    }

    private fun checkRows() {
        (0..6).forEach { i ->
            val mapping = state[i]
            val sure = mapping.count { it } == 1
            if (sure) {
                val o = mapping.indexOf(true)
                (0..6).forEach { otherI ->
                    if (i != otherI) {
                        disable(otherI, o)
                    }
                }
            }
        }
    }
}
