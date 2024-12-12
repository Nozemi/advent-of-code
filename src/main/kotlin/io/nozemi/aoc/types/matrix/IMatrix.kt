package io.nozemi.aoc.types.matrix

import io.nozemi.aoc.types.Coordinates
import io.nozemi.aoc.types.Direction

interface IMatrix<T> : Iterable<MatrixCell<T>> {
    val cols: Int
    val rows: Int

    val distinctValues: List<T>

    fun isWithinBounds(coords: Coordinates) = coords.x in 0..<cols && coords.y in 0..<rows

    fun getAt(coords: Coordinates): T?
    fun getAt(x: Int, y: Int) = getAt(Coordinates(x, y))
    fun getAt(coords: List<Coordinates>): List<T> = coords.mapNotNull { getAt(it) }

    fun setAt(x: Int, y: Int, value: T) = setAt(Coordinates(x, y), value)
    fun setAt(coords: Coordinates, value: T): Boolean
    fun setAt(coords: List<Coordinates>, value: T): Map<Coordinates, Boolean> =
        coords.associateWith { setAt(it, value) }

    fun findAll(search: T): List<Coordinates>

    fun surrounding(
        coords: Coordinates,
        allowDiagonal: Boolean = false,
        onlyWithinBounds: Boolean = true
    ): List<MatrixCell<T>> {
        val surroundingTiles = mutableMapOf(
            Direction.WEST to Coordinates(coords.x - 1, coords.y),
            Direction.EAST to Coordinates(coords.x + 1, coords.y),
            Direction.SOUTH to Coordinates(coords.x, coords.y + 1),
            Direction.NORTH to Coordinates(coords.x, coords.y - 1)
        )

        if (allowDiagonal)
            surroundingTiles += mapOf(
                Direction.NORTH_WEST to Coordinates(coords.x - 1, coords.y - 1),
                Direction.NORTH_EAST to Coordinates(coords.x + 1, coords.y - 1),
                Direction.SOUTH_WEST to Coordinates(coords.x - 1, coords.y + 1),
                Direction.SOUTH_EAST to Coordinates(coords.x + 1, coords.y + 1),
            )

        return surroundingTiles.filter { !onlyWithinBounds || isWithinBounds(it.value) }
            .map { MatrixCell(it.value, getAt(it.value)) }
    }

    val allGroups get() = distinctValues.associateWith { findGroups(it) }

    fun findGroups(value: T): List<List<Coordinates>> {
        val visited = mutableSetOf<Coordinates>()
        val currentGroups = mutableListOf<List<Coordinates>>()

        this.findAll(value).forEach valueLoop@{ v ->
            if (v in visited)
                return@valueLoop

            val group = mutableListOf<Coordinates>()
            floodFill(v, value, visited, group)

            if (group.isEmpty())
                return@valueLoop

            currentGroups.add(group)
        }

        return currentGroups
    }

    fun floodFill(
        start: Coordinates,
        value: T,
        visited: MutableSet<Coordinates>,
        group: MutableList<Coordinates>
    ) {
        val stack = mutableListOf(start)

        while (stack.isNotEmpty()) {
            val current = stack.removeAt(stack.size - 1)

            if (current in visited)
                continue

            visited.add(current)
            if (this.getAt(current) == value) {
                group.add(current)

                stack.addAll(
                    this.surrounding(current)
                        .map { it.coordinates }
                        .filter {
                            it !in visited && this.getAt(current) == value
                        }
                )
            }
        }
    }

    fun copyOf(): IMatrix<T>
}