package io.nozemi.aoc.solutions.year2024.day03

import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

fun main() {
    val input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    MullOverIt(input)
        .printAnswers()
}

class MullOverIt(input: String) : Puzzle<String>(input) {

    override fun Sequence<String>.parse() = this.joinToString()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        val pattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val matches = pattern.findAll(parsedInput).toList()

        var result = 0
        matches.forEach {
            val left = it.groups[1]?.value?.toIntOrNull()
            val right = it.groups[2]?.value?.toIntOrNull()

            if (left == null || right == null)
                return@forEach

            result += left * right
        }

        return result
    }

    private fun part2(): Int {
        val pattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|(do\\(\\))|(don't\\(\\))")
        val matches = pattern.findAll(parsedInput).toList()

        var enabled = true
        var result = 0
        matches.forEach {
            val match = it.groups[0]?.value

            if (match == "don't()")
                enabled = false
            else if (match == "do()")
                enabled = true

            if (!enabled)
                return@forEach

            val left = it.groups[1]?.value?.toIntOrNull()
            val right = it.groups[2]?.value?.toIntOrNull()

            if (left == null || right == null)
                return@forEach

            result += left * right
        }

        return result
    }
}