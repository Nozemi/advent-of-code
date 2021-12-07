package io.nozemi.aoc.solutions.year2021.day07

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class TheTreacheryOfWhales(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int> = this.toList()[0].split(",").map { it.trim().toInt() }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1
    )

    private fun part1(): Int {
        var target = 0
        val maxValue = rawInput.maxOrNull() ?: 1
        var fuelBurned = 0

        repeat(maxValue) {
            var fuelThisIteration = 0
            val crabs = rawInput.toMutableList()
            while (!crabs.parallelStream().allMatch { it == target }) {
                crabs.forEachIndexed { index, crab ->
                    if (crab == target) return@forEachIndexed
                    if (crab < target) crabs[index] = crab + 1
                    if (crab > target) crabs[index] = crab - 1
                    fuelThisIteration++
                }
            }
            if (fuelThisIteration < fuelBurned || fuelBurned == 0) fuelBurned = fuelThisIteration
            target++
        }

        return fuelBurned
    }
}