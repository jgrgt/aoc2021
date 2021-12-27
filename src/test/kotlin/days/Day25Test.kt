package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import util.multiLineInputLines

internal class Day25Test {
    val underTest = Day25()

    @Test
    fun `example `() {
        val result = underTest.runPartOne(
            """v...>>.vv>
.vv>>.vv..
>>.>v>...v
>>v>>.>.v.
v>v.vv.v..
>.>>..v...
.vv..>.>v.
v.v..>>v.v
....v..v.>""".multiLineInputLines()
        )
        assertThat(result, `is`(58))
    }
}
