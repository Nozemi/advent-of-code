package io.nozemi.aoc.puzzles.year2021.day03

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.puzzles.year2021.day03.impl.toEpsilonRateBinary
import io.nozemi.aoc.puzzles.year2021.day03.impl.toGammaRateBinary
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private val logger = InlineLogger()

internal class Day03Test {
    private val input = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent()
    private val day03 = Day03(2021, input)

    @Test
    fun findPowerConsumptionTest() {
        val powerConsumption = day03.findPowerConsumption()
        assertEquals(198, powerConsumption)
    }

    @Test
    fun findOxygenGeneratorRatingTest() {
        val oxygenGeneratorRating = day03.findMeasureTypeRating(Day03.MeasureType.OXYGEN_GENERATOR_RATING, day03.parsedInput)
        assertEquals("10111", oxygenGeneratorRating)
        assertEquals(23, oxygenGeneratorRating.toInt(radix = 2))
    }

    @Test
    fun findCO2ScrubberRatingTest() {
        val co2ScrubberRating = day03.findMeasureTypeRating(Day03.MeasureType.CO2_SCRUBBER_RATING, day03.parsedInput)
        assertEquals("01010", co2ScrubberRating)
        assertEquals(10, co2ScrubberRating.toInt(radix = 2))
    }

    @Test
    fun findMostCommonValuesForEachBitTest() {
        val mostCommon = day03.findMostCommonValuesForEachBit()
        assertEquals(5, mostCommon.size)
        assertEquals(1, mostCommon[0])
        assertEquals(0, mostCommon[1])
        assertEquals(1, mostCommon[2])
        assertEquals(1, mostCommon[3])
        assertEquals(0, mostCommon[4])
    }

    @Test
    fun findMostCommonValuesForEachBit_InOxygenGeneratorRatingModeTest() {
        val mostCommon = day03.findMostCommonValuesForEachBit(input = listOf(
            "00100",
            "00100",
            "00100",
            "10100",
            "10100",
            "10100"
        ))
        assertEquals(1, mostCommon[0])
    }

    fun findMostCommonValuesForEachBit_InCO2ScrubberRatingModeTest() {
        val mostCommon = day03.findMostCommonValuesForEachBit(input = listOf(
            "00100",
            "00100",
            "00100",
            "10100",
            "10100",
            "10100"
        ))
        assertEquals(0, mostCommon[0])
    }

    @Test
    fun examplePart1Test() {
        val mostCommon = day03.findMostCommonValuesForEachBit()
        val gammaRateBinary = mostCommon.toGammaRateBinary()
        assertEquals("10110", gammaRateBinary)
        val gammaRateDecimal = gammaRateBinary.toInt(radix = 2)
        assertEquals(22, gammaRateDecimal)
        val epsilonRate = mostCommon.toEpsilonRateBinary()
        assertEquals("01001", epsilonRate)
        val powerConsumption = day03.findPowerConsumption()
        assertEquals(198, powerConsumption)
    }

    @Test
    fun examplePart2Test() {
        val oxygenGeneratorRating = day03.findMeasureTypeRating(Day03.MeasureType.OXYGEN_GENERATOR_RATING)
        assertEquals("10111", oxygenGeneratorRating)
        val co2ScrubberRating = day03.findMeasureTypeRating(Day03.MeasureType.CO2_SCRUBBER_RATING)
        assertEquals("01010", co2ScrubberRating)
        val lifeSupportRating = day03.findLifeSupportRating()
        assertEquals(230, lifeSupportRating)
    }
}