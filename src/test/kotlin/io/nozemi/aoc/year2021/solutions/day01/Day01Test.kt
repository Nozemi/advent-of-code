package io.nozemi.aoc.year2021.solutions.day01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val day01 = Day01(2021, "")

    private val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent()

    @Test
    fun examplePart1() {
        day01.loadInput(input)
        assertEquals(7, day01.totalIncreases(1))
    }

    @Test
    fun examplePart2() {
        day01.loadInput(input)
        assertEquals(5, day01.totalIncreases(3))
    }
}