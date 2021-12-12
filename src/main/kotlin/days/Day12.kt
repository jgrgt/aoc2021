package days

import util.MutableTree
import util.MutableTree.MutableNode

class Day12 : Day(12) {
    companion object {
        /**
         * Avoid 'lower case' nodes being used twice!
         */
        fun paths(current: List<MutableNode<String>>, end: MutableNode<String>): List<List<MutableNode<String>>> {
            val candidates = current.last().next
            // remove invalid candidates
            val lowerCaseCurrent = current.filter { it.value[0].isLowerCase() }
            val filteredCandidates = candidates.filter { c ->
                !lowerCaseCurrent.contains(c)
            }
            val x = filteredCandidates.map { next ->
                val newCurrent = current + listOf(next)
                if (next == end) {
                    listOf(newCurrent)
                } else {
                    paths(newCurrent, end)
                }
            }
            return x.flatten()
        }

        fun buildTree(lines: List<String>): MutableTree<String> {
            val sep = "-"
            val grouped = lines.map {
                val (start, end) = it.split(sep)
                start to end
            }.groupBy({ it.first }, { it.second })
            val nodes = (grouped.keys + grouped.values.flatten()).toSet().map {
                MutableNode(it, mutableListOf())
            }.associateBy { n ->
                n.value
            }.toMutableMap()
            grouped.forEach { (start, ends) ->
                val node = nodes[start]!!
                val nextNodes = ends.map { end ->
                    nodes[end]!!
                }
                nextNodes.forEach {
                    it.addNext(listOf(node))
                }
                node.addNext(nextNodes)
            }
            val start = nodes["start"]!!
            return MutableTree(start, nodes.values.toList())
        }
    }

    override fun runPartOne(lines: List<String>): Any {
        val tree = buildTree(lines)
        val start = tree.root
        val end = tree.getNode("end")
        val endPaths = paths(listOf(start), end)
        return endPaths.size
    }
}
