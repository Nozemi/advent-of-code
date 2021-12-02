package io.nozemi.aoc.year2021.day02

import io.nozemi.aoc.Puzzle

fun Day02.Submarine.moveSubmarine(input: MutableList<String>) {
    input.forEach {
        val command = it.split(" ")
        val direction = Day02.Direction.valueOf(command[0].uppercase())
        val units = command[1].toInt()

        when(direction) {
            Day02.Direction.FORWARD -> this.horizontal += units
            Day02.Direction.DOWN -> this.depth += units
            Day02.Direction.UP -> this.depth -= units
        }
    }
}

fun Day02.Submarine.moveSubmarineAimed(input: MutableList<String>) {
    input.forEach {
        val command = it.split(" ")
        val direction = Day02.Direction.valueOf(command[0].uppercase())
        val units = command[1].toInt()

        when(direction) {
            Day02.Direction.FORWARD -> {
                this.horizontal += units
                this.depth += (units * this.aim)
            }
            Day02.Direction.DOWN -> this.aim += units
            Day02.Direction.UP -> this.aim -= units
        }
    }
}

class Day02(year: Int, input: String) : Puzzle<MutableList<String>>(year, input) {

    public override lateinit var solutionInput: MutableList<String>

    override fun loadInput(input: String) {
        solutionInput = input.split("\n").mapTo(mutableListOf()) { it.trim() }
    }

    override fun part1(): String {
        val submarine = Submarine()
        submarine.moveSubmarine(solutionInput)
        return "${submarine.horizontal * submarine.depth} (Submarine(horizontal=${submarine.horizontal}, vertical=${submarine.depth}))"
    }

    override fun part2(): String {
        val submarine = Submarine()
        submarine.moveSubmarineAimed(solutionInput)
        return "${submarine.horizontal * submarine.depth} (Submarine(horizontal=${submarine.horizontal}, vertical=${submarine.depth}))"
    }

    data class Submarine(
        var depth: Long = 0L,
        var horizontal: Long = 0L,
        var aim: Long = 0L
    )

    enum class Direction {
        FORWARD,
        UP,
        DOWN
    }
}