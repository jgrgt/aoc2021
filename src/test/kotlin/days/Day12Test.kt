package days

import days.Day12.Companion.buildTree
import days.Day12.Companion.paths
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import util.multiLineInputLines

internal class Day12Test {
    @Test
    fun testSimpleTreePaths() {
        val tree = buildTree(
            """
            start-A
            A-B
            B-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "A", "B", "end")
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
            start-A
            start-B
            B-c
            B-d
            c-end
            d-end
            A-end
        """.multiLineInputLines()
        )
        val paths = paths(listOf(tree.root), tree.getNode("end")).map { path -> path.map { it.value } }
        assertThat(
            paths, `is`(
                equalTo(
                    listOf(
                        listOf("start", "A", "end"),
                        listOf("start", "B", "c", "end"),
                        listOf("start", "B", "d", "end"),
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
}
