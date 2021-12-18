package days

class Day18 : Day(18) {

    sealed class SnailFishNumber {
        companion object {
            fun parse(s: String): SnailFishNumber {
                val (n, leftOver) = when (s[0]) {
                    '[' -> NumberPair.parse(s.substring(1))
                    else -> Leaf(s[0].digitToInt()) to s.substring(1)
                }
                check(leftOver.isEmpty()) { "Leftover string should be empty but was ${leftOver}" }
                return n
            }
        }
    }

    data class NumberPair(val left: SnailFishNumber, val right: SnailFishNumber) : SnailFishNumber() {
        companion object {
            fun parse(s: String): Pair<SnailFishNumber, String> {
                val (left, leftOver) = when (s[0]) {
                    '[' -> parse(s.substring(1))
                    else -> Leaf(s[0].digitToInt()) to s.substring(1)
                }
                check(leftOver[0] == ',') { "Expected a , at the beginning of $leftOver"}
                val (right, other) = when(leftOver[1]) {
                    '[' -> parse(leftOver.substring(2))
                    else -> Leaf(leftOver[1].digitToInt()) to leftOver.substring(2)
                }
                return NumberPair(left, right) to other.substring(1) // skip ]
            }
        }
    }

    data class Leaf(val n: Int) : SnailFishNumber() {
        companion object {
//            fun parse(): Pair<SnailFishNumber, String> {
//                retrun
//            }
        }
    }
}
