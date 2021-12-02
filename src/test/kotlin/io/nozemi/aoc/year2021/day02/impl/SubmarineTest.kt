package io.nozemi.aoc.year2021.day02.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubmarineTest {

    @Test
    fun moveSubmarineTest_NoAimMode() {
        val submarine = Submarine()
        submarine.moveSubmarine("forward 5")
        assertEquals(5, submarine.horizontal)
        submarine.moveSubmarine("down 5")
        assertEquals(5, submarine.horizontal)
        assertEquals(5, submarine.depth)
        submarine.moveSubmarine("forward 8")
        assertEquals(13, submarine.horizontal)
        assertEquals(5, submarine.depth)
    }

    @Test
    fun moveSubmarineTest_AimMode() {
        val submarine = Submarine(aimedMode = true)
        submarine.moveSubmarine("forward 5")
        assertEquals(5, submarine.horizontal)
        submarine.moveSubmarine("down 5")
        assertEquals(5, submarine.horizontal)
        assertEquals(5, submarine.aim)
        submarine.moveSubmarine("forward 8")
        assertEquals(13, submarine.horizontal)
        assertEquals(5, submarine.aim)
        assertEquals(40, submarine.depth)
    }
}