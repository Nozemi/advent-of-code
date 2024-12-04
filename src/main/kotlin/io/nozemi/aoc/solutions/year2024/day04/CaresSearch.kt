package io.nozemi.aoc.solutions.year2024.day04

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

fun main() {
    CaresSearch(
        """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
    ).printAnswers()
}

class CaresSearch(input: String) : Puzzle<List<List<Char>>>(input) {

    override fun Sequence<String>.parse() = this.map {
        it.toList()
    }.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        val rows = parsedInput.size
        val columns = parsedInput.first().size

        var count = 0
        for ((lineNumber, line) in parsedInput.withIndex()) {
            for ((charColumn, char) in line.withIndex()) {
                if (char != 'X')
                    continue

                if (charColumn + 3 < columns) {
                    if (line[charColumn + 1] == 'M'
                        && line[charColumn + 2] == 'A'
                        && line[charColumn + 3] == 'S'
                    ) count++
                }

                if (charColumn - 3 >= 0) {
                    if (line[charColumn - 1] == 'M'
                        && line[charColumn - 2] == 'A'
                        && line[charColumn - 3] == 'S'
                    ) count++
                }

                if (lineNumber + 3 < rows) {
                    if (parsedInput[lineNumber + 1][charColumn] == 'M'
                        && parsedInput[lineNumber + 2][charColumn] == 'A'
                        && parsedInput[lineNumber + 3][charColumn] == 'S'
                    ) count++
                }

                if (lineNumber - 3 >= 0) {
                    if (parsedInput[lineNumber - 1][charColumn] == 'M'
                        && parsedInput[lineNumber - 2][charColumn] == 'A'
                        && parsedInput[lineNumber - 3][charColumn] == 'S'
                    ) count++
                }

                if (lineNumber + 3 < rows && charColumn + 3 < columns) {
                    if (parsedInput[lineNumber + 1][charColumn + 1] == 'M'
                        && parsedInput[lineNumber + 2][charColumn + 2] == 'A'
                        && parsedInput[lineNumber + 3][charColumn + 3] == 'S'
                    ) count++
                }

                if (lineNumber + 3 < rows && charColumn - 3 >= 0) {
                    if (parsedInput[lineNumber + 1][charColumn - 1] == 'M'
                        && parsedInput[lineNumber + 2][charColumn - 2] == 'A'
                        && parsedInput[lineNumber + 3][charColumn - 3] == 'S'
                    ) count++
                }

                if (lineNumber - 3 >= 0 && charColumn + 3 < columns) {
                    if (parsedInput[lineNumber - 1][charColumn + 1] == 'M'
                        && parsedInput[lineNumber - 2][charColumn + 2] == 'A'
                        && parsedInput[lineNumber - 3][charColumn + 3] == 'S'
                    ) count++
                }

                if (lineNumber - 3 >= 0 && charColumn - 3 >= 0) {
                    if (parsedInput[lineNumber - 1][charColumn - 1] == 'M'
                        && parsedInput[lineNumber - 2][charColumn - 2] == 'A'
                        && parsedInput[lineNumber - 3][charColumn - 3] == 'S'
                    ) count++
                }
            }
        }

        return count
    }

    private fun part2() = 0
}