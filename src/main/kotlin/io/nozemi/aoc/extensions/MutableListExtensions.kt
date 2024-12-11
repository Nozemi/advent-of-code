package io.nozemi.aoc.extensions

fun <T> MutableList<T>.insertBefore(index: Int, value: T) =
    this.subList(0, index).also { it.add(value) } + this.subList(index + 1, this.size)

fun <T> MutableList<T>.insertAfter(index: Int, value: T) =
    this.subList(0, index + 1).also { it.add(value) } + this.subList(index + 2, this.size)