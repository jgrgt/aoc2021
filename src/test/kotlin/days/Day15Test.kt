package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import util.multiLineInputLines

class Day15Test {
    val underTest = Day15()

    @Test
    fun `the example works`() {
        val score = underTest.runPartOne(
            """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581""".multiLineInputLines()
        )
        assertThat(score, `is`(40))
    }

    @Test
    fun `the first part of the example works`() {
        val score = underTest.runPartOne(
            """116
               138
               213""".multiLineInputLines()
        )
        assertThat(score, `is`(7))
    }

    @Test
    fun `starting simple`() {
        val score = underTest.runPartOne(
            """
            12
            13""".multiLineInputLines()
        )
        assertThat(score, `is`(4))
    }

    @Test
    fun `slightly bigger`() {
        val score = underTest.runPartOne(
            """
            131
            121
            224""".multiLineInputLines()
        )
        assertThat(score, `is`(8))
    }
}
