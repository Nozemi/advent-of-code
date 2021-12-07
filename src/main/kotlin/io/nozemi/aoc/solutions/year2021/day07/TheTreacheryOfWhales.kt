package io.nozemi.aoc.solutions.year2021.day07

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.math.abs
import kotlin.reflect.KFunction0

class TheTreacheryOfWhales(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int> = this.toList()[0].split(",").map { it.trim().toInt() }

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

    private fun findFuelConsumption(input: List<Int> = rawInput, constantBurnRate: Boolean = true): Int {
        var targetPosition = input.minOrNull() ?: 0
        val maxTargetPosition = input.maxOrNull() ?: 0

        var cheapestOutcome = Int.MAX_VALUE
        repeat(maxTargetPosition) {
            val fuelConsumption = if (constantBurnRate) {
                input.sumOf { abs(it - targetPosition) }
            } else {
                input.sumOf {
                    val distance = abs(it - targetPosition)
                    distance * (distance + 1) / 2
                }
            }
            if (fuelConsumption < cheapestOutcome) cheapestOutcome = fuelConsumption
            targetPosition++
        }

        return cheapestOutcome
    }
}