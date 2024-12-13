package io.nozemi.aoc.solutions.year2024.day10

import io.nozemi.aoc.types.puzzle.Puzzle
import io.nozemi.aoc.types.Vector2
import io.nozemi.aoc.types.datastructures.matrix.IntMatrix
import io.nozemi.aoc.types.datastructures.matrix.intMatrix

class HoofIt(input: String) : Puzzle<IntMatrix>(input) {

    override fun Sequence<String>.parse() =
        intMatrix(this)

    override fun solutions() = listOf(
        ::part1,
        ::part2
    )

    private fun part1() =
        parsedInput.findAll(0).sumOf {
            parsedInput.traverseFrom(it).flatten().filter { pos ->
                parsedInput.getAt(pos) == 9
            }.distinct().count()
        }

    private fun part2() =
        parsedInput.findAll(0).sumOf {
            parsedInput.traverseFrom(it).count()
        }

    private fun IntMatrix.traverseFrom(pos: Vector2): List<List<Vector2>> {
        val trails = mutableListOf<List<Vector2>>()

        fun traverseTrail(pos: Vector2, trail: List<Vector2> = emptyList()) {
            val currentHeight = this.getAt(pos)
                ?: Int.MIN_VALUE

            val currentTrail = trail.toMutableList()
            currentTrail.add(pos)

            val surrounding = this.adjacent(pos).map { it.first.coordinates }.map {
                Pair(it, this.getAt(it))
            }.filter { it.second != null && currentHeight + 1 == it.second }
                .map { it.first }

            surrounding.forEach {
                traverseTrail(it, currentTrail)
            }

            if (currentTrail.lastOrNull { this.getAt(it) == 9 } != null) {
                trails.add(currentTrail)
            }
        }

        traverseTrail(pos)

        return trails
    }
}