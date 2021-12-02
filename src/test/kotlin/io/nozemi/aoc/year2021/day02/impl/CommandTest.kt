package io.nozemi.aoc.year2021.day02.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CommandTest {

    @Test
    fun commandParseTest() {
        val command1 = Command.parse("forward 5")
        val command2 = Command.parse("down 5")
        assertEquals(5, command1.units)
        assertEquals(Direction.FORWARD, command1.direction)
        assertEquals(5, command2.units)
        assertEquals(Direction.DOWN, command2.direction)
    }
}