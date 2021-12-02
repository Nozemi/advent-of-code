package io.nozemi.aoc.year2021.day01

import io.nozemi.aoc.Puzzle
import java.util.stream.Stream

class Day01(year: Int, input: String) : Puzzle<List<Int>>(year, input) {

    public override lateinit var parsedInput: List<Int>

    override fun Stream<String>.parse(): List<Int> = this.toIntList()

    fun totalIncreases(groupSize: Int): Int {
        var totalIncreases = 0

        val groups = parsedInput.windowed(groupSize)
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