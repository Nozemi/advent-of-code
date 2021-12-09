package io.nozemi.aoc.solutions.year2021.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LanternfishTest {
    private val input = """
        3,4,3,1,2
    """.trimIndent()

    private val lanternfish = Lanternfish(input)

    @Test
    fun part1Test() {
        assertEquals(5934, lanternfish.getLanternFishForDuration(80))
    }

    @Test
    fun part2Test() {
        assertEquals(26984457539, lanternfish.getLanternFishForDuration(256))
    }
}