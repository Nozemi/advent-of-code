package io.nozemi.aoc.extensions

fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque<T>().also {
    it.addAll(elements)
}