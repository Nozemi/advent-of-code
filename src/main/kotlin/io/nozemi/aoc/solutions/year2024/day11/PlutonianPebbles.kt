package io.nozemi.aoc.solutions.year2024.day11

import io.nozemi.aoc.extensions.length
import io.nozemi.aoc.types.puzzle.Puzzle

fun main() {
    PlutonianPebbles("125 17")
        .printAnswers()
}

class PlutonianPebbles(input: String) : Puzzle<List<Long>>(input) {

    override fun Sequence<String>.parse() =
        this.joinToString("").split(" ").map { it.toLong() }

    override fun solutions() = listOf(
        ::part1,
        ::part2
    )

    private fun part1() = blink(25, parsedInput)
    private fun part2() = blink(75, parsedInput)

    private fun blink(blinks: Int, stones: List<Long>): Long {
        var stoneCounts = stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

        repeat(blinks) {
            val newStoneCounts = mutableMapOf<Long, Long>()
            for ((stone, count) in stoneCounts) {
                when {
                    stone == 0L -> newStoneCounts[1L] = newStoneCounts.getOrDefault(1L, 0L) + count
                    stone.length % 2 == 0 -> {
                        val stoneStr = stone.toString()
                        val halfLength = stoneStr.length / 2
                        val leftHalf = stoneStr.substring(0, halfLength).toLong()
                        val rightHalf = stoneStr.substring(halfLength).toLong()
                        newStoneCounts[leftHalf] = newStoneCounts.getOrDefault(leftHalf, 0L) + count
                        newStoneCounts[rightHalf] = newStoneCounts.getOrDefault(rightHalf, 0L) + count
                    }
                    else -> {
                        val newStone = stone * 2024
                        newStoneCounts[newStone] = newStoneCounts.getOrDefault(newStone, 0L) + count
                    }
                }
            }
            stoneCounts = newStoneCounts
        }

        return stoneCounts.values.sum()
    }
}