package io.nozemi.aoc.year2021.day02

import io.nozemi.aoc.year2021.day02.impl.Submarine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {
    private val day02 = Day02(2021, "")
    private val input = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent()

    @Test
    fun examplePart1Test() {
        day02.loadInput(input)
        val submarine = Submarine()
        submarine.moveSubmarine(day02.solutionInput)
        assertEquals(15, submarine.horizontal)
        assertEquals(10, submarine.depth)
    }

    @Test
    fun examplePart2Test() {
        day02.loadInput(input)
        val submarine = Submarine(aimedMode = true)
        submarine.moveSubmarine(day02.solutionInput)
        assertEquals(15, submarine.horizontal)
        assertEquals(60, submarine.depth)
        assertEquals(900, (submarine.horizontal * submarine.depth))
    }
}