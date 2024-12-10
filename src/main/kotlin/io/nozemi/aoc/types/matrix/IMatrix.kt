package io.nozemi.aoc.types.matrix

import io.nozemi.aoc.types.Coordinates
import io.nozemi.aoc.types.Direction

interface IMatrix<T> {
    val cols: Int
    val rows: Int

    fun isWithinBounds(coords: Coordinates) = coords.x in 0..<cols && coords.y in 0..<rows

    fun getAt(coords: Coordinates): T?
    fun getAt(x: Int, y: Int) = getAt(Coordinates(x, y))
    fun getAt(coords: List<Coordinates>): List<T> = coords.mapNotNull { getAt(it) }

    fun setAt(x: Int, y: Int, value: T) = setAt(Coordinates(x, y), value)
    fun setAt(coords: Coordinates, value: T): Boolean
    fun setAt(coords: List<Coordinates>, value: T): Map<Coordinates, Boolean> = coords.associateWith { setAt(it, value) }

    fun findAll(search: T): List<Coordinates>

    fun surrounding(coords: Coordinates, allowDiagonal: Boolean = false) = mapOf(
        Direction.WEST to Coordinates(coords.x - 1, coords.y),
        Direction.EAST to Coordinates(coords.x + 1, coords.y),
        Direction.SOUTH to Coordinates(coords.x, coords.y + 1),
        Direction.NORTH to Coordinates(coords.x, coords.y - 1)
    ).filter { isWithinBounds(it.value) }
        .map { it.value }

    fun copyOf(): IMatrix<T>
}