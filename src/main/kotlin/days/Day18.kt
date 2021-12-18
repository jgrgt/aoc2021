package days

class Day18 : Day(18) {
    override fun runPartOne(lines: List<String>): Any {
        val numbers = lines.map { SnailFishNumber.parse(it) }
        return numbers.reduce { acc, n ->
            val new = acc.add(n)
            new.reduce()
        }.magnitude()
    }

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

            val EXPLODED = Leaf(0)
        }

        fun add(other: SnailFishNumber): SnailFishNumber {
            return NumberPair(this, other)
        }

        abstract fun findAndDoExplode(level: Int): ExplodeResult
        fun reduce(): SnailFishNumber {
            val result = findAndDoExplode(0)
            if (result.newNode != null) {
                return result.newNode.reduce()
            }

            val splitResult = split()
            return if (splitResult == this) {
                this
            } else {
                splitResult.reduce()
            }
        }

        abstract fun split(): SnailFishNumber

        abstract fun addLeft(other: Int?): SnailFishNumber
        abstract fun addRight(other: Int?): SnailFishNumber
        abstract fun magnitude(): Int
    }

    data class ExplodeResult(val leftCarryOver: Int?, val newNode: SnailFishNumber?, val rightCarryOver: Int?)

    data class NumberPair(val left: SnailFishNumber, val right: SnailFishNumber) : SnailFishNumber() {
        companion object {
            fun parse(s: String): Pair<SnailFishNumber, String> {
                val (left, leftOver) = when (s[0]) {
                    '[' -> parse(s.substring(1))
                    else -> Leaf(s[0].digitToInt()) to s.substring(1)
                }
                check(leftOver[0] == ',') { "Expected a , at the beginning of $leftOver" }
                val (right, other) = when (leftOver[1]) {
                    '[' -> parse(leftOver.substring(2))
                    else -> Leaf(leftOver[1].digitToInt()) to leftOver.substring(2)
                }
                return NumberPair(left, right) to other.substring(1) // skip ]
            }
        }

        override fun addLeft(other: Int?): SnailFishNumber {
            if (other == null) {
                return this
            }

            return NumberPair(left, right.addLeft(other))
        }

        override fun addRight(other: Int?): SnailFishNumber {
            if (other == null) {
                return this
            }

            return NumberPair(left.addRight(other), right)
        }

        override fun magnitude(): Int {
            return 3 * left.magnitude() + 2 * right.magnitude()
        }

        override fun findAndDoExplode(level: Int): ExplodeResult {
            if (level == 4) {
                return explode()
            }
            val leftResult = left.findAndDoExplode(level + 1)

            if (leftResult.newNode != null) {
                return ExplodeResult(
                    leftResult.leftCarryOver,
                    NumberPair(leftResult.newNode, right.addRight(leftResult.rightCarryOver)),
                    null
                )
            }

            val rightResult = right.findAndDoExplode(level + 1)
            if (rightResult.newNode != null) {
                return ExplodeResult(
                    null,
                    NumberPair(left.addLeft(rightResult.leftCarryOver), rightResult.newNode),
                    rightResult.rightCarryOver
                )
            }

            return ExplodeResult(null, null, null)
        }

        override fun split(): SnailFishNumber {
            val leftResult = left.split()
            if (left != leftResult) {
                return NumberPair(leftResult, right)
            }

            val rightResult = right.split()
            if (right != rightResult) {
                return NumberPair(left, rightResult)
            }

            return this
        }

        private fun explode(): ExplodeResult {
            return ExplodeResult((left as Leaf).n, EXPLODED, (right as Leaf).n)
        }
    }

    data class Leaf(val n: Int) : SnailFishNumber() {
        companion object {
        }

        override fun addLeft(other: Int?): SnailFishNumber {
            if (other == null) {
                return this
            }
            return Leaf(n + other)
        }

        override fun addRight(other: Int?): SnailFishNumber {
            if (other == null) {
                return this
            }
            return Leaf(n + other)
        }

        override fun findAndDoExplode(level: Int): ExplodeResult {
            return ExplodeResult(null, null, null)
        }

        override fun split(): SnailFishNumber {
            if (n >= 10) {
                val left = n / 2
                val right = n - left
                return NumberPair(Leaf(left), Leaf(right))
            }

            return this
        }

        override fun magnitude(): Int {
            return n
        }
    }
}
