package io.nozemi.aoc.solutions.year2021.day03

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2021.day03.impl.countBitsInPosition
import io.nozemi.aoc.solutions.year2021.day03.impl.toEpsilonRateBinary
import io.nozemi.aoc.solutions.year2021.day03.impl.toGammaRateBinary
import kotlin.reflect.KFunction0

class BinaryDiagnostic(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        return findPowerConsumption()
    }

    private fun part2(): Long {
        return findLifeSupportRating()
    }

    fun findPowerConsumption(input: List<String> = this.parsedInput): Long {
        val mostCommon = findMostCommonValuesForEachBit(input)
        val gammaRate = mostCommon.toGammaRateBinary().toLong(radix = 2)
        val epsilonRate = mostCommon.toEpsilonRateBinary().toLong(radix = 2)
        return (gammaRate * epsilonRate)
    }

    fun findLifeSupportRating(input: List<String> = this.parsedInput): Long {
        val oxygenGeneratorRating = findMeasureTypeRating(MeasureType.OXYGEN_GENERATOR_RATING, input).toInt(radix = 2)
        val co2ScrubberRatingFound = findMeasureTypeRating(MeasureType.CO2_SCRUBBER_RATING, input).toInt(radix = 2)
        return (oxygenGeneratorRating * co2ScrubberRatingFound).toLong()
    }

    /**
     * Return a map of bit's position and which was most common for that position in the current List<String>.
     */
    fun findMostCommonValuesForEachBit(
        input: List<String> = this.parsedInput
    ): Map<Int, Int> {
        // Map<Position, Most Common Bit>
        val mostCommon = mutableMapOf<Int, Int>()

        val counts = input.countBitsInPosition()
        for (i in 0 until counts.size) {
            val position = counts[i] ?: Pair(0, 0)
            if (position.first >= position.second) mostCommon[i] = 0
            if (position.second >= position.first) mostCommon[i] = 1
        }

        return mostCommon
    }

    fun findMeasureTypeRating(measureType: MeasureType, input: List<String> = this.parsedInput): String {
        var ratings = input

        var counts = ratings.countBitsInPosition()
        while (ratings.size > 1) {
            counts.forEach { count ->
                counts = ratings.countBitsInPosition()

                if (ratings.size <= 1) return@forEach

                val zeroCount = counts[count.key]?.first ?: 0
                val oneCount = counts[count.key]?.second ?: 0

                ratings = when (measureType) {
                    /**
                     * Oxygen Generator Rating Rules
                     * - Keep the ones with bit that has a majority in the current position.
                     * - If equal amount of 0s and 1s, keep the ones with 1s in the current position.
                     */
                    MeasureType.OXYGEN_GENERATOR_RATING -> {
                        if (zeroCount > oneCount) {
                            ratings.filter { it.toCharArray()[count.key] == '0' }
                        } else if (oneCount >= zeroCount) {
                            ratings.filter { it.toCharArray()[count.key] == '1' }
                        } else {
                            ratings
                        }
                    }
                    /**
                     * CO2 Scrubber Rating Rules
                     * - Keep the ones with bit that has a minority in the current position.
                     * - If equal amount of 0s and 1s, keep the ones with 0s in the current position.
                     */
                    MeasureType.CO2_SCRUBBER_RATING -> {
                        if (oneCount < zeroCount) {
                            ratings.filter { it.toCharArray()[count.key] == '1' }
                        } else if (zeroCount <= oneCount) {
                            ratings.filter { it.toCharArray()[count.key] == '0' }
                        } else {
                            ratings
                        }
                    }
                }
            }
        }

        if (ratings.size != 1) return "00000"

        return ratings.first()
    }


    enum class MeasureType {
        OXYGEN_GENERATOR_RATING,
        CO2_SCRUBBER_RATING
    }
}