package io.nozemi.aoc.solutions.year2021.day09

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class SmokeBasin(input: String) : Puzzle<Array<IntArray>>(input) {

    override fun Sequence<String>.parse(): Array<IntArray> = this.map { line ->
        line.toCharArray().map { digit -> digit.digitToInt() }.toIntArray()
    }.toList().toTypedArray()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1
    )

    val lowPoints = mutableListOf<Int>()

    private fun part1(): String {
        rawInput.forEachIndexed { row, columns ->
            run {
                columns.forEachIndexed { column, number ->
                    run {
                        // Get number above if row isn't 0
                        val above = if (row == 0) null else rawInput[row - 1][column]
                        val below = if (row == rawInput.size - 1) null else rawInput[row + 1][column]
                        val left = if (column == 0) null else rawInput[row][column - 1]
                        val right = if (column == columns.size - 1) null else rawInput[row][column + 1]
                        if (
                            (above == null || number < above)
                            && (below == null || number < below)
                            && (left == null || number < left)
                            && (right == null || number < right)
                        ) {
                            lowPoints.add(number)
                        }
                    }
                }
            }
        }

        return "$lowPoints : ${lowPoints.sumOf { it + 1 }}"
    }
}