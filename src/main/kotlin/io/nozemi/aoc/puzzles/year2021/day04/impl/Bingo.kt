package io.nozemi.aoc.puzzles.year2021.day04.impl

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.puzzles.year2021.day03.impl.isGreaterThanOrEqual
import io.nozemi.aoc.puzzles.year2021.day03.impl.isLessThan

private val logger = InlineLogger()

fun Array<IntArray>.transposeMatrix() = Array(this[0].size) { i -> IntArray(this.size) { j -> this[j][i] } }

class Bingo(private val numbers: IntArray, private val boards: MutableList<Board>) {
    private val drawnNumbers = mutableListOf<Int>()

    //fun findWinningBoards(): List<Board> {
    //    var competingBoards: List<Board> = mutableListOf(*boards.toTypedArray())

    //    val winners = mutableListOf<Board>()
    //    for (i in numbers) {
    //        drawnNumbers.add(i)

    //        competingBoards = competingBoards.filter { it.getWinningNumbers(drawnNumbers).isEmpty() }

    //        winners.addAll(
    //            competingBoards.filter {
    //                it.getWinningNumbers(drawnNumbers).isNotEmpty()
    //            }.map {
    //                //logger.error { "Board below won with these numbers: ${drawnNumbers.toList()}." }
    //                //logger.error { it }
    //                it.numbersWhenWon = drawnNumbers.toIntArray()
    //                it
    //            }
    //        )
    //    }

    //    return winners
    //}

    fun findWinningBoards(): List<Board> {
        val winners: MutableList<Board> = mutableListOf()

        for (i in numbers.indices) {
            drawnNumbers.add(numbers[i])

            if (drawnNumbers.size isGreaterThanOrEqual 5) {
                val potentialBoards = boards.filter { board ->
                    (board.values.any { row -> drawnNumbers.containsAll(row.asList()) } || board.values.transposeMatrix().any { row -> drawnNumbers.containsAll(row.asList()) })
                }.map {
                    it.numbersWhenWon = drawnNumbers.toIntArray()
                    it
                }

                winners.addAll(potentialBoards)
                boards.removeAll(potentialBoards)
            }
        }

        return winners
    }

    companion object {

        fun parse(raw: String): Bingo {
            val firstLine = raw.lines()[0]
            val boards = raw.substring(firstLine.length, raw.length)

            val parsedBoards = mutableListOf<Board>()
            var currentBoard = StringBuilder()
            boards.lines().forEach {
                if (it.isBlank()) return@forEach
                currentBoard.append(it)
                if (currentBoard.lines().size isGreaterThanOrEqual 5) {
                    val parsedBoard = Board.parse(currentBoard.toString())
                    if (parsedBoard != null) parsedBoards.add(parsedBoard)
                    currentBoard = StringBuilder()
                } else {
                    currentBoard.append("\n")
                }
            }

            return Bingo(firstLine.split(",").map { it.trim().toInt() }.toIntArray(), parsedBoards)
        }
    }
}

class Board(val values: Array<IntArray>) {
    var numbersWhenWon = intArrayOf()
    var winningNumbers = intArrayOf()

    fun findUnmarkedNumbers(): List<Int> {
        val unmarkedNumbers = mutableListOf<Int>()

        values.map { row ->
            row.filter { cell -> !numbersWhenWon.contains(cell) }.toIntArray()
        }.forEach { row ->
            row.forEach { cell -> unmarkedNumbers.add(cell) }
        }

        return unmarkedNumbers
    }

    //fun getWinningNumbers(numbers: List<Int>): IntArray {
    //    if (numbers.size isLessThan 5) return intArrayOf()

    //    return this.values.firstOrNull { numbers.containsAll(it.asList()) }
    //        ?: this.values.transposeMatrix().firstOrNull { numbers.containsAll(it.asList()) }
    //        ?: intArrayOf()
    //}

    fun getWinningNumbers(numbers: List<Int>): IntArray {
        if (numbers.size isLessThan 5) return intArrayOf()

        val result = this.values.firstOrNull { numbers.containsAll(it.asList()) }
            ?: this.values.transposeMatrix().firstOrNull { numbers.containsAll(it.asList()) }
            ?: intArrayOf()

        if (result.isNotEmpty()) {
            logger.error { "Board's winning numbers: ${result.toList()}" }
            logger.error { "Drawn Numbers: $numbers" }
            logger.error { "Looking at board below:" }
            logger.error { this }
            logger.error { "==========================" }
            logger.error { " " }
            logger.error { " " }
        }

        return result
    }

    fun hasWinningNumbers(numbers: List<Int>): Boolean {
        return getWinningNumbers(numbers).isNotEmpty()
    }

    //private fun Array<IntArray>.transposeMatrix(): Array<IntArray> {
    //    val transposedMatrix = Array(this[0].size) { IntArray(this.size) }
    //    for (i in 0 until this.size) {
    //        for (j in 0 until this[0].size) {
    //            transposedMatrix[j][i] = this[i][j]
    //        }
    //    }
    //    return transposedMatrix
    //}

    fun calculateScore(): Long {
        val unmarkedSum = this.findUnmarkedNumbers().sum().toLong()
        val lastDrawnNumber = this.numbersWhenWon.last()
        return unmarkedSum * lastDrawnNumber
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (row in values) {
            for (column in row) {
                builder.append("${column.toString().padStart(2, ' ')}    ")
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    companion object {
        private val regex = Regex(" {0,2}(([\\d]+) {1,2}([\\d]+) {1,2}([\\d]+) {1,2}([\\d]+) {1,2}([\\d]+))")

        private fun String.toBoardLine(): IntArray {
            val matches = regex.matchEntire(this) ?: return intArrayOf()
            val result = matches.groupValues
            return result.subList(result.size - 5, result.size).map { it.toInt() }.toIntArray()
        }

        fun parse(raw: String): Board? {
            if (raw.isBlank()) return null
            return Board(raw.lines().map { line -> line.toBoardLine() }.toTypedArray())
        }
    }
}