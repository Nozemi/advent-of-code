package io.nozemi.aoc.solutions.year2021.day02

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2021.day02.impl.Submarine
import kotlin.reflect.KFunction0

class Dive(input: String, unitTest: Boolean = false) : Puzzle<List<String>>(input, unitTest) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        val submarine = Submarine()
        submarine.moveSubmarine(rawCommands = rawInput)
        return submarine.horizontal * submarine.depth
    }

    private fun part2(): Long {
        val submarine = Submarine(aimedMode = true)
        submarine.moveSubmarine(rawCommands = rawInput)
        return submarine.horizontal * submarine.depth
    }
}