package io.nozemi.aoc.solutions.year2020.day05

import io.nozemi.aoc.types.puzzle.Puzzle
import kotlin.reflect.KFunction0

class BinaryBoarding(input: String) : Puzzle<List<String>>(input) {

    override fun Sequence<String>.parse(): List<String> = toList()

    override fun solutions(): List<KFunction0<Any>> = listOf()
}