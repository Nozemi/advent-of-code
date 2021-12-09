package io.nozemi.aoc.solutions.year2020.day01

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class ReportRepair(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int> = this.toList().map { it.trim().toInt() }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        val result = findTwoNumbersThatAmountsTo(2020, this.parsedInput) ?: return 0
        return result.first * result.second
    }

    private fun part2(): Int {
        val result = findThreeNumbersThatAmountsTo(2020, this.parsedInput)
        if (result.size < 3) return 0
        return result[0] * result[1] * result[2]
    }

    fun findTwoNumbersThatAmountsTo(amountsTo: Int, input: List<Int>): Pair<Int, Int>? {
        for (x in input.indices) {
            for (y in input.indices) {
                if(input[x] + input[y] == amountsTo) {
                    return Pair(input[x], input[y])
                }
            }
        }

        return null
    }

    fun findThreeNumbersThatAmountsTo(amountsTo: Int, input: List<Int>): IntArray {
        for (x in input.indices) {
            for (y in input.indices) {
                for (z in input.indices) {
                    if(input[x] + input[y] + input[z] == amountsTo) {
                        return intArrayOf(input[x], input[y], input[z])
                    }
                }
            }
        }
        return intArrayOf()
    }
}