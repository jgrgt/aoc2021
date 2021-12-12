package util

data class Tree<T>(val root: Node<T>) {
    data class Node<T>(val value: T, val next: List<Node<T>>) {

    }
}


class MutableTree<T>(val root: MutableNode<T>, private val nodes: List<MutableNode<T>>) {
    fun getNode(value: T): MutableNode<T> {
        return nodes.find { it.value == value } ?: error("Did not find node for value $value")
    }

    data class MutableNode<T>(val value: T, val next: MutableList<MutableNode<T>>) {
        fun addNext(nextNodes: List<MutableNode<T>>) {
            val filtered = nextNodes.filter { !next.contains(it) }
            next.addAll(filtered)
        }
    }
}

