package io.nozemi.aoc.year2020.day02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {
    private val input = """
        1-3 a: abcde
        1-3 b: cdefg
        2-9 c: ccccccccc
    """.trimIndent()

    private val day02 = Day02(2020, input)

    @Test
    fun parsePasswordAndPolicyTest() {
        val passwordAndPolicy = "1-3 a: abcde".parsePasswordAndPolicy()
        assertNotNull(passwordAndPolicy)
        assertEquals(passwordAndPolicy?.password, "abcde")
        assertEquals(passwordAndPolicy?.minimum, 1)
        assertEquals(passwordAndPolicy?.maximum, 3)
        assertEquals(passwordAndPolicy?.character, 'a')
    }

    @Test
    fun countCharTest() {
        assertEquals(2, "bbvfjhafuyha".countChar('a'))
        assertEquals(5, "bbbbb".countChar('b'))
        assertEquals(5, "bbasdbbweb".countChar('b'))
        assertNotEquals(3, "abcaabaa".countChar('a'))
    }

    @Test
    fun examplePart1Test() {
        assertEquals(2, day02.countValidPart1())
    }

    @Test
    fun examplePart2Test() {
        assertEquals(1, day02.countValidPart2())
    }
}