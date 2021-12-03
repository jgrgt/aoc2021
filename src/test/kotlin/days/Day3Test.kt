package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day3Test {
    val underTest = Day3()

    @Test
    fun singleBitWorks() {
        val (gamma, epsilon) = underTest.buildGammaAndEpsilon(listOf(3), 3)

        assertThat("Gamma is one", gamma, `is`(1))
        assertThat("Epsilon is 0", epsilon, `is`(0))
    }

    @Test
    fun twoBitsWork() {
        val (gamma, epsilon) = underTest.buildGammaAndEpsilon(listOf(3, 0), 3)

        assertThat("Gamma is two", gamma, `is`(2))
        assertThat("Epsilon is one", epsilon, `is`(1))
    }

    @Test
    fun oxygenSingleBit() {
        val result = underTest.oxygen(
            listOf(
                "0",
                "0",
                "1"
            )
        )

        assertThat(result, `is`("0"))
    }

    @Test
    fun oxygenTwoBits() {
        val result = underTest.oxygen(
            listOf(
                "01",
                "00",
                "10"
            )
        )

        assertThat(result, `is`("01"))
    }

    @Test
    fun oxygenExample() {
        val result = underTest.oxygen(
            listOf(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010",
            )
        )

        assertThat(result, `is`("10111"))
    }

    @Test
    fun co2scrubberExample() {
        val result = underTest.co2scrubber(
            listOf(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010",
            )
        )

        assertThat(result, `is`("01010"))
    }

    @Test
    fun p2Example() {
        val result = underTest.p2(
            listOf(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010",
            )
        )

        assertThat(result, `is`(230))
    }
}
