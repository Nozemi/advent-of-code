package io.nozemi.aoc.solutions.year2021.day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TheTreacheryOfWhalesTest {
    private val input = """
        16,1,2,0,4,2,7,1,2,14
    """.trimIndent()

    private val theTreacheryOfWhales = TheTreacheryOfWhales(input)

    @Test
    fun part1Test() {
        assertEquals(37, theTreacheryOfWhales.findFuelConsumption())
    }

    @Test
    fun part2Test() {
        assertEquals(168, theTreacheryOfWhales.findFuelConsumption(constantBurnRate = false))
    }
}