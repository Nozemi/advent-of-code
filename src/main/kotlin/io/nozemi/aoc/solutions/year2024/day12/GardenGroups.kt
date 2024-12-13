package io.nozemi.aoc.solutions.year2024.day12

import io.nozemi.aoc.types.Direction
import io.nozemi.aoc.types.datastructures.distjoint.ForestDisjointSet
import io.nozemi.aoc.types.Vector2
import io.nozemi.aoc.types.datastructures.matrix.CharMatrix
import io.nozemi.aoc.types.datastructures.matrix.charMatrix
import io.nozemi.aoc.types.puzzle.Puzzle

fun main() {
    GardenGroups(
        """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent()
    ).printAnswers()
}

class GardenGroups(input: String) : Puzzle<CharMatrix>(input) {

    override fun Sequence<String>.parse() = charMatrix(this)

    override fun solutions() = listOf(
        ::part1,
        ::part2
    )

    private fun part1() = parsedInput.allGroups
        .map { (crop, groups) ->
            groups.sumOf {
                val perimeter = parsedInput.perimeter(crop, it).size
                val area = it.size

                area * perimeter
            }
        }.sum()

    private fun part2() = parsedInput.allGroups
        .map { (_, groups) ->
            groups.sumOf {
                val sides = parsedInput.countSides(it)
                val area = it.size

                area * sides
            }
        }.sum()

    private fun CharMatrix.perimeter(crop: Char, area: List<Vector2>) =
        area.map { cell ->
            this.adjacent(cell, allowOutOfBounds = true)
                .filter { parsedInput.getAt(it.first.coordinates) != crop }
        }.flatten()

    private fun CharMatrix.countSides(area: List<Vector2>): Int {
        val sides = ForestDisjointSet<Pair<Vector2, Direction>>()

        for (pos in area) {
            val type = this.getAt(pos)

            sides.addAll(
                adjacent(pos, filter = {
                    it.first.value != type
                }, allowOutOfBounds = true)
                    .map { Pair(pos, it.second) }
            )
        }

        for (pos in area) {
            for (direction in Direction.orthogonal) {
                val partition1 = sides[Pair(pos, direction)] ?: continue
                val partition2 = sides[Pair(pos + Vector2(direction.y, -direction.x), direction)] ?: continue

                sides.union(partition1, partition2)
            }
        }

        return sides.partitions
    }
}
