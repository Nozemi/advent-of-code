package io.nozemi.aoc.year2021.day02.impl

class Submarine(
    var depth: Long = 0L,
    var horizontal: Long = 0L,
    var aim: Long = 0L,
    var aimedMode: Boolean = false
) {
    fun moveSubmarine(rawCommands: List<String>) {
        rawCommands.forEach { moveSubmarine(it.toCommand()) }
    }

    fun moveSubmarine(rawCommand: String) {
        moveSubmarine(rawCommand.toCommand())
    }

    fun moveSubmarine(commands: Iterable<Command>) {
        commands.forEach { moveSubmarine(it) }
    }

    fun moveSubmarine(command: Command) {
        when (command.direction) {
            Direction.FORWARD -> {
                if (aimedMode) {
                    depth += (command.units * aim)
                }
                horizontal += command.units
            }
            Direction.DOWN -> if (aimedMode) aim += command.units else depth += command.units
            Direction.UP -> if (aimedMode) aim -= command.units else depth -= command.units
        }
    }
}

