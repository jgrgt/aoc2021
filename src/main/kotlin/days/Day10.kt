package days

class Day10 : Day(10) {
    override fun runPartOne(lines: List<String>): Any {
        return lines.map { line ->
            findInvalidChar(line.toCharArray().toList()).first
        }.sumOf { c ->
            val n: Int = when(c) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                null -> 0
                else -> error("invalid character $c")
            }
            n
        }
    }

    override fun runPartTwo(lines: List<String>): Any {
        val scores = lines.map { line ->
            findInvalidChar(line.toCharArray().toList())
        }.filter { (c, _) ->
            c == null
        }.map { (_, remainder) ->
            remainder
        }.map {
            lineScore(it)
        }.sorted()
        return scores[scores.size / 2]
    }

    fun findInvalidChar(chars: List<Char>) : Pair<Char?, List<Char>> {
        val stack = mutableListOf<Char>()
        chars.forEach { c ->
            when (c) {
                '(' -> stack.add(c)
                ')' -> {
                    if (stack.last() == '(') {
                        stack.removeLast()
                    } else {
                        return c to emptyList()
                    }
                }
                '[' -> stack.add(c)
                ']' -> {
                    if (stack.last() == '[') {
                        stack.removeLast()
                    } else {
                        return c to emptyList()
                    }
                }
                '{' -> stack.add(c)
                '}' -> {
                    if (stack.last() == '{') {
                        stack.removeLast()
                    } else {
                        return c to emptyList()
                    }
                }
                '<' -> stack.add(c)
                '>' -> {
                    if (stack.last() == '<') {
                        stack.removeLast()
                    } else {
                        return c to emptyList()
                    }
                }
                else -> error("invalid character $c")
            }
        }
        stack.reverse()
        return null to stack
    }

    fun lineScore(s: String): Long {
        return lineScore(s.toCharArray().toList())
    }

    fun lineScore(s: List<Char>): Long {
        return s.fold(0) { acc, c ->
            acc * 5L + when(c) {
                '(' -> 1L
                '[' -> 2L
                '{' -> 3L
                '<' -> 4L
                else -> error("invalid character $c")
            }
        }
    }
}
