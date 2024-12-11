package io.nozemi.aoc.extensions

/**
 * Gets the digit count of the Int.
 * Example: 1234 -> 4
 */
val Int.length get() = this.toString().length