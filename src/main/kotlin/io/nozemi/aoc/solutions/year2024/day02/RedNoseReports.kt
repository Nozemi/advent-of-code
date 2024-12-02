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

class RedNoseReports(input: String) : Puzzle<List<List<Int>>>(input) {

    private val maxLevelDiff = 3

    override fun Sequence<String>.parse() = this.map { report ->
        report.trim().split(" ")
            .mapNotNull { level ->
                level.toIntOrNull()
            }
    }.toList()


    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        var safeReports = 0
        parsedInput.forEach reportLoop@ { report ->
            val increasing = report[0] < report[1]

            report.forEachIndexed { index, level ->
                val next = report.getOrNull(index + 1)
                if (next != null && ((increasing && next <= level) || (!increasing && next >= level)))
                    return@reportLoop
            }

            report.forEachIndexed { index, level ->
                val left = report.getOrNull(index - 1)
                val right = report.getOrNull(index + 1)

                if (increasing) {
                    if (left != null && level - left > maxLevelDiff)
                        return@reportLoop

                    if (right != null && right - level > maxLevelDiff)
                        return@reportLoop
                } else {
                    if (left != null && left - level > maxLevelDiff)
                        return@reportLoop

                    if (right != null && level - right > maxLevelDiff)
                        return@reportLoop
                }
            }

            safeReports++
        }

        return safeReports
    }
    private fun part2(): Int = 0
}