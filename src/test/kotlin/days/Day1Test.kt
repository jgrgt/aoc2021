package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day1Test {

    private val dayOne = Day1()

    @Test
    fun emptyListGives0() {
        assertThat(DepthIncreaseCounter.count(emptyList()), `is`(0))
    }

    @Test
    fun listOf1ItemGives0() {
        assertThat(DepthIncreaseCounter.count(listOf(42)), `is`(0))
    }

    @ParameterizedTest
    @CsvSource("100,200,1", "200,100,0", "100,101,1", "100,100,0")
    fun listOf2ItemsGives(item1: Int, item2: Int, expected: Int) {
        assertThat(DepthIncreaseCounter.count(listOf(item1, item2)), `is`(expected))
    }

    @Test
    fun demoInputGives7() {
        val inputs = listOf(
            199,
            200,
            208,
            210,
            200,
            207,
            240,
            269,
            260,
            263,
        )
        assertThat(DepthIncreaseCounter.count(inputs), `is`(7))
    }
//    fun testPartOne() {
//        assertThat(dayOne.partOne(), `is`("THIS IS"))
//    }
//
//    @Test
//    fun testPartTwo() {
//        val partTwo = dayOne.partTwo()
//        assertThat(partTwo, notNullValue())
//        assertThat(partTwo, instanceOf(String::class.java))
//        assertThat(partTwo, `is`("FILE"))
//    }
}
