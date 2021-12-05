package io.nozemi.aoc.solutions.year2021.day04

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2021.day04.impl.Bingo
import io.nozemi.aoc.solutions.year2021.day04.impl.Board
import kotlin.reflect.KFunction0

class Day04(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    private val data = rawInput.joinToString("\n")

    private val winners: List<Board>

    init {
        val bingo = Bingo.parse(data)
        winners = bingo.findWinningBoards()
    }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        if (winners.isEmpty()) return 0L
        return winners.first().calculateScore()
    }

    private fun part2(): Long {
        if (winners.isEmpty()) return 0L
        return winners.last().calculateScore()
    }
}