package io.nozemi.aoc.extensions

/**
 * Gets the left half of the integer.
 * Example: 1234 -> 12
 */
val Int.leftHalf get() = this.toString().let { it.substring(0, it.length / 2) }.toInt()

/**
 * Gets the right half of the integer.
 * Example: 1234 -> 34
 */
val Int.rightHalf get() = this.toString().let { it.substring(it.length / 2) }.toInt()

/**
 * Gets the digit count of the Int.
 * Example: 1234 -> 4
 */
val Int.length get() = this.toString().length