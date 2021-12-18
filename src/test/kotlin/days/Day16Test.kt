package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day16Test {
    @Test
    fun `it can parse the simple example`() {
        val packet = Day16.Packet.parse("D2FE28")
        assertThat(packet, `is`(Day16.Packet(6, Day16.PacketType.LITERAL, 2021)))
    }
}
