package io.nozemi.aoc.puzzles.year2021.day02

import io.nozemi.aoc.puzzles.Puzzle
import io.nozemi.aoc.puzzles.year2021.day02.impl.Submarine
import java.util.stream.Stream

class Day02(input: String) : Puzzle<List<String>>(input) {

    public override lateinit var parsedInput: List<String>

    override fun Stream<String>.parse(): List<String> = this.toStringList()

    override fun part1(): String {
        val submarine = Submarine()
        submarine.moveSubmarine(rawCommands = parsedInput)
        return "${submarine.horizontal * submarine.depth} (Submarine(horizontal=${submarine.horizontal}, depth=${submarine.depth}))"
    }

    override fun part2(): String {
        val submarine = Submarine(aimedMode = true)
        submarine.moveSubmarine(rawCommands = parsedInput)
        return "${submarine.horizontal * submarine.depth} (Submarine(horizontal=${submarine.horizontal}, depth=${submarine.depth}))"
    }
}