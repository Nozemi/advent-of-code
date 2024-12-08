package io.nozemi.aoc.solutions.year2024.day07

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.time.measureTime

fun main() {
    BridgeRepair(
        """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent()
    ).printAnswers()
}

class BridgeRepair(input: String) : Puzzle<Map<Long, List<Long>>>(input) {

    override fun Sequence<String>.parse() = this.map {
        val (total, numbers) = it.split(':')

        return@map Pair(total.toLong(), numbers.split(' ').mapNotNull(String::toLongOrNull))
    }.toMap()

    override fun solutions() = listOf(
        ::part1,
        ::part2
    )

    private fun part1() = parsedInput.findValidCalculations(
        listOf(Plus(), Multiply())
    )

    private fun part2() = parsedInput.findValidCalculations(
        listOf(Plus(), Multiply(), Concatenate())
    )

    private fun Map<Long, List<Long>>.findValidCalculations(operators: List<Operator>) =
        this.filter { (total, numbers) ->
            if (operators.any { total == it.call(numbers) })
                return@filter true

            return@filter permutations(numbers.size, operators).distinct()
                .any { combo ->
                    total == numbers.reduceIndexed { index, a, b ->
                        combo[index].call(listOf(a, b))
                    }
                }
        }.map { it.key }
            .sum()

    private fun permutations(size: Int, values: List<Operator>): List<Array<Operator>> {
        if (size == 1) return values.map { arrayOf(it) }

        val permutations = mutableListOf<Array<Operator>>()
        for (value in values) {
            val smaller = permutations(size - 1, values)
            for (permutation in smaller) {
                permutations.add(arrayOf(value) + permutation)
            }
        }

        return permutations
    }
}