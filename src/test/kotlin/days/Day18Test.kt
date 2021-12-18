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

    @Test
    fun `it can explode ex 1`() {
        val n = Day18.SnailFishNumber.parse("[[[[[9,8],1],2],3],4]").findAndDoExplode(0).newNode
        assertThat(n, `is`(Day18.SnailFishNumber.parse("[[[[0,9],2],3],4]")))
    }

    @Test
    fun `it can explode ex 2`() {
        val n = Day18.SnailFishNumber.parse("[7,[6,[5,[4,[3,2]]]]]").findAndDoExplode(0).newNode
        assertThat(n, `is`(Day18.SnailFishNumber.parse("[7,[6,[5,[7,0]]]]")))
    }

    @Test
    fun `it can explode ex 3`() {
        val n = Day18.SnailFishNumber.parse("[[6,[5,[4,[3,2]]]],1]").findAndDoExplode(0).newNode
        assertThat(n, `is`(Day18.SnailFishNumber.parse("[[6,[5,[7,0]]],3]")))
    }

    @Test
    fun `it can explode ex 4`() {
        val n = Day18.SnailFishNumber.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").findAndDoExplode(0).newNode
        assertThat(n, `is`(Day18.SnailFishNumber.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")))
    }
//
//    @Test
//    fun `it can explode ex 5`() {
//        val n = Day18.SnailFishNumber.parse("").reduce(0)
//        assertThat(n, `is`(Day18.SnailFishNumber.parse("")))
//    }

    @Test
    fun `example 1`() {
        val result = Day18.SnailFishNumber.parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]").reduce()
        assertThat(result, `is`(Day18.SnailFishNumber.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")))
    }
}
