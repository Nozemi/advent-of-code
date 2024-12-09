package io.nozemi.aoc.types

interface IMatrix<T> {
    val cols: Int
    val rows: Int

    fun getAt(x: Int, y: Int): T
    fun getAt(coords: Coordinates): T

    fun set(x: Int, y: Int, value: T)
    fun set(coords: Coordinates, value: T)

    fun findAll(search: T): List<Coordinates>

    fun copyOf(): IMatrix<T>
}

class IntMatrix(
    private val values: Array<IntArray>
) : IMatrix<Int> {
    override val cols get() = values[0].size
    override val rows get() = values.size

    override fun getAt(x: Int, y: Int) = values[y][x]
    override fun getAt(coords: Coordinates) = values[coords.y][coords.x]

    override fun set(coords: Coordinates, value: Int) {
        values[coords.y][coords.x] = value
    }

    override fun set(x: Int, y: Int, value: Int) {
        values[y][x] = value
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

    override fun getAt(x: Int, y: Int) = values[y][x]
    override fun getAt(coords: Coordinates) = values[coords.y][coords.x]

    override fun set(coords: Coordinates, value: Char) {
        values[coords.y][coords.x] = value
    }

    override fun set(x: Int, y: Int, value: Char) {
        values[y][x] = value
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