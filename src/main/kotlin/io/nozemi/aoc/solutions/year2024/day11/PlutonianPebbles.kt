package io.nozemi.aoc.solutions.year2024.day11

import io.nozemi.aoc.extensions.insertAfter
import io.nozemi.aoc.extensions.leftHalf
import io.nozemi.aoc.extensions.length
import io.nozemi.aoc.extensions.rightHalf
import io.nozemi.aoc.types.puzzle.Puzzle

fun main() {
    PlutonianPebbles("125 17")
        .printAnswers()
}

class PlutonianPebbles(input: String) : Puzzle<LongArray>(input) {

    override fun Sequence<String>.parse() =
        this.joinToString("").split(" ").map { it.toLong() }.toLongArray()

    override fun solutions() = listOf(
        ::part1
    )

    private fun part1() = blink(25, parsedInput, print = false).size

    private fun blink(blinks: Int, stones: LongArray, blink: Int = 1, print: Boolean = false): LongArray {
        if (blink == 1 && print) {
            println("Initial arrangement:")
            println(stones.joinToString(" "))
            println()
        }

        if (blinks == 0)
            return stones

        val currentBlink = stones.toMutableList()

        var modifier = 0
        for (i in stones.indices) {
            val pos = i + modifier
            when {
                stones[i] == 0L -> currentBlink[pos] = 1
                stones[i].length % 2 == 0 -> {
                    val stone = stones[i]
                    currentBlink[pos] = stone.leftHalf
                    currentBlink.insertAfter(pos, stone.rightHalf)
                    modifier++
                }
                else -> currentBlink[pos] = stones[i] * 2024
            }
        }

        if (print) {
            println("After $blink blink${if (blink == 1) "" else "s"}:")
            println(currentBlink.joinToString(" "))
            println()
        }

        return blink(blinks - 1, currentBlink.toLongArray(), blink + 1, print)
    }
}