package io.nozemi.aoc

import io.nozemi.aoc.commandline.PuzzleSelectScreen
import java.time.MonthDay
import java.time.Year

val currentYear = Year.now().value
val currentDay = MonthDay.now().dayOfMonth

class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) = PuzzleSelectScreen().main(args)
    }
}