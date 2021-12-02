package io.nozemi.aoc.year2020.day01

import io.nozemi.aoc.Puzzle

class Day01(year: Int, input: String) : Puzzle<MutableList<Int>>(year, input) {

    public override lateinit var solutionInput: MutableList<Int>

    override fun loadInput(input: String) {
        solutionInput = input.split("\n")
            .mapTo(mutableListOf()) {
                it.trim().toInt()
            }
    }

    fun findTwoNumbersThatAmountsTo(amountsTo: Int, input: MutableList<Int>): Pair<Int, Int>? {
        for (x in input.indices) {
            for (y in input.indices) {
                if(input[x] + input[y] == amountsTo) {
                    return Pair(input[x], input[y])
                }
            }
        }

        return null
    }

    fun findThreeNumbersThatAmountsTo(amountsTo: Int, input: MutableList<Int>): IntArray {
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
        val result = findTwoNumbersThatAmountsTo(2020, solutionInput)
            ?: return "No two numbers amounts to 2020 in the given input."
        return "${result.first * result.second} (${result.first} * ${result.second} = ${result.first * result.second})"
    }

    override fun part2(): String {
        val result = findThreeNumbersThatAmountsTo(2020, solutionInput)
        if(result.size < 3) {
            return "No three numbers amounts to 2020 in the given input."
        }
        return "${result[0] * result[1] * result[2]} (${result[0]} * ${result[1]} * ${result[2]} = ${result[0] * result[1] * result[2]})"
    }
}