package io.nozemi.aoc.puzzles.year2020.day02

import io.nozemi.aoc.puzzles.Puzzle
import io.nozemi.aoc.puzzles.countChar
import java.util.regex.Pattern
import java.util.stream.Stream

fun String.parsePasswordAndPolicy(): Day02.PasswordAndPolicy? {
    val pattern = Pattern.compile("(\\d*)-(\\d*) (\\w): (.*)")
    val matcher = pattern.matcher(this)

    if (!matcher.matches()) {
        return null
    }

    return Day02.PasswordAndPolicy(
        minimum = matcher.group(1).toInt(),
        maximum = matcher.group(2).toInt(),
        character = matcher.group(3).toCharArray()[0],
        password = matcher.group(4)
    )
}



class Day02(input: String) : Puzzle<List<String>>(input) {

    public override lateinit var parsedInput: List<String>

    override fun Stream<String>.parse(): List<String> = this.toStringList()

    fun countValidPart1(): Int {
        var validPasswords = 0

        parsedInput.forEach {
            val passwordAndPolicy = it.parsePasswordAndPolicy()

            if (passwordAndPolicy != null) {
                val charOccurrences = passwordAndPolicy.password.countChar(passwordAndPolicy.character)
                if (charOccurrences >= passwordAndPolicy.minimum && charOccurrences <= passwordAndPolicy.maximum) validPasswords++
            }
        }

        return validPasswords
    }

    fun countValidPart2(): Int {
        var validPasswords = 0

        parsedInput.forEach {
            var passwordAndPolicy = it.parsePasswordAndPolicy()

            if (passwordAndPolicy != null) {
                val char1 = passwordAndPolicy.password[passwordAndPolicy.minimum - 1]
                val char2 = passwordAndPolicy.password[passwordAndPolicy.maximum - 1]
                if((char1 == passwordAndPolicy.character) xor (char2 == passwordAndPolicy.character)) validPasswords++
            }
        }

        return validPasswords
    }

    override fun part1(): String {
        val validPasswords = countValidPart1()
        return "$validPasswords, there are $validPasswords valid passwords."
    }

    override fun part2(): String {
        val validPasswords = countValidPart2()
        return "$validPasswords, there are $validPasswords valid passwords."
    }

    data class PasswordAndPolicy(
        val minimum: Int,
        val maximum: Int,
        val character: Char,
        val password: String
    )
}