package io.nozemi.aoc.solutions.year2024.day10

import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.types.Coordinates
import io.nozemi.aoc.types.IntMatrix
import io.nozemi.aoc.types.intMatrix

class HoofIt(input: String) : Puzzle<IntMatrix>(input) {

    override fun Sequence<String>.parse() =
        intMatrix(this)

    override fun solutions() = listOf(
        ::part1
    )

    private fun part1(): Int {
        val trails = mutableListOf<List<Coordinates>>()

        fun traverseTrail(pos: Coordinates, trail: List<Coordinates> = emptyList()) {
            val currentHeight = parsedInput.getAt(pos)
                ?: Int.MIN_VALUE

            val currentTrail = trail.toMutableList()
            currentTrail.add(pos)

            val surrounding = parsedInput.surrounding(pos).map {
                Pair(it, parsedInput.getAt(it))
            }.filter { it.second != null && currentHeight + 1 == it.second }
                .map { it.first }

            surrounding.forEach {
                traverseTrail(it, currentTrail)
            }

            if (currentTrail.lastOrNull { parsedInput.getAt(it) == 9 } != null) {
                trails.add(currentTrail)
            }
        }
        
        var score = 0
        parsedInput.findAll(0).forEach { 
            trails.clear()
            traverseTrail(it)

            score += trails.flatten().filter { pos -> parsedInput.getAt(pos) == 9 }.distinct().count()
        }

        return score
    }
}