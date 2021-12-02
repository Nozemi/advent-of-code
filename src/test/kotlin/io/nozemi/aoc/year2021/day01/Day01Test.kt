package io.nozemi.aoc.year2021.day01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test {
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

    private val day01 = Day01(2021, input)

    @Test
    fun examplePart1Test() {
        assertEquals(7, day01.totalIncreases(1))
    }

    @Test
    fun examplePart2Test() {
        assertEquals(5, day01.totalIncreases(3))
    }
}