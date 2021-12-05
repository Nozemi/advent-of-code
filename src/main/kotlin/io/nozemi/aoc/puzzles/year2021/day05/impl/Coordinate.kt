package io.nozemi.aoc.puzzles.year2021.day05.impl

class Coordinate(val x: Int, val y: Int) {

    override fun toString(): String {
        return "Position (x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinate) return false
        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    companion object {

        fun parsePosition(raw: String): Coordinate {
            val coords = raw.trim().split(",")
            return Coordinate(coords[0].toInt(), coords[1].toInt())
        }
    }
}