package io.nozemi.aoc.solutions.year2021.day05.impl

fun Coordinate.addX(value: Int): Coordinate = Coordinate(x = x + value, y = y)
fun Coordinate.addY(value: Int): Coordinate = Coordinate(y = y + value, x = x)