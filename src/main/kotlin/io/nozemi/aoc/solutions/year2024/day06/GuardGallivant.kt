package io.nozemi.aoc.solutions.year2024.day06

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2024.day06.GuardGallivant.Direction.Companion.asDirection
import kotlin.reflect.KFunction0

fun main() {
    val example = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()

    GuardGallivant(example).printAnswers()
}

class GuardGallivant(input: String) : Puzzle<List<List<Char>>>(input) {

    override fun Sequence<String>.parse() = this.map {
        it.toList()
    }.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        val initialPosition = parsedInput.mapIndexedNotNull rowLoop@{ y, gridY ->
            val x = gridY.mapIndexedNotNull columnLoop@{ x, cell ->
                return@columnLoop if (Direction.entries.map { it.symbol }.contains(cell)) x else null
            }.firstOrNull() ?: -1

            if (x >= 0) return@rowLoop Coordinate(x, y)
            else return@rowLoop null
        }.first()

        var isInMap = true
        var direction = parsedInput[initialPosition.y][initialPosition.x].asDirection
        var currentPosition = initialPosition
        val visitedCells = mutableListOf<Coordinate>()
        while (isInMap) {
            visitedCells.add(currentPosition)

            val nextCell = when (direction) {
                Direction.UP -> Coordinate(currentPosition.x, currentPosition.y - 1)
                Direction.DOWN -> Coordinate(currentPosition.x, currentPosition.y + 1)
                Direction.LEFT -> Coordinate(currentPosition.x - 1, currentPosition.y)
                Direction.RIGHT -> Coordinate(currentPosition.x + 1, currentPosition.y)
            }

            val nextSymbol = parsedInput.getOrNull(nextCell.y)?.getOrNull(nextCell.x)

            if (nextSymbol == null) {
                isInMap = false
                continue
            }

            if (nextSymbol == '#') {
                direction = direction.turn90()
            } else {
                currentPosition = nextCell
            }
        }

        return visitedCells.distinct().size
    }

    private fun part2() = 0

    private enum class Direction(val symbol: Char, val degrees: Int) {
        UP('^', 0),
        DOWN('v', 180),
        LEFT('<', 270),
        RIGHT('>', 90);

        fun turn90(): Direction {
            val deg = this.degrees + 90
            if (deg >= 360)
                return UP

            return deg.asDirection
        }

        companion object {
            val Char.asDirection get() = entries.single { it.symbol == this }
            val Int.asDirection get() = entries.single { it.degrees == this }
        }
    }

    data class Coordinate(val x: Int, val y: Int)
}