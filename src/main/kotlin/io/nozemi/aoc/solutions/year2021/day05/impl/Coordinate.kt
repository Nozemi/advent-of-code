package io.nozemi.aoc.solutions.year2021.day05.impl

data class Coordinate(val x: Int, val y: Int) {

    companion object {

        operator fun get(raw: String): Coordinate
            = with(raw.trim().split(",")) { Coordinate(this[0].toInt(), this[1].toInt()) }
    }
}