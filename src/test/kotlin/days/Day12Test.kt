package days

import days.Day12.Companion.buildTree
import days.Day12.Companion.paths
import days.Day12.Companion.paths2
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import util.multiLineInputLines

internal class Day12Test {
    val underTest = Day12()

    @Test
    fun testSimpleTreePaths() {
        val tree = buildTree(
            """
            start-A
            A-b
            b-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "A", "b", "end")
                    )
                )
            )
        )
    }

    @Test
    fun testSimpleMultiPath() {
        val tree = buildTree(
            """
            start-A
            start-B
            B-end
            A-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "A", "end"),
                        listOf("start", "B", "end"),
                    )
                )
            )
        )
    }

    @Test
    fun testSimpleMultiPathMultiSubPath() {
        val tree = buildTree(
            """
            start-a
            start-b
            b-c
            b-d
            c-end
            d-end
            a-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "a", "end"),
                        listOf("start", "b", "c", "end"),
                        listOf("start", "b", "d", "end"),
                    )
                )
            )
        )
    }

    @Test
    fun `it does not allow loops on lower cases`() {
        val tree = buildTree(
            """
            start-a
            start-b
            a-c
            b-c
            c-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "a", "c", "end"),
                        listOf("start", "b", "c", "end"),
                    )
                )
            )
        )
    }

    @Test
    fun `it does allow loops on upper cases`() {
        val tree = buildTree(
            """
            start-a
            start-B
            a-c
            B-c
            B-d
            c-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "a", "c", "end"),
                        listOf("start", "B", "c", "end"),
                        listOf("start", "B", "d", "B", "c", "end"),
                    )
                )
            )
        )
    }

    @Test
    fun `test example 1`() {
        val result = underTest.runPartOne(
            """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.multiLineInputLines()
        )
        assertThat(result, `is`(10))
    }

    @Test
    fun `test example 2`() {
        val result = underTest.runPartOne(
            """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
        """.multiLineInputLines()
        )
        assertThat(result, `is`(19))
    }

    @Test
    fun `test example 2 - 1`() {
        val result = underTest.runPartTwo(
            """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.multiLineInputLines()
        )
        assertThat(result, `is`(36))
    }

    @Test
    fun `test example 2 - 2`() {
        val result = underTest.runPartTwo(
            """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
        """.multiLineInputLines()
        )
        assertThat(result, `is`(103))
    }

    @Test
    fun `test simple lower 2-visits`() {
        val tree = buildTree(
            """
            start-A
            A-c
            A-end
        """.multiLineInputLines()
        )
        val paths = paths2(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "A", "c", "A", "c", "A", "end"),
                        listOf("start", "A", "c", "A", "end"),
                        listOf("start", "A", "end"),
                    )
                )
            )
        )
    }
}
