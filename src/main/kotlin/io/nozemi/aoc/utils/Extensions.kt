package io.nozemi.aoc.utils

import java.nio.file.Path

fun Array<IntArray>.transposeMatrix() = Array(this[0].size) { i -> IntArray(this.size) { j -> this[j][i] } }

fun String.countChar(char: Char): Int {
    var count = 0

    for (i in 0 until this.length) {
        if (this.toCharArray()[i] == char) count++
    }

    return count
}

fun String.toPath(): Path {
    return Path.of(this)
}

fun <T> MutableList<T>.addIfNotExists(value: T) {
    if (value !in this) {
        this.add(value)
    }
}

fun MutableList<Long>.median(): Long {
    this.sort()
    val median: Long
    val totalElements = this.size

    median = if (totalElements % 2 == 0) {
        val sumOfMiddleElements = this[totalElements / 2] + this[totalElements / 2 - 1]
        sumOfMiddleElements
    } else {
        this[totalElements / 2]
    }

    return median
}

operator fun MutableMap<Int, String>?.get(key: Int): String? {
    if (this == null) return null
    return this[key]
}

operator fun Array<Set<Char>>.set(index: Int, value: String) {
    this[index] = value
}