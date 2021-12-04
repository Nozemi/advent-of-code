package io.nozemi.aoc.puzzles.year2021.day04.impl

import io.nozemi.aoc.puzzles.year2021.day03.impl.isGreaterThanOrEqual

class Bingo(private val numbers: IntArray, private val boards: MutableList<Board>) {
    val drawnNumbers = mutableListOf<Int>()

    fun addBoard(board: Board) {
        boards.add(board)
    }

    fun findWinningBoards(): List<Board> {
        for (i in numbers.indices) {
            drawnNumbers.add(numbers[i])

            if (drawnNumbers.size isGreaterThanOrEqual 5) {
                val potentialBoards = boards.filter { b -> b.values.any { r -> drawnNumbers.containsAll(r.asList()) } }
                if (potentialBoards.isNotEmpty()) return potentialBoards
            }
        }

        return emptyList()
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

    fun findUnmarkedNumbers(bingo: Bingo): IntArray {
        val unmarkedNumbers = mutableListOf<Int>()
        values.map { row -> row.filter { number -> !bingo.drawnNumbers.contains(number) }.toIntArray() }
            .forEach { row ->
                row.forEach { number ->
                    unmarkedNumbers.add(number)
                }
            }
        return unmarkedNumbers.toIntArray()
    }

    fun findMarkedNumbers(bingo: Bingo): IntArray {
        val unmarkedNumbers = mutableListOf<Int>()
        values.map { row -> row.filter { number -> bingo.drawnNumbers.contains(number) }.toIntArray() }
            .forEach { row ->
                row.forEach { number ->
                    unmarkedNumbers.add(number)
                }
            }
        return unmarkedNumbers.toIntArray()
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