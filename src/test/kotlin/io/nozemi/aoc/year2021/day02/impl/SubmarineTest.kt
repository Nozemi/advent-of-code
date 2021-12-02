package io.nozemi.aoc.year2021.day02.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubmarineTest {

    @Test
    fun moveSubmarineTest_NoAimMode() {
        val submarine = Submarine()
        submarine.moveSubmarine(mutableListOf(
            "forward 5",
            "down 5",
            "forward 8"
        ))
        assertEquals(13, submarine.horizontal)
        assertEquals(5, submarine.depth)
    }

    @Test
    fun moveSubmarineTest_AimMode() {
        val submarine = Submarine()
        submarine.moveSubmarine(mutableListOf(
            "forward 5",
            "down 5",
            "forward 8"
        ), aimed = true)
        assertEquals(13, submarine.horizontal)
        assertEquals(40, submarine.depth)
    }
}