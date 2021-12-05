package io.nozemi.aoc.puzzles

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

@Deprecated("This will be removed, as it's not needed.", replaceWith = ReplaceWith(">"))
infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): Boolean =
    if (this != null && other != null) this > other else false

@Deprecated("This will be removed, as it's not needed.", replaceWith = ReplaceWith(">="))
infix fun <T : Comparable<T>> T?.isGreaterThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this >= other else false

@Deprecated("This will be removed, as it's not needed.", replaceWith = ReplaceWith("<"))
infix fun <T : Comparable<T>> T?.isLessThan(other: T?): Boolean =
    if (this != null && other != null) this < other else false

@Deprecated("This will be removed, as it's not needed.", replaceWith = ReplaceWith("<="))
infix fun <T : Comparable<T>> T?.isLessThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this <= other else false