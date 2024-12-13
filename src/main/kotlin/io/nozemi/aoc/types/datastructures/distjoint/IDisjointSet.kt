package io.nozemi.aoc.types.datastructures.distjoint

interface IDisjointSet<T> : Iterable<IDisjointSet.Partition<T>> {
    interface Partition<T> : Iterable<T>

    val elements: Int
    val partitions: Int

    fun add(x: T): Partition<T>
    fun addAll(vararg x: T) = x.forEach { add(it) }
    fun addAll(x: List<T>) = x.forEach { add(it) }
    operator fun get(x: T): Partition<T>?
    fun union(x: Partition<T>, y: Partition<T>)
}