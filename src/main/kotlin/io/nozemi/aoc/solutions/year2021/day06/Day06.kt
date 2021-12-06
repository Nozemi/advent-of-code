package io.nozemi.aoc.solutions.year2021.day06

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class Day06(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int>
        = this.first().split(",").toList().map { it.trim().toInt() }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        return getLanternFishForDuration(80)
    }

    private fun part2(): Long {
        return getLanternFishForDuration(256)
    }

    /**
     * Gets the amount of lantern fish after the provided amount of days.
     */
    private fun getLanternFishForDuration(days: Int): Long {
        val fish: Array<Long> = Array(9) { 0L }
        rawInput.forEach {
            fish[it] = ++fish[it]
        }

        for (day in 1 until days + 1) {
            val born = fish[0]

            fish.forEachIndexed { age, amount ->
                run {
                    if (age == 0) return@forEachIndexed
                    fish[age - 1] = amount
                }
            }
            fish[8] = born
            fish[6] += born
        }

        return fish.sum()
    }
}