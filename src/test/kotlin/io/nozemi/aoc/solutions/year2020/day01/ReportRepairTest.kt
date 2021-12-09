package io.nozemi.aoc.solutions.year2020.day01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ReportRepairTest {
    private val input = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent()

    private val reportRepair = ReportRepair(input)

    @Test
    fun part1Test() {
        val result = reportRepair.findTwoNumbersThatAmountsTo(2020, reportRepair.parsedInput)
        assertNotNull(result)
        assertEquals(Pair(1721, 299), result)
        assertEquals(514579, result!!.first * result.second)
    }

    @Test
    fun part2Test() {
        val result = reportRepair.findThreeNumbersThatAmountsTo(2020, reportRepair.parsedInput)
        assertArrayEquals(intArrayOf(979, 366, 675), result)
        assertEquals(241861950, result[0] * result[1] * result[2])
    }
}