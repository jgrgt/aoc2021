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
}
