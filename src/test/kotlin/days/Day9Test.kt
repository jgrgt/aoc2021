package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day9Test {
    val underTest = Day9()

    @Test
    fun example1() {
        val risk = underTest.runPartOne(
            """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().lines()
        )
        assertThat(risk, `is`(15))
    }
}
