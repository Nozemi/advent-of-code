package io.nozemi.aoc.types.datastructures.distjoint

import io.nozemi.aoc.extensions.arrayDequeOf

/**
 * Used Graham's [advent-2024](https://github.com/grahamedgecombe/advent-2024/blob/main/src/main/kotlin/com/grahamedgecombe/advent2024/util/ForestDisjointSet.kt) as reference.
 *
 * @author Graham Edgecombe
 */
class ForestDisjointSet<T> : IDisjointSet<T> {
    private val nodes = mutableMapOf<T, Node<T>>()

    override val elements: Int
        get() = nodes.size

    override var partitions: Int = 0
        private set

    override fun add(x: T): IDisjointSet.Partition<T> {
        val node = findNode(x)
        if (node != null)
            return node

        partitions++

        val newNode = Node(x)
        nodes[x] = newNode
        return newNode
    }

    override fun get(x: T): IDisjointSet.Partition<T>? {
        return findNode(x)
    }

    override fun union(x: IDisjointSet.Partition<T>, y: IDisjointSet.Partition<T>) {
        require(x is Node<T>)
        require(y is Node<T>)

        val xRoot = x.find()
        val yRoot = y.find()

        if (xRoot == yRoot)
            return

        when {
            xRoot.rank < yRoot.rank -> xRoot.parent = yRoot
            xRoot.rank > yRoot.rank -> yRoot.parent = xRoot
            else -> {
                yRoot.parent = xRoot
                xRoot.rank++
            }
        }

        partitions--
    }

    override fun iterator(): Iterator<IDisjointSet.Partition<T>> {
        return nodes.values.iterator()
    }

    private fun findNode(x: T): Node<T>? {
        val node = nodes[x]
            ?: return null

        return node.find()
    }

    private class Node<T>(val value: T) : IDisjointSet.Partition<T> {
        val children = mutableListOf<Node<T>>()

        private var _parent = this
        var parent
            get() = _parent
            set(parent) {
                _parent = parent
                _parent.children.add(this)
            }
        var rank = 0

        fun find(): Node<T> {
            if (parent !== this) _parent.find()

            return parent
        }

        override fun iterator() = NodeIterator(find())

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Node<*>) return false

            return find() === other.find()
        }

        override fun hashCode() = find().value.hashCode()
        override fun toString() = find().value.toString()
    }

    private class NodeIterator<T>(root: Node<T>) : Iterator<T> {
        private val queue = arrayDequeOf(root)

        override fun hasNext() = queue.isNotEmpty()

        override fun next(): T {
            val node = queue.removeFirstOrNull() ?: throw NoSuchElementException()

            queue.addAll(node.children)

            return node.value
        }
    }
}