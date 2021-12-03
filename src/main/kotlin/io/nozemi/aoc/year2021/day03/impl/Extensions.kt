package io.nozemi.aoc.year2021.day03.impl

import com.github.michaelbull.logging.InlineLogger

private val logger = InlineLogger()

fun Map<Int, Int>.toGammaRateBinary(): String {
    val builder = StringBuilder()
    this.forEach { builder.append(it.value) }
    return builder.toString()
}

fun Map<Int, Int>.toEpsilonRateBinary(): String {
    val builder = StringBuilder()
    this.forEach {
        when (it.value) {
            0 -> builder.append("1")
            1 -> builder.append("0")
        }
    }
    return builder.toString()
}

fun List<String>.countBitsInPosition(): Map<Int, Pair<Int, Int>> {
    val positionCounts: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()

    val binarySize = this.first().length
    for (i in 0 until binarySize) {
        var zeroBits = 0
        var oneBits = 0
        // We need to loop over all the bits on each line, and count them.
        this.forEach {
            if (it.toCharArray()[i] == '0') zeroBits++
            if (it.toCharArray()[i] == '1') oneBits++
        }
        positionCounts.put(i, Pair(zeroBits, oneBits))
    }

    return positionCounts
}

infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): Boolean =
    if (this != null && other != null) this > other else false

infix fun <T : Comparable<T>> T?.isGreaterThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this >= other else false

infix fun <T : Comparable<T>> T?.isLessThan(other: T?): Boolean =
    if (this != null && other != null) this < other else false

infix fun <T : Comparable<T>> T?.isLessThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this <= other else false