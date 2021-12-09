package io.nozemi.aoc.solutions.year2021.day01

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class SonarSweep(input: String, unitTest: Boolean = false) : Puzzle<List<Int>>(input, unitTest) {

    override fun Sequence<String>.parse(): List<Int> = this.map { it.trim().toInt() }.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return totalIncreases(1)
    }

    private fun part2(): Int {
        return totalIncreases(3)
    }

    private fun totalIncreases(groupSize: Int): Int {
        var totalIncreases = 0

        val groups = rawInput.windowed(groupSize)

        for (i in 1 until groups.size) {
            if (groups[i].sum() > groups[i - 1].sum())
                totalIncreases++
        }

        return totalIncreases
    }
}