package io.nozemi.aoc.year2021.day02.impl

class Submarine(
    var depth: Long = 0L,
    var horizontal: Long = 0L,
    var aim: Long = 0L,
    var aimedMode: Boolean = false
) {

    fun moveSubmarine(commands: MutableList<String>) {
        commands.forEach {
            moveSubmarine(it)
        }
    }

    fun moveSubmarine(command: String) {
        val parsedCommand: Command = Command.parse(command)

        when (parsedCommand.direction) {
            Direction.FORWARD -> {
                if (this.aimedMode) {
                    this.depth += (parsedCommand.units * this.aim)
                }
                this.horizontal += parsedCommand.units
            }
            Direction.DOWN -> {
                if(this.aimedMode) {
                    this.aim += parsedCommand.units
                } else {
                    this.depth += parsedCommand.units
                }
            }
            Direction.UP -> {
                if(this.aimedMode) {
                    this.aim -= parsedCommand.units
                } else {
                    this.depth -= parsedCommand.units
                }
            }
        }
    }
}

