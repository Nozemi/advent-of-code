package io.nozemi.aoc.solutions.year2020.day04

import io.nozemi.aoc.types.puzzle.Puzzle
import io.nozemi.aoc.solutions.year2020.day04.impl.Height
import io.nozemi.aoc.solutions.year2020.day04.impl.Passport
import io.nozemi.aoc.utils.toPath
import java.nio.file.Files
import kotlin.reflect.KFunction0

fun main() {
    PassportProcessing(Files.readString("./data/inputs/2020/day04.txt".toPath()))
        .printAnswers()
}

class PassportProcessing(input: String) : Puzzle<List<Passport>>(input) {

    override fun Sequence<String>.parse(): List<Passport> {
        val rawPassports = mutableListOf<Passport>()
        var rawPassportBuilder = StringBuilder()
        this.forEach { line ->
            if (line.isBlank()) {
                rawPassports.add(rawPassportBuilder.toString().toPassport())
                rawPassportBuilder = StringBuilder()
            }
            rawPassportBuilder.append(" $line")
        }
        rawPassports.add(rawPassportBuilder.toString().toPassport())

        return rawPassports
    }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return parsedInput.count { it.isValid(strictMode = false) }
    }

    private fun part2(): Int {
        return parsedInput.count { it.isValid(strictMode = true) }
    }

    private fun String.toPassport(strictMode: Boolean = true): Passport {
        val properties = split(" ")

        var birthYear: Int? = null
        var issueYear: Int? = null
        var expirationYear: Int? = null
        var height = Height(-1, "")
        var hairColor: String? = null
        var eyeColor: String? = null
        var passportId: String? = null
        var countryId: String? = null

        properties.forEach {
            if (it.startsWith("byr:")) birthYear = it.split(":")[1].toInt()
            if (it.startsWith("iyr:")) issueYear = it.split(":")[1].toInt()
            if (it.startsWith("eyr:")) expirationYear = it.split(":")[1].toInt()
            if (it.startsWith("hgt:")) height = it.toHeight(strictMode)
            if (it.startsWith("hcl:")) hairColor = it.split(":")[1]
            if (it.startsWith("ecl:")) eyeColor = it.split(":")[1]
            if (it.startsWith("pid:")) passportId = it.split(":")[1]
            if (it.startsWith("cid:")) countryId = it.split(":")[1]
        }

        return Passport(birthYear, issueYear, expirationYear, height, hairColor, eyeColor, passportId, countryId)
    }

    private fun String.toHeight(strictMode: Boolean = true): Height {
        val value = split(":")[1]

        if (!strictMode) return Height(-1, value)

        val regex = Regex("([\\d]+)([\\w]+)")
        val result = regex.find(value)?.groupValues ?: return Height(-1, "")

        return Height(result[1].toInt(), result[2])
    }
}