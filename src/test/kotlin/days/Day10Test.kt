package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day10Test {
    val underTest = Day10()

    @Test
    fun example1() {
       val score = underTest.runPartOne("""
           [({(<(())[]>[[{[]{<()<>>
           [(()[<>])]({[<{<<[]>>(
           {([(<{}[<>[]}>{[]{[(<()>
           (((({<>}<{<{<>}{[]{[]{}
           [[<[([]))<([[{}[[()]]]
           [{[{({}]{}}([{[{{{}}([]
           {<[[]]>}<{[{[{[]{()[[[]
           [<(<(<(<{}))><([]([]()
           <{([([[(<>()){}]>(<<{{
           <{([{{}}[<[[[<>{}]]]>[]]
       """.trimIndent().lines())
        assertThat(score, `is`(26397))
    }

    @Test
    fun singleLine() {
        val c = underTest.findInvalidChar("{([(<{}[<>[]}>{[]{[(<()>".toCharArray().toList())
        assertThat(c, `is`('}'))
    }

//    @Test
//    fun simple() {
//        val c = underTest.findInvalidChar("{}".toCharArray().toList())
//        assertThat(c, `is`(null))
//    }

    @Test
    fun simpleBad() {
        val c = underTest.findInvalidChar("{(}".toCharArray().toList())
        assertThat(c, `is`('}'))
    }

    @Test
    fun lineScore1() {
        val score = underTest.lineScore("}}]])})]")
        assertThat(score, `is`(288957))
    }
}
