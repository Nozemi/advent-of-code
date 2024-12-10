package io.nozemi.aoc.solutions.year2021.day11

import io.nozemi.aoc.types.puzzle.Puzzle
import kotlin.reflect.KFunction0

fun main() {
    DumboOctopus(
        """
        5665114554
        4882665427
        6185582113
        7762852744
        7255621841
        8842753123
        8225372176
        7212865827
        7758751157
        1828544563
        """.trimIndent()
    ).printAnswers()
}

class DumboOctopus(val input: String) : Puzzle<List<List<Octopus>>>(input) {

    override fun Sequence<String>.parse(): List<List<Octopus>> =
        map { row -> row.toCharArray().map { octopus -> Octopus(octopus.digitToInt(), 0, false) }.toList() }.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return simulateFlashes(input = parsedInput, steps = 100, visualize = false)
    }

    private fun part2(): Int {
        parsedInput = input.lineSequence().parse()
        return simulateFlashes(input = parsedInput, steps = -1, visualize = false, findFirstGlobalFlash = true)
    }

    private fun simulateFlashes(
        steps: Int = 2,
        input: List<List<Octopus>> = parsedInput,
        visualize: Boolean = false,
        findFirstGlobalFlash: Boolean = false
    ): Int {
        if (visualize) {
            println("Before any steps:")
            parsedInput.visualizeInConsole()
            println()
        }
        repeat(if (findFirstGlobalFlash) Int.MAX_VALUE else steps) { index ->
            input.forEach {
                it.forEach { octopus ->
                    octopus.flashed = false
                }
            }

            input.flatten().forEach { it.energy++ }


            if (input.flatten().any { it.energy > 9 }) {
                for (y in input.indices) {
                    for (x in 0 until input[y].size) {
                        input.flash(Coordinate(x, y))
                    }
                }
            }

            for (y in input.indices) {
                for (x in 0 until input[y].size) {
                    if (input[y][x].flashed) input[y][x].energy = 0
                }
            }

            if (input.flatten().all { it.energy == 0 }) return index + 1

            if (visualize) {
                println("After step ${index + 1}:")
                parsedInput.visualizeInConsole()
                println()
            }
        }

        return input.flatten().sumOf { it.flashes }
    }

    private fun List<List<Octopus>>.flash(coordinate: Coordinate) {
        // This octopus has already flashed this step, or is less than 10.
        if (this.getCoordinate(coordinate).flashed || this.getCoordinate(coordinate).energy <= 9) return

        // Change the values accordingly.
        this.getCoordinate(coordinate).flashes++
        this.getCoordinate(coordinate).flashed = true

        // Process adjacent octopuses.
        this.getAdjacent(coordinate).forEach {
            if (++this[it.y][it.x].energy > 9) this.flash(it)
        }
    }


    private fun List<List<Octopus>>.getAdjacent(
        coordinate: Coordinate,
        includeDiagonal: Boolean = true
    ): List<Coordinate> {
        return getAdjacent(coordinate.x, coordinate.y, includeDiagonal)
    }

    /**
     * Returns all possible adjacent cell coordinates.
     */
    private fun List<List<Octopus>>.getAdjacent(x: Int, y: Int, includeDiagonal: Boolean = true): List<Coordinate> {
        val adjacent = mutableListOf<Coordinate>()

        // Let's check if we're on the top row, if not, add cell above.
        if (y > 0) adjacent.add(Coordinate(x, y - 1))
        // Let's check if we're on the bottom row, if not, add cell below.
        if (y < this.size - 1) adjacent.add(Coordinate(x, y + 1))
        // Let's check if we're on the leftmost column, if not, add cell to the left.
        if (x > 0) adjacent.add(Coordinate(x - 1, y))
        // Let's check if we're on the rightmost column, if not, add cell to the right.
        if (x < this[y].size - 1) adjacent.add(Coordinate(x + 1, y))

        if (!includeDiagonal) return adjacent

        // Let's check if we're in the top left cell, if not, add cell top & left.
        if (y > 0 && x > 0) adjacent.add(Coordinate(x - 1, y - 1))
        // Let's check if we're  in the top right cell, if not, add cell top & right.
        if (y > 0 && x < this[y].size - 1) adjacent.add(Coordinate(x + 1, y - 1))
        // Let's check if we're in the bottom left corner, if not, add cell below & left.
        if (y < this.size - 1 && x > 0) adjacent.add(Coordinate(x - 1, y + 1))
        // Let's check if we're in the bottom right corner, if not, add cell below & right
        if (y < this.size - 1 && x < this[y].size - 1) adjacent.add(Coordinate(x + 1, y + 1))

        return adjacent
    }

    private fun List<List<Octopus>>.getCoordinate(coordinate: Coordinate): Octopus {
        return this[coordinate.y][coordinate.x]
    }

    private fun List<List<Octopus>>.visualizeInConsole() {
        for (y in 0 until this.size) {
            for (x in 0 until this[y].size) {
                print("${this[y][x].energy} ")
            }
            println()
        }
    }
}

data class Coordinate(val x: Int, val y: Int)
data class Octopus(var energy: Int, var flashes: Int = 0, var flashed: Boolean = false)