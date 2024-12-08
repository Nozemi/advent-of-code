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

    //println(10 power 3)
}

infix fun Int.power(exponent: Int): Int =
    if (exponent != 0)
        this * (this power exponent - 1)
    else 1

class BridgeRepair(input: String) : Puzzle<Map<Long, List<Long>>>(input) {

    override fun Sequence<String>.parse() =
        this.map {
            val (total, numbers) = it.split(':')

            return@map Pair(total.toLong(), numbers.split(' ').mapNotNull(String::toLongOrNull))
        }.toMap()

    override fun solutions() =
        listOf(
            ::part1,
            ::part2
        )

    private fun part1() =
        parsedInput.findValidCalculations(
            intArrayOf(1, 2)
        )

    private fun part2() =
        parsedInput.findValidCalculations(
            intArrayOf(1, 2, 3)
        )

    private val operators = mapOf(
        1 to Plus(),
        2 to Multiply(),
        3 to Concatenate()
    )

    private fun Map<Long, List<Long>>.findValidCalculations(o: IntArray) =
        this.filter { (total, numbers) ->
            if (o.any { total == operators[it]!!.call(numbers) })
                return@filter true

            return@filter permutations(numbers.size, o).firstOrNull { combo ->
                total == numbers.reduceIndexed { index, a, b ->
                    operators[combo[index]]?.call(listOf(a, b)) ?: 0
                }
            } != null
        }.map { it.key }
            .sum()

    private val permutationsCache = mutableMapOf<Pair<Int, IntArray>, Array<IntArray>>()
    private fun permutations(size: Int, values: IntArray): Array<IntArray> {
        val key = Pair(size, values)
        if (permutationsCache.containsKey(key))
            return permutationsCache[key]!!

        if (size == 1) return values.map { intArrayOf(it) }.toTypedArray()

        val permutations = mutableListOf<IntArray>()
        for (value in values) {
            val smaller = permutations(size - 1, values)
            for (permutation in smaller) {
                permutations.add(intArrayOf(value) + permutation)
            }
        }
        
        permutationsCache[key] = permutations.toTypedArray()
        return permutations.toTypedArray()
    }
}