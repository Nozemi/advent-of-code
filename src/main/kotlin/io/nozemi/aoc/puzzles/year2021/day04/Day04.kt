package io.nozemi.aoc.puzzles.year2021.day04

import io.nozemi.aoc.puzzles.Puzzle
import io.nozemi.aoc.puzzles.year2021.day04.impl.Bingo
import io.nozemi.aoc.puzzles.year2021.day04.impl.Board
import java.util.stream.Stream

class Day04(year: Int, input: String) : Puzzle<String>(year, input) {

    public override lateinit var parsedInput: String

    override fun Stream<String>.parse(): String {
        return this.toList().joinToString("\n")
    }

    var winners: List<Board> = emptyList()

    init {
        val bingo = Bingo.parse(parsedInput)
        winners = bingo.findWinningBoards()
    }

    override fun part1(): String {
        if (winners.isEmpty()) return "No winners were found."
        val score = winners.first().calculateScore()
        return "$score, first winner's score was $score."
    }

    override fun part2(): String {
        if (winners.isEmpty()) return "No winners were found."
        val score = winners.last().calculateScore()
        return "$score, last winner's score was $score."
    }
}