package io.nozemi.aoc.solutions.year2021.day06

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

class Day06(input: String) : Puzzle<List<Int>>(input) {

    override fun Sequence<String>.parse(): List<Int>
        = this.first().split(",").toList().map { it.trim().toInt() }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1
    )

    private fun part1(): Long {
        return getLanternFishForDuration(80)
    }

    /**
     * Gets the amount of lantern fish after the provided amount of days.
     */
    private fun getLanternFishForDuration(days: Int): Long {
        val fish = rawInput.toMutableList()
        for (day in 1 until days + 1) {
            // Each number represents a lanternfish and it's counter.
            // Every day we decrement the numbers by 1.
            // When a number reaches the count of 0:
            // - Reset it back to 6
            // - Add another lanternfish with counter of 8
            //print("Day ${day.toString().padStart(days.toString().length, ' ')}: ")
            for (f in 0 until fish.size) {
                //print("${fish[f]}")
                //if (f < fish.size - 1) print(", ")
                fish[f]--
            }
            for (f in 0 until fish.size) {
                if (fish[f] == -1) fish.add(8);
                if (fish[f] == -1) fish[f] = 6
            }
            //println()
        }
        return fish.size.toLong()
    }
}