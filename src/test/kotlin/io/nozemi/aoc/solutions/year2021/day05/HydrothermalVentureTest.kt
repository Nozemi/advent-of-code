package io.nozemi.aoc.solutions.year2021.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HydrothermalVentureTest {
    private val input = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent()

    private val hydrothermalVenture = HydrothermalVenture(input)

    @Test
    fun part1Test() {
        assertEquals(5, hydrothermalVenture.solve())
    }

    @Test
    fun part2Test() {
        assertEquals(12, hydrothermalVenture.solve(considerDiagonals = true))
    }
}