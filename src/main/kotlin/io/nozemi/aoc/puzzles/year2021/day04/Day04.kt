package io.nozemi.aoc.puzzles.year2021.day04

import io.nozemi.aoc.puzzles.Puzzle
import io.nozemi.aoc.puzzles.year2021.day04.impl.Bingo
import java.util.stream.Stream

class Day04(year: Int, input: String) : Puzzle<String>(year, input) {

    public override lateinit var parsedInput: String

    override fun Stream<String>.parse(): String {
        return this.toList().joinToString("\n")
    }

    override fun part1(): String {
        val bingo = Bingo.parse(parsedInput)
        val winners = bingo.findWinningBoards()

        val unmarkedSum = winners[0].findUnmarkedNumbers(bingo).sum()
        val lastNumber = bingo.drawnNumbers.last()

        return "${unmarkedSum * lastNumber}, ($unmarkedSum * $lastNumber = ${unmarkedSum * lastNumber})"
    }
}