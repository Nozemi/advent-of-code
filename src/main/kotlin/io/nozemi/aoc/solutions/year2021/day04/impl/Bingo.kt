package io.nozemi.aoc.solutions.year2021.day04.impl

import io.nozemi.aoc.solutions.transposeMatrix

class Bingo(private val numbers: IntArray, private val boards: MutableList<Board>) {
    private val drawnNumbers = mutableListOf<Int>()

    fun findWinningBoards(): List<Board> {
        val winners: MutableList<Board> = mutableListOf()

        for (i in numbers) {
            drawnNumbers.add(i)

            if (drawnNumbers.size >= 5) {
                val potentialBoards = boards.filter { it.hasWinningNumbers(drawnNumbers) }.map {
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
                if (currentBoard.lines().size >= 5) {
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

    fun findUnmarkedNumbers(): List<Int> {
        val unmarkedNumbers = mutableListOf<Int>()

        values.map { row ->
            row.filter { cell -> !numbersWhenWon.contains(cell) }.toIntArray()
        }.forEach { row ->
            row.forEach { cell -> unmarkedNumbers.add(cell) }
        }

        return unmarkedNumbers
    }

    private fun getWinningNumbers(numbers: List<Int>): IntArray {
        if (numbers.size < 5) return intArrayOf()

        return values.firstOrNull { numbers.containsAll(it.asList()) }
            ?: values.transposeMatrix().firstOrNull { numbers.containsAll(it.asList()) }
            ?: intArrayOf()
    }

    fun hasWinningNumbers(numbers: List<Int>): Boolean {
        return getWinningNumbers(numbers).isNotEmpty()
    }

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