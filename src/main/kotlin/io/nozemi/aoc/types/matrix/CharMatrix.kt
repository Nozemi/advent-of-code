package io.nozemi.aoc.types.matrix

import io.nozemi.aoc.types.Coordinates

class CharMatrix(
    private val values: Array<CharArray>
) : IMatrix<Char> {
    val distinctValues get() = values.map { it.distinct() }.flatten().distinct()

    override val cols get() = values[0].size
    override val rows get() = values.size

    override fun getAt(coords: Coordinates): Char? {
        if (!isWithinBounds(coords))
            return null

        return values[coords.y][coords.x]
    }

    override fun setAt(coords: Coordinates, value: Char): Boolean {
        if (!isWithinBounds(coords))
            return false

        values[coords.y][coords.x] = value
        return true
    }

    override fun findAll(search: Char): List<Coordinates> = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (col == search)
                return@mapIndexed Coordinates(x, y)

            return@mapIndexed null
        }.filterNotNull()
    }

    override fun toString() = values.joinToString("\n") { it.joinToString(" ") }
    override fun copyOf() = CharMatrix(values.copyOf())
}