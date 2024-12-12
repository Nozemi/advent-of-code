package io.nozemi.aoc.types.matrix

import io.nozemi.aoc.types.Coordinates

data class MatrixCell<T>(val x: Int, val y: Int, val value: T) {
    val coordinates: Coordinates get() = Coordinates(x, y)
}