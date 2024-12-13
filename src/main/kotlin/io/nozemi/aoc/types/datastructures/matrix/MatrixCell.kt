package io.nozemi.aoc.types.datastructures.matrix

import io.nozemi.aoc.types.Vector2

data class MatrixCell<T>(val x: Int, val y: Int, val value: T?) {
    constructor(coordinates: Vector2, value: T?) : this(coordinates.x, coordinates.y, value)

    val coordinates: Vector2 get() = Vector2(x, y)
}