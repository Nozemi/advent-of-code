package io.nozemi.aoc.solutions.year2021.day08

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2021.day08.impl.SegmentPattern
import io.nozemi.aoc.solutions.year2021.day08.impl.SegmentPattern.Companion.toSegmentPattern
import kotlin.reflect.KFunction0

class SevenSegmentSearch(input: String) : Puzzle<List<SegmentPattern>>(input) {

    override fun Sequence<String>.parse(): List<SegmentPattern> = map { it.toSegmentPattern() }.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return getSumOfUniqueLengthPatternsInOutputs()
    }

    private fun part2(): Int {
        return getSumOfOutputNumbers()
    }

    fun getSumOfUniqueLengthPatternsInOutputs(input: List<SegmentPattern> = rawInput): Int {
        return input.sumOf {
            it.uniqueLengthPatternsInOutput()
        }
    }

    fun getSumOfOutputNumbers(input: List<SegmentPattern> = rawInput): Int {
        return input.sumOf {
            it.rewireOutputPattern()
            it.getOutputNumber()
        }
    }
}