package io.nozemi.aoc.solutions.year2020.day04

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class PassportProcessing(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    private val rawPassports = this.rawInput.toList().joinToString("\n")

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): String {
        return "Need to re-implement solution."
    }

    private fun part2(): String {
        return "Need to re-implement solution."
    }
}