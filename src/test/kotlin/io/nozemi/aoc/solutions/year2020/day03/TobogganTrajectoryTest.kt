package io.nozemi.aoc.solutions.year2020.day03

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TobogganTrajectoryTest {
    private val input = """
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent()

    private val tobogganTrajectory = TobogganTrajectory(input)

    @Test
    fun part1Test() {
        assertEquals(7, tobogganTrajectory.traverseMap(3, 1))
    }

    @Test
    fun part2Test() {
        val slope1 = tobogganTrajectory.traverseMap(1, 1)
        val slope2 = tobogganTrajectory.traverseMap(3, 1)
        val slope3 = tobogganTrajectory.traverseMap(5, 1)
        val slope4 = tobogganTrajectory.traverseMap(7, 1)
        val slope5 = tobogganTrajectory.traverseMap(1, 2)
        assertEquals(336, slope1 * slope2 * slope3 * slope4 * slope5)
    }
}