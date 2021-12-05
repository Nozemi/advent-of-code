package io.nozemi.aoc.solutions.year2020.day01

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class Day01(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int> = this.toList().map { it.trim().toInt() }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return 0
    }

    private fun part2(): Int {
        return 0
    }
}