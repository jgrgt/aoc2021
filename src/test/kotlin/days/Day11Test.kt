package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day11Test {
    val underTest = Day11()
    val example = """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526""".trimIndent().lines()
    val parsedExample = underTest.parse(example)

    val miniExample = """11111
19991
19191
19991
11111""".trimIndent().lines()
    val parsedMiniExample = underTest.parse(miniExample)

    @Test
    fun testExample1With10Steps() {
        val octopusses = Day11.Octopusses(parsedExample)
        val flashes = (0..9).sumOf {
            octopusses.flash()
        }
        assertThat(flashes, `is`(204))
    }

    @Test
    fun testExample1With2Steps() {
        val octopusses = Day11.Octopusses(parsedExample)
        val flashes = (0..1).sumOf {
            octopusses.flash()
        }
        assertThat(flashes, `is`(35))
    }

    @Test
    fun miniTest1() {
        val octopusses = Day11.Octopusses(parsedMiniExample)
        octopusses.flash()
        assertThat(
            octopusses.levels, `is`(
                underTest.parse(
                    """
            34543
            40004
            50005
            40004
            34543
        """.trimIndent().lines()
                )
            )
        )
    }

    @Test
    fun miniTest2() {
        val octopusses = Day11.Octopusses(parsedMiniExample)
        octopusses.flash()
        octopusses.flash()
        assertThat(
            octopusses.levels, `is`(
                underTest.parse(
                    """
                        45654
                        51115
                        61116
                        51115
                        45654
        """.trimIndent().lines()
                )
            )
        )
    }

    @Test
    fun subFlash() {
        Day11.Octopusses(listOf(listOf(1)))


    }
}
