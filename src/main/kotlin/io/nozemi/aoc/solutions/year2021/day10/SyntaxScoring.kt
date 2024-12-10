package io.nozemi.aoc.solutions.year2021.day10

import io.nozemi.aoc.types.puzzle.ANSI_BOLD
import io.nozemi.aoc.types.puzzle.ANSI_RED
import io.nozemi.aoc.types.puzzle.ANSI_RESET
import io.nozemi.aoc.types.puzzle.Puzzle
import io.nozemi.aoc.utils.median
import kotlin.reflect.KFunction0

fun main() {
    SyntaxScoring("").printAnswers()
}

class SyntaxScoring(input: String) : Puzzle<List<String>>(input) {
    override fun Sequence<String>.parse(): List<String> = toList()

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        return getScore(ScoreType.ERROR)
    }

    private fun part2(): Long {
        return getScore(ScoreType.AUTO_COMPLETE)
    }

    fun getScore(type: ScoreType): Long {
        var errorScore = 0L
        val autoCompleteLineScores = mutableListOf<Long>()
        var currentLineAutoScore = 0L
        parsedInput.forEach {
            it.syntaxChecker(errorOccurred = { bracket ->
                when (bracket) {
                    ')' -> errorScore += 3
                    ']' -> errorScore += 57
                    '}' -> errorScore += 1197
                    '>' -> errorScore += 25137
                }
            }, missingClosingBrackets = { stacks ->
                stacks.filter { stack -> !stack.second }.forEach { stack ->
                    currentLineAutoScore *= 5
                    when (stack.first.toClosing()) {
                        ')' -> currentLineAutoScore += 1
                        ']' -> currentLineAutoScore += 2
                        '}' -> currentLineAutoScore += 3
                        '>' -> currentLineAutoScore += 4
                    }
                }
                autoCompleteLineScores.add(currentLineAutoScore)
                currentLineAutoScore = 0
            })
        }

        return if (type == ScoreType.ERROR) {
            errorScore
        } else {
            autoCompleteLineScores.median()
        }
    }

    enum class ScoreType {
        ERROR,
        AUTO_COMPLETE
    }

    private val openingSymbols = listOf('[', '{', '(', '<')
    private val closingSymbols = listOf(']', '}', ')', '>')
    private fun String.syntaxChecker(
        errorOccurred: (char: Char) -> Unit,
        missingClosingBrackets: (stacks: ArrayDeque<Pair<Char, Boolean>>) -> Unit
    ) {
        val stacks = ArrayDeque<Pair<Char, Boolean>>(0)
        this.forEachIndexed { index, it ->
            when (it) {
                in openingSymbols -> stacks.addFirst(Pair(it, false))
                in closingSymbols -> {
                    val lastNotClosedIndex = stacks.lastNotClosed() ?: return@forEachIndexed
                    val lastNotClosed = lastNotClosedIndex.second
                    if (lastNotClosed.first.toClosing() != it) return errorOccurred(it)
                    else stacks[lastNotClosedIndex.first] = Pair(it, true)
                }
            }
        }

        missingClosingBrackets(stacks)
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