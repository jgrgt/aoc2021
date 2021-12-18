package days

import days.Day18.Leaf
import days.Day18.NumberPair
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import util.multiLineInputLines

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
        assertThat(n, `is`(NumberPair(NumberPair(Leaf(4), NumberPair(Leaf(1), Leaf(8))), NumberPair(Leaf(6), Leaf(3)))))
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

    @Test
    fun `example 2`() {
        val result = Day18().runPartTwo(
            """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.multiLineInputLines()
        )
        assertThat(result, `is`(3993))
    }

    @Test
    fun `test generate pairs`() {
        val lines = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.multiLineInputLines()
        val n1 = Day18.SnailFishNumber.parse("[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]")
        val n2 = Day18.SnailFishNumber.parse("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]")
        val numbers = lines.map { Day18.SnailFishNumber.parse(it) }
        val result = Day18().generateAllPairs(numbers)
        assertThat(result.toList(), hasItem(n1 to n2))
    }

    @Test
    fun `subtest example 2`() {
        val n1 = Day18.SnailFishNumber.parse("[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]")
        val n2 = Day18.SnailFishNumber.parse("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]")
        val reduced = n1.add(n2).reduce()
        assertThat(
            reduced,
            `is`(Day18.SnailFishNumber.parse("[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]"))
        )
    }

    @Test
    fun `subtest example 2 magnitude`() {
        val n = Day18.SnailFishNumber.parse("[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]")
        assertThat(n.magnitude(), `is`(3993))
    }
}
