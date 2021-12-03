package io.nozemi.aoc.puzzles.year2020.day04

import io.nozemi.aoc.puzzles.Puzzle
import io.nozemi.aoc.puzzles.year2020.day04.impl.Passport
import io.nozemi.aoc.puzzles.year2020.day04.impl.parseToPassport
import java.util.stream.Stream

class Day04(year: Int, input: String) : Puzzle<List<String>>(year, input) {

    override lateinit var parsedInput: List<String>

    override fun Stream<String>.parse(): List<String> = this.toStringList()

    val rawPassports = mutableListOf<String>()
    var passports = mutableListOf<Passport>()

    private fun readRawPassportData() {
        var builder = StringBuilder()

        for (i in parsedInput.indices) {
            if (parsedInput[i].isBlank()) {
                rawPassports.add(builder.toString().trim())
                builder = StringBuilder()
            }
            builder.append(" ${parsedInput[i]}")
            if (i == (parsedInput.size - 1)) rawPassports.add(builder.toString().trim())
        }
    }

    fun parsePassports(strictMode: Boolean = false) {
        passports = mutableListOf()
        rawPassports.forEach {
            val passport = it.parseToPassport(strictMode)
            passports.add(passport)
            logger.debug { passport.toString() }
        }
        logger.debug { "=======================================================" }
    }

    init {
        readRawPassportData()
    }

    override fun part1(): String {
        parsePassports(strictMode = false)

        var validCount = 0
        passports.forEach { if (it.isValid()) validCount++ }
        return "$validCount, by the current rules, we have $validCount/${passports.size} valid passports."
    }

    override fun part2(): String {
        parsePassports(strictMode = true)

        var validCount = 0
        passports.forEach { if (it.isValid()) validCount++ }
        return "$validCount, by the current rules, we have $validCount/${passports.size} valid passports."
    }
}