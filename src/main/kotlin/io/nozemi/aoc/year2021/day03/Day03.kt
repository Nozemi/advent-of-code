package io.nozemi.aoc.year2021.day03

import io.nozemi.aoc.Puzzle
import io.nozemi.aoc.year2021.day03.impl.*
import java.util.stream.Stream

class Day03(year: Int, input: String) : Puzzle<List<String>>(year, input) {

    public override lateinit var parsedInput: List<String>

    override fun Stream<String>.parse(): List<String> = this.toStringList()

    enum class MeasureType {
        OXYGEN_GENERATOR_RATING,
        CO2_SCRUBBER_RATING
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
            if (position.first <= position.second) mostCommon[i] = 1
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

                val zeroCount = counts[count.key]?.first
                val oneCount = counts[count.key]?.second

                ratings = when (measureType) {
                    /**
                     * Oxygen Generator Rating Rules
                     * - Keep the ones with bit that has a majority in the current position.
                     * - If equal amount of 0s and 1s, keep the ones with 1s in the current position.
                     */
                    MeasureType.OXYGEN_GENERATOR_RATING -> {
                        if (zeroCount isGreaterThan oneCount) {
                            ratings.filter { it.toCharArray()[count.key].digitToInt() == 0 }
                        } else if (oneCount isGreaterThanOrEqual zeroCount) {
                            ratings.filter { it.toCharArray()[count.key].digitToInt() == 1 }
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
                        if (oneCount isLessThan zeroCount) {
                            ratings.filter { it.toCharArray()[count.key].digitToInt() == 1 }
                        } else if (zeroCount isLessThanOrEqual oneCount) {
                            ratings.filter { it.toCharArray()[count.key].digitToInt() == 0 }
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

    fun findPowerConsumption(input: List<String> = this.parsedInput): Long {
        val mostCommon = findMostCommonValuesForEachBit(input)
        val gammaRate = mostCommon.toGammaRateBinary().toInt(radix = 2)
        val epsilonRate = mostCommon.toEpsilonRateBinary().toInt(radix = 2)
        return (gammaRate * epsilonRate).toLong()
    }

    fun findLifeSupportRating(input: List<String> = this.parsedInput): Long {
        val oxygenGeneratorRating = findMeasureTypeRating(MeasureType.OXYGEN_GENERATOR_RATING, input).toInt(radix = 2)
        val co2ScrubberRatingFound = findMeasureTypeRating(MeasureType.CO2_SCRUBBER_RATING, input).toInt(radix = 2)
        return (oxygenGeneratorRating * co2ScrubberRatingFound).toLong()
    }

    override fun part1(): String {
        val powerConsumption = findPowerConsumption()
        return "$powerConsumption, the power consumption is $powerConsumption."
    }

    override fun part2(): String {
        val lifeSupportRating = findLifeSupportRating()
        return "$lifeSupportRating, the life support rating is $lifeSupportRating."
    }
}
