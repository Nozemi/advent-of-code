package io.nozemi.aoc.puzzles.year2021.day05

import io.nozemi.aoc.puzzles.Puzzle
import io.nozemi.aoc.puzzles.year2021.day05.impl.DangerDiagram
import java.util.stream.Stream

class Day05(input: String) : Puzzle<List<String>>(input) {

    override fun part1(): String {
        val dangerDiagram = DangerDiagram.fromRawData(parsedInput, draw = false, drawOnlyDangers = false)
        val dangerousPoints = dangerDiagram.dangerousCoordinates.count()
        return "$dangerousPoints, the amount of danger points is $dangerousPoints."
    }

    override fun part2(): String {
        val dangerDiagram = DangerDiagram.fromRawData(parsedInput, considerDiagonals = true, draw = false, drawOnlyDangers = false)
        val dangerousPoints = dangerDiagram.dangerousCoordinates.count()
        return "$dangerousPoints, the amount of danger points is $dangerousPoints."
    }

    override lateinit var parsedInput: List<String>

    override fun Stream<String>.parse(): List<String> {
        return this.toList()
    }
}