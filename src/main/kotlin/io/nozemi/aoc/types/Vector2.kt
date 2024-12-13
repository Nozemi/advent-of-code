package io.nozemi.aoc.types

import kotlin.math.max
import kotlin.math.min

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun plus(direction: Direction) = plus(direction.value)

    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun minus(direction: Direction) = minus(direction.value)

    fun distanceFrom(other: Vector2) = Vector2(
        max(x, other.x) - min(x, other.x),
        max(y, other.y) - min(y, other.y)
    )

    override fun toString(): String {
        return "${this::class.simpleName}(x=$x, y=$y)"
    }
}