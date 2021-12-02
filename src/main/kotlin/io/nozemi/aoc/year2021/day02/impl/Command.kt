package io.nozemi.aoc.year2021.day02.impl

class Command(
    val units: Int = 0,
    val direction: Direction = Direction.FORWARD,
    var aimed: Boolean = false
) {

    fun setAimed(aimed: Boolean): Command {
        this.aimed = aimed
        return this
    }

    companion object {
        @JvmStatic
        fun parse(input: String): Command {
            val raw = input.split(" ")
            return Command(
                units = raw[1].toInt(),
                direction = Direction.valueOf(raw[0].uppercase()),
                aimed = false
            )
        }
    }
}

enum class Direction {
    FORWARD,
    UP,
    DOWN
}