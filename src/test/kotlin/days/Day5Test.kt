package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day5Test {
    val underTest = Day5()

    @Test
    fun testParseLines() {
        val s = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()
        val ls = s.lines()
        val lines = underTest.parseLines(ls)
        assertThat(
            lines, `is`(
                listOf(
                    Line(Day5Point(0, 9), Day5Point(5, 9)),
                    Line(Day5Point(9, 4), Day5Point(3, 4)),
                    Line(Day5Point(2, 2), Day5Point(2, 1)),
                    Line(Day5Point(7, 0), Day5Point(7, 4)),
                    Line(Day5Point(0, 9), Day5Point(2, 9)),
                    Line(Day5Point(3, 4), Day5Point(1, 4)),
                )
            )
        )
    }

    @Test
    fun testMaxPoint() {
        val maxPoint = underTest.findMaxPoint(listOf(Line(Day5Point(1, 4), Day5Point(2, 1))))
        assertThat(maxPoint, `is`(Day5Point(2, 4)))
    }

    @Test
    fun testExample() {
        val s = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()
        val ls = s.lines()
        val result = underTest.p1(ls)
        assertThat(result, `is`(5))
    }


    @Test
    fun testExample2() {
        val s = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()
        val ls = s.lines()
        val result = underTest.p2(ls)
        assertThat(result, `is`(12))
    }

    @Test
    fun testDiagonal() {
        val line = Line(Day5Point(4, 1), Day5Point(1, 4))
        val points = line.points()
        assertThat(points.size, `is`(4))
        assertThat(
            points.toSet(), `is`(
                setOf(
                    Day5Point(1, 4),
                    Day5Point(2, 3),
                    Day5Point(3, 2),
                    Day5Point(4, 1),
                )
            )
        )
    }
}
