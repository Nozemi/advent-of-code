package io.nozemi.aoc.solutions.year2021.day02.impl

fun String.toCommand(): Command {
    return Command.parse(this)
}

class Command(
    val units: Int = 0,
    val direction: Direction = Direction.FORWARD
) {

    companion object {
        @JvmStatic
        fun parse(input: String): Command {
            val raw = input.split(" ")
            return Command(
                units = raw[1].toInt(),
                direction = Direction.valueOf(raw[0].uppercase())
            )
        }
    }
}

enum class Direction {
    FORWARD,
    UP,
    DOWN
}