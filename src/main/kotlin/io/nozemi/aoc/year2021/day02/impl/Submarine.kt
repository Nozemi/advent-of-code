package io.nozemi.aoc.year2021.day02.impl

class Submarine(
    var depth: Long = 0L,
    var horizontal: Long = 0L,
    private var aim: Long = 0L
) {

    fun moveSubmarine(input: MutableList<String>, aimed: Boolean = false) {
        input.forEach {
            val command: Command = Command.parse(it)
            command.setAimed(aimed)

            when (command.direction) {
                Direction.FORWARD -> {
                    if (command.aimed) {
                        this.depth += (command.units * this.aim)
                    }
                    this.horizontal += command.units
                }
                Direction.DOWN -> {
                    if(command.aimed) {
                        this.aim += command.units
                    } else {
                        this.depth += command.units
                    }
                }
                Direction.UP -> {
                    if(command.aimed) {
                        this.aim -= command.units
                    } else {
                        this.depth -= command.units
                    }
                }
            }
        }
    }
}

