package io.nozemi.aoc.year2021.solutions.day01

import io.nozemi.aoc.Puzzle
import java.nio.file.Files
import java.nio.file.Path

class Day01(year: Int) : Puzzle<MutableList<Int>>(year) {

    override lateinit var solutionInput: MutableList<Int>

    private fun Int.isGreaterThan(than: Int): Boolean {
        return this > than
    }

    override fun loadInput(inputFilePath: Path) {
        solutionInput = mutableListOf()
        Files.readAllLines(inputFilePath).forEach {
            solutionInput.add(it.toInt())
        }
    }

    private fun totalIncreases(groupSize: Int): Int {
        var totalIncreases = 0

        val groups = solutionInput.windowed(groupSize)
        logger.debug { "Groups (amount=${groups.size}, groupSize=$groupSize): $groups" }

        for (i in 1 until groups.size) {
            if(groups[i].sum().isGreaterThan(groups[i - 1].sum()))
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