package io.nozemi.aoc.types.datastructures.matrix

import io.nozemi.aoc.types.Vector2

class CharMatrix(
    private val values: Array<CharArray>
) : IMatrix<Char> {
    override val cols get() = values[0].size
    override val rows get() = values.size

    override val distinctValues get() = values.map { it.distinct() }.flatten().distinct()

    fun forEach(action: (row: CharArray) -> Unit) {
        values.forEach { action(it) }
    }

    override fun getAt(coords: Vector2): Char? {
        if (!isWithinBounds(coords))
            return null

        return values[coords.y][coords.x]
    }

    override fun setAt(coords: Vector2, value: Char): Boolean {
        if (!isWithinBounds(coords))
            return false

        values[coords.y][coords.x] = value
        return true
    }

    override fun findAll(search: Char): List<Vector2> = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (col == search)
                return@mapIndexed Vector2(x, y)

            return@mapIndexed null
        }.filterNotNull()
    }

    override fun toString() = values.joinToString("\n") { it.joinToString(" ") }
    override fun copyOf() = CharMatrix(values.copyOf())

    override fun iterator() = values.mapIndexed { y, row ->
        row.mapIndexed { x, _ -> MatrixCell(x, y, values[y][x]) }
    }.flatten().iterator()
}