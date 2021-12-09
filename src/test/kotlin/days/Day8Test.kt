package days

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class Day8Test {
    val underTest = Day8()

    @Test
    fun testExample() {
        val result = underTest.runPartOne(
            """be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce""".trimIndent().lines()
        )
        assertThat(result, `is`(26))
    }

    @Test
    fun occuranceExperiment() {
        val s = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab"
        val signals = s.split(" ")
        val occurences = MutableList(7) { 0 }
        signals.forEach { signal ->
            signal.chars().forEach { c ->
                val index = c - 'a'.code
                occurences[index] = occurences[index] + 1
            }
        }

        println(occurences)
    }

    @Test
    fun testDecode() {
        val chars = underTest.decode("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab")
        assertThat(chars, `is`(listOf('d', 'e', 'a', 'f', 'g', 'b', 'c')))
    }

    @Test
    fun testTranslate() {
        val mapping = listOf('d', 'e', 'a', 'f', 'g', 'b', 'c')
        assertThat(underTest.translate("cdfeb", mapping), `is`(5))
        assertThat(underTest.translate("fcadb", mapping), `is`(3))
    }
}
