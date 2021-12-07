package io.nozemi.aoc.utils

fun Array<IntArray>.transposeMatrix() = Array(this[0].size) { i -> IntArray(this.size) { j -> this[j][i] } }

fun String.countChar(char: Char): Int {
    var count = 0

    for (i in 0 until this.length) {
        if (this.toCharArray()[i] == char) count++
    }

    return count
}

fun <T> MutableList<T>.addIfNotExists(value: T) {
    if (value !in this) this.add(value)
}

operator fun MutableMap<Int, String>?.get(key: Int): String? {
    if (this == null) return null
    return this[key]
}