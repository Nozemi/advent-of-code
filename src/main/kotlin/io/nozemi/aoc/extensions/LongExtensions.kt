package io.nozemi.aoc.extensions

/**
 * Gets the digit count of the Int.
 * Example: 1234 -> 4
 */
val Long.length get() = this.toString().length