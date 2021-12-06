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
        val lanternfish: Array<Long> = Array(9) { 0L }
        rawInput.forEach {
            lanternfish[it] = ++lanternfish[it]
        }

        repeat(days) {
            val born = lanternfish[0]
            lanternfish.forEachIndexed { age, amount -> if (age == 0) return@forEachIndexed else lanternfish[age - 1] = amount }
            lanternfish[8] = born
            lanternfish[6] += born
        }

        return lanternfish.sum()
    }
}