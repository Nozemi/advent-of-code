package io.nozemi.aoc.solutions.year2021.day07

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.math.abs
import kotlin.reflect.KFunction0

class TheTreacheryOfWhales(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int> = this.toList().single()
        .split(",").map { it.trim().toInt() }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return findFuelConsumption()
    }

    private fun part2(): Int {
        return findFuelConsumption(constantBurnRate = false)
    }

    fun findFuelConsumption(input: List<Int> = parsedInput, constantBurnRate: Boolean = true): Int =
        input.range.minOf { currentPosition ->
            input.sumOf {
                val distance = abs(it - currentPosition)
                if (!constantBurnRate) distance * (distance + 1) / 2 else distance
            }
        }
}

private val List<Int>.range get() = minOrNull()!!..maxOrNull()!!