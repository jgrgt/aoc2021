package days

import days.Day18.Leaf
import days.Day18.NumberPair
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day18Test {
    @Test
    fun `it can parse a literal`() {
        val n = Day18.SnailFishNumber.parse("4")
        assertThat(n, `is`(Leaf(4)))
    }

    @Test
    fun `it can parse a pair`() {
        val n = Day18.SnailFishNumber.parse("[4,3]")
        assertThat(n, `is`(NumberPair(Leaf(4), Leaf(3))))
    }

    @Test
    fun `it can parse a nested pair`() {
        val n = Day18.SnailFishNumber.parse("[[4,1],3]")
        assertThat(n, `is`(NumberPair(NumberPair(Leaf(4), Leaf(1)), Leaf(3))))
    }

    @Test
    fun `it can parse a double nested pair`() {
        val n = Day18.SnailFishNumber.parse("[[4,[1,8]],[6,3]]")
        assertThat(n, `is`(NumberPair(NumberPair(Leaf(4), NumberPair(Leaf(1), Leaf(8))), NumberPair(Leaf(6),Leaf(3)))))
    }
}
