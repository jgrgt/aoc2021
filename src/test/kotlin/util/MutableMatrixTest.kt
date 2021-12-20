package util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class MutableMatrixTest {
    @Test
    fun createFromSingleDigits() {
        val m = MutableMatrix.fromSingleDigits(
            """123
            456
            789
        """.multiLineInputLines(),
            creator = { s -> s.digitToInt() }
        )

        assertThat(
            m, `is`(
                MutableMatrix(
                    mutableListOf(
                        mutableListOf(1, 2, 3),
                        mutableListOf(4, 5, 6),
                        mutableListOf(7, 8, 9),
                    )
                )
            )
        )
    }

    @Test
    fun createFromCommaLines() {
        val m = MutableMatrix.fromCommaLines(
            """one, two, three
            four, five, six
            seven, eight, nine
        """.multiLineInputLines(),
            creator = { s -> s }
        )

        assertThat(
            m, `is`(
                MutableMatrix(
                    mutableListOf(
                        mutableListOf("one", "two", "three"),
                        mutableListOf("four", "five", "six"),
                        mutableListOf("seven", "eight", "nine"),
                    )
                )
            )
        )
    }

    @Test
    fun `it can window the center of a 3x3`() {
        val m = MutableMatrix(
            mutableListOf(
                mutableListOf(1, 2, 3),
                mutableListOf(4, 5, 6),
                mutableListOf(7, 8, 9),
            )
        )

        var calls = 0
        m.window(Point(1, 1)) {
            calls += 1
            assertThat(
                it, `is`(
                    MutableMatrix(
                        mutableListOf(
                            mutableListOf(1, 2, 3),
                            mutableListOf(4, 5, 6),
                            mutableListOf(7, 8, 9),
                        )
                    )
                )
            )
        }
        assertThat(calls, `is`(1))
    }

    @Test
    fun `it can clone safely`() {
        val m = MutableMatrix(
            mutableListOf(
                mutableListOf(1, 2),
                mutableListOf(3, 4),
            )
        )
        val cloned = m.clone()

        m.set(Point(0, 0), 5)
        m.set(Point(0, 1), 6)
        m.set(Point(1, 0), 7)
        m.set(Point(1, 1), 8)

        assertThat(
            m, `is`(
                MutableMatrix(
                    mutableListOf(
                        mutableListOf(5, 6),
                        mutableListOf(7, 8),
                    )
                )
            )
        )

        assertThat(
            cloned, `is`(
                MutableMatrix(
                    mutableListOf(
                        mutableListOf(1, 2),
                        mutableListOf(3, 4),
                    )
                )
            )
        )
    }
}
