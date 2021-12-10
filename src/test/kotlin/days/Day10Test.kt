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
        assertThat(c, `is`('}' to emptyList()))
    }

//    @Test
//    fun simple() {
//        val c = underTest.findInvalidChar("{}".toCharArray().toList())
//        assertThat(c, `is`(null))
//    }

    @Test
    fun simpleBad() {
        val c = underTest.findInvalidChar("{(}".toCharArray().toList())
        assertThat(c, `is`('}' to emptyList()))
    }

    @Test
    fun lineScore1() {
        val score = underTest.lineScore(inverted("}}]])})]"))
        assertThat(score, `is`(288957))
    }

    @Test
    fun lineScore2() {
        val score = underTest.lineScore(inverted(")}>]})"))
        assertThat(score, `is`(5566))
    }

    @Test
    fun lineScore3() {
        val score = underTest.lineScore(inverted("}}>}>))))"))
        assertThat(score, `is`(1480781))
    }

    fun inverted(s: String) : String {
        return s.map {
            when (it) {
                '}' -> '{'
                ']' -> '['
                ')' -> '('
                '>' -> '<'
                else -> error("Unknown char $it")
            }
        }.joinToString("")
    }

    @Test
    fun example2() {
        val score = underTest.runPartTwo("""
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
        assertThat(score, `is`(288957))
    }

    @Test
    fun run2() {
        val score = underTest.partTwo()
        assertThat(score, `is`(0))
    }
}
