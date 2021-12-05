package io.nozemi.aoc.puzzles.year2021.day05.impl

import java.lang.Integer.max
import java.lang.Integer.min

class LineSegment(val start: Coordinate, val end: Coordinate) {
    val coordinates: List<Coordinate>

    init {
        coordinates = allCoordinates()
    }

    private fun allCoordinates(): List<Coordinate> {
        if (start.x == end.x) return IntRange(min(start.y, end.y), max(start.y, end.y)).map { Coordinate(x = start.x, y = it) }
        if (start.y == end.y) return IntRange(min(start.x, end.x), max(start.x, end.x)).map { Coordinate(x = it, y = start.y) }
        return emptyList()
    }

    override fun toString(): String {
        return "Line (start=$start, end=$end)"
    }

    companion object {

        fun parseStartToEnd(raw: String): LineSegment {
            val positions = raw.split("->")

            return LineSegment(
                start = Coordinate.parsePosition(positions[0]),
                end = Coordinate.parsePosition(positions[1])
            )
        }
    }
}