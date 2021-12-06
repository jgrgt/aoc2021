package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day6Test {
    val underTest = Day6()

    @Test
    fun testExample18Days() {
        val result = underTest.p1(listOf("3,4,3,1,2"), 18)
        assertThat(result, `is`(26))
    }

    @Test
    fun testExample80Days() {
        val result = underTest.p1(listOf("3,4,3,1,2"), 80)
        assertThat(result, `is`(5934))
    }

    @Test
    fun testExample256Days() {
        val result = underTest.p1(listOf("3,4,3,1,2"), 256)
        assertThat(result, `is`(26984457539))
    }

    @Test
    fun testNextDay() {
        val result = underTest.nextDay(listOf(3, 4, 3, 1, 2))
        val expectedList = listOf(2, 3, 2, 0, 1)
        assertThat(result, `is`(expectedList))
    }

    @Test
    fun testSimpleCreation() {
        val result = underTest.nextDay(listOf(0))
        val expectedList = listOf(6, 8)
        assertThat(result, `is`(expectedList))
    }

    @Test
    fun testMultipleCreations() {
        val result = underTest.nextDay(listOf(1, 0, 1, 0, 1))
        val expectedList = listOf(0, 6, 0, 6, 0, 8, 8)
        assertThat(result, `is`(expectedList))
    }
}
