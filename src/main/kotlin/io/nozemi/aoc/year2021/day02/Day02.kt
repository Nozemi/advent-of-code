package io.nozemi.aoc.year2021.day02

import io.nozemi.aoc.Puzzle
import io.nozemi.aoc.year2021.day02.impl.Submarine

class Day02(year: Int, input: String) : Puzzle<MutableList<String>>(year, input) {

    public override lateinit var solutionInput: MutableList<String>

    override fun loadInput(input: String) {
        solutionInput = input.split("\n").mapTo(mutableListOf()) { it.trim() }
    }

    override fun part1(): String {
        val submarine = Submarine()
        submarine.moveSubmarine(rawCommands = solutionInput)
        return "${submarine.horizontal * submarine.depth} (Submarine(horizontal=${submarine.horizontal}, depth=${submarine.depth}))"
    }

    override fun part2(): String {
        val submarine = Submarine(aimedMode = true)
        submarine.moveSubmarine(rawCommands = solutionInput)
        return "${submarine.horizontal * submarine.depth} (Submarine(horizontal=${submarine.horizontal}, depth=${submarine.depth}))"
    }
}