package io.nozemi.aoc.solutions.year2021.day08

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2021.day08.impl.SegmentPattern
import io.nozemi.aoc.solutions.year2021.day08.impl.SegmentPattern.Companion.toSegmentPattern
import kotlin.reflect.KFunction0

class SevenSegmentSearch(input: String) : Puzzle<List<SegmentPattern>>(input) {

    override fun Sequence<String>.parse(): List<SegmentPattern> = map { it.toSegmentPattern() }.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1
    )

    private fun part1(): Int {
        return rawInput.sumOf { it.amountOfDigits(1, 4, 7, 8) }
    }

    private fun part2(): Long {
        return rawInput[0].getOutputNumber()
        //return rawInput.sumOf { it.amountOfDigits() }
        return 0
    }
}