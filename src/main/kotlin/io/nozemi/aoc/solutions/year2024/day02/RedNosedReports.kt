package io.nozemi.aoc.solutions.year2024.day02

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

fun main() {
    RedNoseReports(
        """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
        """.trimIndent()
    ).printAnswers()
}

class RedNoseReports(input: String) : Puzzle<List<RedNosedReport>>(input) {

    private val maxLevelDiff = 3

    override fun Sequence<String>.parse() = this.map { report ->
        RedNosedReport(
            report.trim()
                .split(" ")
                .mapNotNull { level -> level.toIntOrNull() }
                .toMutableList()
        )
    }.toList()


    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1() = parsedInput.count {
        it.isValid(maxLevelDiff, false)
    }

    private fun part2() = parsedInput.count {
        it.isValid(maxLevelDiff, true)
    }

/*
    private fun part2(): Int {
        println("")
        println("")
        val safeReports = parsedInput.filter { it.isValid(maxLevelDiff, true) }
        safeReports.forEach {
            println(it.levels)
        }

        return safeReports.size
    }*/
}