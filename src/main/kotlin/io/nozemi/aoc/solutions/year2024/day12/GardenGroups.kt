package io.nozemi.aoc.solutions.year2024.day12

import io.nozemi.aoc.types.Coordinates
import io.nozemi.aoc.types.matrix.CharMatrix
import io.nozemi.aoc.types.matrix.IMatrix
import io.nozemi.aoc.types.matrix.charMatrix
import io.nozemi.aoc.types.puzzle.Puzzle

fun main() {
    GardenGroups(
        """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent()
    ).printAnswers()
}

class GardenGroups(input: String) : Puzzle<CharMatrix>(input) {

    override fun Sequence<String>.parse() = charMatrix(this)

    override fun solutions() = listOf(
        ::part1
    )

    private fun part1() = parsedInput.findGroups()
        .map { (crop, groups) ->
            groups.sumOf {
                it.size * parsedInput.perimeter(crop, it).size
            }
        }.sum()


    private fun CharMatrix.perimeter(crop: Char, area: List<Coordinates>) =
        area.map { cell ->
            this.surrounding(cell, onlyWithinBounds = false)
                .filter { parsedInput.getAt(it) != crop }
        }.flatten()
}
