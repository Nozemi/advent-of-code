package io.nozemi.aoc.solutions.year2020.day04

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2020.day04.impl.Passport
import io.nozemi.aoc.solutions.year2020.day04.impl.parseToPassport
import kotlin.reflect.KFunction0

class Day04(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    val rawPassports = mutableListOf<String>()
    var passports = mutableListOf<Passport>()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    init {
        readRawPassportData()
    }

    private fun part1(): Int {
        parsePassports(strictMode = false)
        var validCount = 0
        passports.forEach { if (it.isValid()) validCount++ }
        return validCount
    }

    private fun part2(): Int {
        parsePassports(strictMode = true)
        var validCount = 0
        passports.forEach { if (it.isValid()) validCount++ }
        return validCount
    }

    fun parsePassports(strictMode: Boolean = false) {
        passports = mutableListOf()
        rawPassports.forEach {
            val passport = it.parseToPassport(strictMode)
            passports.add(passport)
        }
    }

    private fun readRawPassportData() {
        var builder = StringBuilder()

        for (i in rawInput.indices) {
            if (rawInput[i].isBlank()) {
                rawPassports.add(builder.toString().trim())
                builder = StringBuilder()
            }
            builder.append(" ${rawInput[i]}")
            if (i == (rawInput.size - 1)) rawPassports.add(builder.toString().trim())
        }
    }
}