﻿package io.nozemi.aoc.types.datastructures.matrix

import io.nozemi.aoc.types.Vector2

class IntMatrix(
    private val values: Array<IntArray>
) : IMatrix<Int> {
    override val cols get() = values[0].size
    override val rows get() = values.size

    override val distinctValues
        get() = values.map { it.distinct() }
            .flatten()
            .distinct()

    override fun getAt(coords: Vector2): Int? {
        if (!isWithinBounds(coords))
            return null

        return values[coords.y][coords.x]
    }

    override fun setAt(coords: Vector2, value: Int): Boolean {
        if (!isWithinBounds(coords))
            return false

        values[coords.y][coords.x] = value
        return true
    }

    override fun findAll(search: Int): List<Vector2> = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (col == search)
                return@mapIndexed Vector2(x, y)

            return@mapIndexed null
        }.filterNotNull()
    }

    override fun toString() = values.joinToString("\n") { it.joinToString(" ") }
    override fun copyOf() = IntMatrix(values.copyOf())

    override fun iterator() = values.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> MatrixCell(x, y, values[y][x]) }
    }.flatten().iterator()
}