package io.nozemi.aoc.solutions.year2021.day01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class SonarSweepTest {
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
    private val sonarSweep = SonarSweep(input, unitTest = true)

    @Test
    fun part1Test() {
        val answer = sonarSweep.getAnswer(1).value
        assertEquals(7, answer)
    }

    @Test
    fun part2Test() {
        val answer = sonarSweep.getAnswer(2).value
        assertEquals(5, answer)
    }
}