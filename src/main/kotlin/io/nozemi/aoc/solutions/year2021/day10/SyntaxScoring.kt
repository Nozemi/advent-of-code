package io.nozemi.aoc.solutions.year2021.day10

import io.nozemi.aoc.puzzle.ANSI_BOLD
import io.nozemi.aoc.puzzle.ANSI_RED
import io.nozemi.aoc.puzzle.ANSI_RESET
import io.nozemi.aoc.puzzle.Puzzle
import kotlin.reflect.KFunction0

fun main() {
    SyntaxScoring("").printAnswers()
}

class SyntaxScoring(input: String) : Puzzle<List<String>>(input) {
    override fun Sequence<String>.parse(): List<String> = toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1
    )

    private fun part1(): Int {
        return getSyntaxErrorScore()
    }

    fun getSyntaxErrorScore(): Int {
        var score = 0
        parsedInput.forEach {
            val result = checkSyntax(it)
            if (result != null) {
                when (result) {
                    ')' -> score += 3
                    ']' -> score += 57
                    '}' -> score += 1197
                    '>' -> score += 25137
                }
            }
        }
        return score
    }

    private val openingSymbols = listOf('[', '{', '(', '<')
    private val closingSymbols = listOf(']', '}', ')', '>')
    private fun checkSyntax(input: String): Char? {
        val stacks = ArrayDeque<Pair<Char, Boolean>>(0)
        input.forEachIndexed { index, it ->
            when(it) {
                in openingSymbols -> stacks.addFirst(Pair(it, false))
                in closingSymbols -> {
                    val lastNotClosedIndex = stacks.lastNotClosed() ?: return@forEachIndexed
                    val lastNotClosed = lastNotClosedIndex.second
                    if (lastNotClosed.first.toClosing() != it) {
                        println("Error! Expected ${lastNotClosed.first.toClosing()}, but found $it (col=$index) on line -> ${input.highlight(index)}")
                        return it
                    }
                    if (lastNotClosed.first.toClosing() == it) stacks[lastNotClosedIndex.first] = Pair(it, true)
                }
            }
        }

        return null
    }

    private fun String.highlight(column: Int, color: String = ANSI_RED): String {
        val char = this[column]
        val part1 = this.substring(0 until column)
        val part2 = this.substring((column - 1) until this.length)
        return buildString {
            append(part1)
            append(color)
            append(ANSI_BOLD)
            append(char)
            append(ANSI_RESET)
            append(part2)
        }
    }

    private fun Char.toClosing(): Char {
        return when (this) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            else -> throw RuntimeException("Wtf are you doing!? You shouldn't be able to get this!")
        }
    }

    private fun MutableList<Pair<Char, Boolean>>.setLast(value: Pair<Char, Boolean>) {
        this[this.size - 1] = value
    }

    private fun ArrayDeque<Pair<Char, Boolean>>.lastNotClosed(): Pair<Int, Pair<Char, Boolean>>? {
        for ((index, pair) in withIndex()) {
            if (!pair.second) return Pair(index, pair)
        }

        return null
    }
}