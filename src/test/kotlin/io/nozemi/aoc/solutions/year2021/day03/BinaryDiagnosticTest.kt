package io.nozemi.aoc.solutions.year2021.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BinaryDiagnosticTest {
    private val input = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent()

    private val binaryDiagnostic = BinaryDiagnostic(input)

    @Test
    fun part1Test() {
        assertEquals(198, binaryDiagnostic.findPowerConsumption())
    }

    @Test
    fun part2Test() {
        assertEquals(230, binaryDiagnostic.findLifeSupportRating())
    }
}