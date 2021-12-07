package io.nozemi.aoc.solutions.year2020.day02

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.utils.countChar
import java.util.regex.Pattern
import kotlin.reflect.KFunction0

class PasswordPhilosophy(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = this.toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        return countValidPart1()
    }

    private fun part2(): Int {
        return countValidPart2()
    }

    fun countValidPart1(): Int {
        var validPasswords = 0

        rawInput.forEach {
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

        rawInput.forEach {
            var passwordAndPolicy = it.parsePasswordAndPolicy()

            if (passwordAndPolicy != null) {
                val char1 = passwordAndPolicy.password[passwordAndPolicy.minimum - 1]
                val char2 = passwordAndPolicy.password[passwordAndPolicy.maximum - 1]
                if((char1 == passwordAndPolicy.character) xor (char2 == passwordAndPolicy.character)) validPasswords++
            }
        }

        return validPasswords
    }

    data class PasswordAndPolicy(
        val minimum: Int,
        val maximum: Int,
        val character: Char,
        val password: String
    )
}

fun String.parsePasswordAndPolicy(): PasswordPhilosophy.PasswordAndPolicy? {
    val pattern = Pattern.compile("(\\d*)-(\\d*) (\\w): (.*)")
    val matcher = pattern.matcher(this)

    if (!matcher.matches()) {
        return null
    }

    return PasswordPhilosophy.PasswordAndPolicy(
        minimum = matcher.group(1).toInt(),
        maximum = matcher.group(2).toInt(),
        character = matcher.group(3).toCharArray()[0],
        password = matcher.group(4)
    )
}