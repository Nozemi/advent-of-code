package io.nozemi.aoc

import io.nozemi.aoc.types.puzzle.PuzzleSelectScreen
import java.time.MonthDay
import java.time.Year

val currentYear = Year.now().value
val currentDay = MonthDay.now().dayOfMonth

fun main(args: Array<String>) {
    PuzzleSelectScreen().main(args)
}