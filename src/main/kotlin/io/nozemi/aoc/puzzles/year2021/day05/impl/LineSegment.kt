package io.nozemi.aoc.puzzles.year2021.day05.impl

import com.github.michaelbull.logging.InlineLogger
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

private val logger = InlineLogger()

class LineSegment(val first: Coordinate, val last: Coordinate, val considerDiagonals: Boolean = false) {
    val coordinates: List<Coordinate>

    init {
        coordinates = allCoordinates()
    }

    private fun allCoordinates(): List<Coordinate> {
        if (first.x == last.x) return IntRange(min(first.y, last.y), max(first.y, last.y)).map { Coordinate(x = first.x, y = it) }
        if (first.y == last.y) return IntRange(min(first.x, last.x), max(first.x, last.x)).map { Coordinate(x = it, y = first.y) }

        /*
         * Checks if the current segment line is 45 degrees.
         * bigX-smallX == bigY-smallY when this is true
         */
        if (abs(first.x - last.x) == abs(first.y - last.y) && considerDiagonals) {
            val stepsNeeded = abs(first.x - last.x)
            val direction = Direction.getDirection(first, last)
            val steps = mutableListOf(first)
            var currentCoordinate = first
            for (i in 0 until stepsNeeded) {
                currentCoordinate = when (direction) {
                    Direction.UP_LEFT -> currentCoordinate.addX(-1).addY(-1)
                    Direction.UP_RIGHT -> currentCoordinate.addX(1).addY(-1)
                    Direction.DOWN_LEFT -> currentCoordinate.addX(-1).addY(1)
                    Direction.DOWN_RIGHT -> currentCoordinate.addX(1).addY(1)
                    else -> currentCoordinate
                }
                steps.add(currentCoordinate)
            }
            if (steps.last() == last) return steps
        }

        return emptyList()
    }

    override fun toString(): String {
        return "Line (start=$first, end=$last)"
    }

    enum class Direction {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT;

        companion object {

            fun getDirection(first: Coordinate, last: Coordinate): Direction? {
                if (first.x > last.x && first.y > last.y) return UP_LEFT
                if (first.x > last.x && first.y < last.y) return DOWN_LEFT
                if (first.x < last.x && first.y > last.y) return UP_RIGHT
                if (first.x < last.x && first.y < last.y) return DOWN_RIGHT
                return null
            }
        }
    }

    companion object {

        fun parseStartToEnd(raw: String, considerDiagonals: Boolean = false): LineSegment {
            val positions = raw.split("->")

            return LineSegment(
                first = Coordinate.parsePosition(positions[0]),
                last = Coordinate.parsePosition(positions[1]),
                considerDiagonals
            )
        }
    }
}