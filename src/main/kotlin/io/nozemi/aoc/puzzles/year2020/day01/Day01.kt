package io.nozemi.aoc.puzzles.year2020.day01

import io.nozemi.aoc.puzzles.Puzzle
import java.util.stream.Stream

class Day01(year: Int, input: String) : Puzzle<List<Int>>(year, input) {

    public override lateinit var parsedInput: List<Int>

    override fun Stream<String>.parse(): List<Int> = this.toIntList()

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

    override fun part1(): String {
        val result = findTwoNumbersThatAmountsTo(2020, parsedInput)
            ?: return "No two numbers amounts to 2020 in the given input."
        return "${result.first * result.second} (${result.first} * ${result.second} = ${result.first * result.second})"
    }

    override fun part2(): String {
        val result = findThreeNumbersThatAmountsTo(2020, parsedInput)
        if(result.size < 3) {
            return "No three numbers amounts to 2020 in the given input."
        }
        return "${result[0] * result[1] * result[2]} (${result[0]} * ${result[1]} * ${result[2]} = ${result[0] * result[1] * result[2]})"
    }
}