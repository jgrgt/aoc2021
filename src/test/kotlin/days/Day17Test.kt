package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import util.Point

internal class Day17Test {
    val underTest = Day17()

    @Test
    fun `it can do the example`() {
        // x=20..30, y=-10..-5
        val vector = underTest.findHighestLaunch(20, 30, -10, -5)
        assertThat(vector, `is`(Point(6, 9)))
    }

    @Test
    fun `it can do the example for part 2`() {
        // x=20..30, y=-10..-5
        val vectors = underTest.findGoodLaunches(20, 30, -10, -5)
        assertThat(vectors.size, `is`(112))
    }
}
