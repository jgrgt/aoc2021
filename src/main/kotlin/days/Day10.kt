package days

class Day10 : Day(10) {
    override fun runPartOne(lines: List<String>): Any {
        return lines.map { line ->
            findInvalidChar(line.toCharArray().toList())
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
        return lines.map { line ->
            findInvalidChar(line.toCharArray().toList())
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

    fun findInvalidChar(chars: List<Char>) : Char? {
        val stack = mutableListOf<Char>()
//        var pars = 0
//        var sqbr = 0
//        var curl = 0
//        var gtlt = 0
        chars.forEach { c ->
            when (c) {
                '(' -> stack.add(c)
                ')' -> {
                    if (stack.last() == '(') {
                        stack.removeLast()
                    } else {
                        return c
                    }
//                    if (pars > 0) {
//                        pars -= 1
//                    } else {
//                        return c
//                    }
                }
                '[' -> stack.add(c)
                ']' -> {
                    if (stack.last() == '[') {
                        stack.removeLast()
                    } else {
                        return c
                    }
                }
                '{' -> stack.add(c)
                '}' -> {
                    if (stack.last() == '{') {
                        stack.removeLast()
                    } else {
                        return c
                    }
                }
                '<' -> stack.add(c)
                '>' -> {
                    if (stack.last() == '<') {
                        stack.removeLast()
                    } else {
                        return c
                    }
                }
                else -> error("invalid character $c")
            }
        }
        return null
    }

    fun lineScore(s: String): Int {
        return s.fold(0) { acc, c ->
            acc * 5 + when(c) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> error("invalid character $c")
            }
        }
    }
}
