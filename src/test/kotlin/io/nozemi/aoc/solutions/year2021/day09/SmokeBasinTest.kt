package io.nozemi.aoc.solutions.year2021.day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SmokeBasinTest {
    private val input = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent()

    private val smokeBasin = SmokeBasin(input)

    @Test
    fun part1Test() {
        assertEquals(15, smokeBasin.getLowPoints().sumOf { it + 1 })
    }

    @Test
    fun part2Test() {
        val basins = smokeBasin.findBasins().toMutableList()

        assertTrue(basins.size > 3)

        val basinSizes = basins.map { it.size }.toMutableList()

        val threeLargestBasins = IntArray(3) { -1 }
        repeat(3) { iteration ->
            val max = basinSizes.maxOrNull() ?: throw RuntimeException("You fucked up... Should never really come to this!?")
            threeLargestBasins[iteration] = max; basinSizes.remove(max)
        }

        assertEquals(1134, threeLargestBasins[0] * threeLargestBasins[1] * threeLargestBasins[2])
    }
}