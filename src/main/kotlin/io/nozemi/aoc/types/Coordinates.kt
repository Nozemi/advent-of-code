package io.nozemi.aoc.types

import kotlin.math.max
import kotlin.math.min

data class Coordinates(
    private var xPos: Int,
    private var yPos: Int
) {
    val x get() = xPos
    val y get() = yPos

    operator fun plus(other: Coordinates) = Coordinates(x + other.x, y + other.y)
    operator fun minus(other: Coordinates) = Coordinates(x - other.x, y - other.y)

    operator fun plusAssign(other: Coordinates) {
        xPos += other.x
        yPos += other.y
    }

    operator fun minusAssign(other: Coordinates) {
        xPos -= other.x
        yPos -= other.y
    }

    fun moveBy(steps: Int, direction: Direction): Coordinates {
        when (direction) {
            Direction.NORTH -> yPos -= steps
            Direction.SOUTH -> yPos += steps
            Direction.WEST -> xPos -= steps
            Direction.EAST -> xPos += steps

            Direction.NORTH_WEST -> {
                yPos -= steps
                xPos -= steps
            }

            Direction.NORTH_EAST -> {
                yPos -= steps
                xPos += steps
            }

            Direction.SOUTH_WEST -> {
                yPos += steps
                xPos -= steps
            }

            Direction.SOUTH_EAST -> {
                yPos += steps
                xPos += steps
            }
        }

        return this
    }

    fun distanceTo(other: Coordinates) = Coordinates(
        max(x, other.x) - min(x, other.x),
        max(y, other.y) - min(y, other.y)
    )

    override fun toString(): String {
        return "Coordinate(x=${x + 1}, y=${y + 1})"
    }
}