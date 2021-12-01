package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.hamcrest.core.IsNull.notNullValue
import org.junit.jupiter.api.Test

class Day1Test {

    private val dayOne = Day1()
    private val counter = DepthIncreaseCounter()

    @Test
    fun emptyListGives0() {
        assertThat(counter.count(emptyList()), `is`(0))
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
