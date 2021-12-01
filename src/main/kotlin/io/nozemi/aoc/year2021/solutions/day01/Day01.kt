package io.nozemi.aoc.year2021.solutions.day01

import io.nozemi.aoc.Puzzle

class Day01(year: Int, input: String) : Puzzle<MutableList<Int>>(year, input) {

    public override lateinit var solutionInput: MutableList<Int>

    override fun loadInput(input: String) {
        solutionInput = input.split("\n")
            .mapTo(mutableListOf()) {
                it.trim().toInt()
            }
    }

    fun totalIncreases(groupSize: Int): Int {
        var totalIncreases = 0

        val groups = solutionInput.windowed(groupSize)
        logger.debug { "Groups (amount=${groups.size}, groupSize=$groupSize): $groups" }

        for (i in 1 until groups.size) {
            if (groups[i].sum() > groups[i - 1].sum())
                totalIncreases++
        }

        return totalIncreases
    }

    override fun part1(): String {
        return "${totalIncreases(1)} total increases."
    }

    override fun part2(): String {
        return "${totalIncreases(3)} total increases."
    }
}