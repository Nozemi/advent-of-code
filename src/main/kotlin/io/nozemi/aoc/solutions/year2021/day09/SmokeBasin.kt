package io.nozemi.aoc.solutions.year2021.day09

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.utils.addIfNotExists
import kotlin.reflect.KFunction0

class SmokeBasin(input: String) : Puzzle<Array<IntArray>>(input) {

    override fun Sequence<String>.parse(): Array<IntArray> = this.map { line ->
        line.toCharArray().map { digit -> digit.digitToInt() }.toIntArray()
    }.toList().toTypedArray()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return getLowPoints().sumOf { it + 1 }
    }

    private fun part2(): Int {
        val basins = findBasins().toMutableList()

        if (basins.size <= 3) {
            throw RuntimeException("You fucked up! You need at least 3 basins.")
        }

        val basinSizes = basins.map { it.size }.toMutableList()

        val threeLargestBasins = IntArray(3) { -1 }
        repeat(3) { iteration ->
            val max = basinSizes.maxOrNull() ?: throw RuntimeException("You fucked up... Should never really come to this!?")
            threeLargestBasins[iteration] = max; basinSizes.remove(max)
        }

        return threeLargestBasins[0] * threeLargestBasins[1] * threeLargestBasins[2]
    }

    fun findBasins(): List<Basin> {
        val basins = mutableListOf<Basin>()
        iterateLowPoints { input, row, column, number ->
            val positions = traverseBasin(input, mutableListOf(), number, row, column)
            basins.add(Basin(size = positions.count(), positions = positions))
        }

        return basins
    }

    private fun aboveConditionBasin(input: Array<IntArray>, row: Int, column: Int, number: Int): Boolean =
        (row != 0 && input[row - 1][column] < 9 && input[row - 1][column] > number)

    private fun belowConditionBasin(input: Array<IntArray>, row: Int, column: Int, number: Int): Boolean =
        (row < (input.size - 1) && input[row + 1][column] < 9 && input[row + 1][column] > number)

    private fun leftConditionBasin(input: Array<IntArray>, row: Int, column: Int, number: Int): Boolean =
        (column != 0 && input[row][column - 1] < 9 && input[row][column - 1] > number)

    private fun rightConditionBasin(input: Array<IntArray>, row: Int, column: Int, number: Int): Boolean =
        (column < (input[row].size - 1) && input[row][column + 1] < 9 && input[row][column + 1] > number)

    private fun traverseBasin(
        input: Array<IntArray>, visitedNodes: MutableList<Position>, currentNumber: Int, row: Int, column: Int
    ): MutableList<Position> {
        visitedNodes.addIfNotExists(Position(row, column, currentNumber))

        if (aboveConditionBasin(input, row, column, currentNumber)) {
            visitedNodes.addIfNotExists(Position(row, column, input[row - 1][column]))
            traverseBasin(input, visitedNodes, input[row - 1][column], row - 1, column)
        }

        if (belowConditionBasin(input, row, column, currentNumber)) {
            visitedNodes.addIfNotExists(Position(row, column, input[row + 1][column]))
            traverseBasin(input, visitedNodes, input[row + 1][column], row + 1, column)
        }

        if (leftConditionBasin(input, row, column, currentNumber)) {
            visitedNodes.addIfNotExists(Position(row, column, input[row][column - 1]))
            traverseBasin(input, visitedNodes, input[row][column - 1], row, column - 1)
        }

        if (rightConditionBasin(input, row, column, currentNumber)) {
            visitedNodes.addIfNotExists(Position(row, column, input[row][column + 1]))
            traverseBasin(input, visitedNodes, input[row][column + 1], row, column + 1)
        }

        return visitedNodes
    }

    private inline fun iterateLowPoints(
        input: Array<IntArray> = rawInput,
        doWithPosition: (input: Array<IntArray>, row: Int, column: Int, number: Int) -> Unit
    ) {
        input.forEachIndexed { row, columns ->
            columns.forEachIndexed { column, number ->
                // Get number above if row isn't 0
                val above = if (row == 0) 10 else input[row - 1][column]
                val below = if (row == input.size - 1) 10 else input[row + 1][column]
                val left = if (column == 0) 10 else input[row][column - 1]
                val right = if (column == columns.size - 1) 10 else input[row][column + 1]

                if ((number < above) && (number < below) && (number < left) && (number < right)) {
                    doWithPosition(input, row, column, number)
                }
            }
        }
    }

    fun getLowPoints(input: Array<IntArray> = rawInput): List<Int> {
        val lowPoints = mutableListOf<Int>()

        iterateLowPoints(input) { _, _, _, number ->
            lowPoints.add(number)
        }

        return lowPoints
    }

    data class Basin(
        val size: Int,
        val positions: List<Position>
    )

    data class Position(
        val row: Int,
        val column: Int,
        val value: Int
    ) {

        override fun equals(other: Any?): Boolean {
            if (other == null || other !is Position) return false
            return this.row == other.row && this.column == other.column
        }

        override fun hashCode(): Int {
            var result = row
            result = 31 * result + column
            result = 31 * result + value
            return result
        }
    }
}