package io.nozemi.aoc.solutions.year2020.day03

import io.nozemi.aoc.types.puzzle.Puzzle
import kotlin.reflect.KFunction0

class TobogganTrajectory(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        return traverseMap(3, 1)
    }

    private fun part2(): Long {
        val slope1 = traverseMap(1, 1)
        val slope2 = traverseMap(3, 1)
        val slope3 = traverseMap(5, 1)
        val slope4 = traverseMap(7, 1)
        val slope5 = traverseMap(1, 2)
        return slope1 * slope2 * slope3 * slope4 * slope5
    }

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
}