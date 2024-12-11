package io.nozemi.aoc.extensions

/**
 * Gets the left half of the integer.
 * Example: 1234 -> 12
 */
val Long.leftHalf get() = this.toString().let { it.substring(0, it.length / 2) }.toLong()

/**
 * Gets the right half of the integer.
 * Example: 1234 -> 34
 */
val Long.rightHalf get() = this.toString().let { it.substring(it.length / 2) }.toLong()

/**
 * Gets the digit count of the Int.
 * Example: 1234 -> 4
 */
val Long.length get() = this.toString().length