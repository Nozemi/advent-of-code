package io.nozemi.aoc.puzzles.year2020.day03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {
    private val input = """
        ..##.........##.........##.........##.........##.........##.......
        #...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
        .#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
        ..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
        .#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
        ..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....
        .#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
        .#........#.#........#.#........#.#........#.#........#.#........#
        #.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
        #...##....##...##....##...##....##...##....##...##....##...##....#
        .#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#
    """.trimIndent()
    private val day03 = Day03(input)

    @Test
    fun traverseMapTest() {
        val trees = day03.traverseMap(3, 1)
        assertEquals(7, trees)
    }

    @Test
    fun examplePart2Test() {
        val slope1 = day03.traverseMap(1, 1)
        val slope2 = day03.traverseMap(3, 1)
        val slope3 = day03.traverseMap(5, 1)
        val slope4 = day03.traverseMap(7, 1)
        val slope5 = day03.traverseMap(1, 2)
        assertEquals(336, (slope1 * slope2 * slope3 * slope4 * slope5))
    }
}