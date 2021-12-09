package io.nozemi.aoc.solutions.year2021.day02

import io.nozemi.aoc.solutions.year2021.day02.impl.Submarine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DiveTest {
    private val input = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent()
    private val dive = Dive(input)

    @Test
    fun part1Test() {
        val submarine = Submarine()
        submarine.moveSubmarine(rawCommands = dive.parsedInput)
        assertEquals(150, submarine.horizontal * submarine.depth)
    }

    @Test
    fun part2Test() {
        val submarine = Submarine(aimedMode = true)
        submarine.moveSubmarine(rawCommands = dive.parsedInput)
        assertEquals(900, submarine.horizontal * submarine.depth)
    }
}