package io.nozemi.aoc.year2020.day01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class Day01Test {
    private val day01 = Day01(2020, "")

    private val input = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent()

    @Test
    fun examplePart1Test() {
        day01.loadInput(input)
        val result = day01.findTwoNumbersThatAmountsTo(2020, day01.solutionInput)
        assertNotNull(result)
        assertEquals(514579, (result.first * result.second))
    }

    @Test
    fun examplePart2Test() {
        day01.loadInput(input)
        val result = day01.findThreeNumbersThatAmountsTo(2020, day01.solutionInput)
        assertEquals(3, result.size)
        assertEquals(241861950, (result[0] * result[1] * result[2]))
    }
}