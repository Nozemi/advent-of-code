package io.nozemi.aoc.types

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

class IntMatrix(
    private val values: Array<IntArray>
) : IMatrix<Int> {
    override val cols get() = values[0].size
    override val rows get() = values.size

    override fun getAt(coords: Coordinates): Int? {
        if (!isWithinBounds(coords))
            return null

        return values[coords.y][coords.x]
    }

    override fun setAt(coords: Coordinates, value: Int): Boolean {
        if (!isWithinBounds(coords))
            return false

        values[coords.y][coords.x] = value
        return true
    }

    override fun findAll(search: Int): List<Coordinates> = values.flatMapIndexed { y, row ->
        row.mapIndexed { x, col ->
            if (col == search)
                return@mapIndexed Coordinates(x, y)

            return@mapIndexed null
        }.filterNotNull()
    }

    override fun toString() = values.joinToString("\n") { it.joinToString(" ") }
    override fun copyOf() = IntMatrix(values.copyOf())
}

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

fun intMatrix(rows: Int, cols: Int, defaultValue: Int = 0) =
    IntMatrix(Array(rows) { IntArray(cols) { defaultValue } })

fun intMatrix(raw: String) = IntMatrix(raw.split("\n").map { row ->
    row.map { it.digitToInt() }.toIntArray()
}.toTypedArray())

fun intMatrix(raw: Sequence<String>) = IntMatrix(raw.map {
    it.map { row ->
        row.digitToInt()
    }.toIntArray()
}.toList().toTypedArray())

fun charMatrix(rows: Int, cols: Int, defaultValue: Char = Char.MIN_VALUE) =
    CharMatrix(Array(rows) { CharArray(cols) { defaultValue } })

fun charMatrix(raw: String) = CharMatrix(raw.split("\n").map { it.toCharArray() }.toTypedArray())
fun charMatrix(raw: Sequence<String>) = CharMatrix(raw.map { it.toCharArray() }.toList().toTypedArray())

fun main() {
    val intMatrix = intMatrix(3, 3)
    val charMatrix = charMatrix(3, 3, '.')

    val antennaMatrix = charMatrix(
        """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()
    )

    println(charMatrix)
    println()
    println(intMatrix)
    println()
    println(antennaMatrix)
}