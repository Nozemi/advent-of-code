package io.nozemi.aoc.solutions.year2020.day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordPhilosophyTest {
    private val input = """
        1-3 a: abcde
        1-3 b: cdefg
        2-9 c: ccccccccc
    """.trimIndent()

    private val passwordPhilosophy = PasswordPhilosophy(input)

    @Test
    fun part1Test() {
        assertEquals(2, passwordPhilosophy.countValidPart1())
    }

    @Test
    fun part2Test() {
        assertEquals(1, passwordPhilosophy.countValidPart2())
    }
}