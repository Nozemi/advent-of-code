package io.nozemi.aoc.solutions.year2021.day05

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2021.day05.impl.DangerDiagram
import kotlin.reflect.KFunction0

class HydrothermalVenture(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return solve()
    }

    private fun part2(): Int {
        return solve(considerDiagonals = true)
    }

    fun solve(considerDiagonals: Boolean = false): Int {
        val dangerDiagram = DangerDiagram.fromRawData(parsedInput, considerDiagonals = considerDiagonals)
        return dangerDiagram.dangerousCoordinates.count()
    }
}