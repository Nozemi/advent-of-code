package io.nozemi.aoc.puzzles.year2020.day03

import io.nozemi.aoc.puzzles.Puzzle
import java.util.stream.Stream

class Day03(input: String) : Puzzle<List<String>>(input) {

    override lateinit var parsedInput: List<String>

    override fun Stream<String>.parse(): List<String> = this.toStringList()

    fun traverseMap(stepsRight: Int, stepsDown: Int): Long {
        var x = 0
        var trees = 0

        for (y in parsedInput.indices step stepsDown) {
            val level = parsedInput[y]
            if (level.toCharArray()[x] == '#') trees++
            x = (x + stepsRight) % level.length
        }

        return trees.toLong()
    }

    override fun part1(): String {
        val answer = traverseMap(3, 1)
        return "$answer, Slope(stepsRight=3, stepsDown=1, treesFound=$answer)"
    }

    override fun part2(): String {
        val slope1 = traverseMap(1, 1)
        val slope2 = traverseMap(3, 1)
        val slope3 = traverseMap(5, 1)
        val slope4 = traverseMap(7, 1)
        val slope5 = traverseMap(1, 2)
        val answer = (slope1 * slope2 * slope3 * slope4 * slope5)
        return "$answer"
    }
}