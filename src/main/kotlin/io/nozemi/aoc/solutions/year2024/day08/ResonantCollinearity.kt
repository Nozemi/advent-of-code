package io.nozemi.aoc.solutions.year2024.day08

import io.nozemi.aoc.types.puzzle.Puzzle
import io.nozemi.aoc.types.Coordinates
import io.nozemi.aoc.types.matrix.CharMatrix
import io.nozemi.aoc.types.matrix.charMatrix
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KFunction0

fun main() {
    ResonantCollinearity(
        """
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
        """.trimIndent()
    ).printAnswers()
}

class ResonantCollinearity(input: String) : Puzzle<CharMatrix>(input) {

    override fun Sequence<String>.parse() = charMatrix(this)

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        val antiNodes = mutableListOf<Coordinates>()

        val frequencies = parsedInput.distinctValues
            .filter { !listOf('.', '#').contains(it) }

        frequencies.forEach { frq ->
            val antennas = parsedInput.findAll(frq)

            antennas.forEach a1@{ antenna1 ->
                antennas.forEach a2@{ antenna2 ->
                    if (antenna1 == antenna2)
                        return@a2

                    val distanceX = max(antenna1.x, antenna2.x) - min(antenna1.x, antenna2.x)
                    val distanceY = max(antenna1.y, antenna2.y) - min(antenna1.y, antenna2.y)

                    val x1: Int
                    val y1: Int
                    val x2: Int
                    val y2: Int

                    if (antenna1.y < antenna2.y) {
                        y1 = antenna1.y - distanceY
                        y2 = antenna2.y + distanceY
                    } else {
                        y1 = antenna1.y + distanceY
                        y2 = antenna2.y - distanceY
                    }

                    if (antenna1.x < antenna2.x) {
                        x1 = antenna1.x - distanceX
                        x2 = antenna2.x + distanceX
                    } else {
                        x1 = antenna1.x + distanceX
                        x2 = antenna2.x - distanceX
                    }

                    if (!(x1 < 0 || x1 >= parsedInput.cols || y1 < 0 || y1 >= parsedInput.rows)) {
                        antiNodes.add(Coordinates(x1, y1))
                    }

                    if (!(x2 < 0 || x2 >= parsedInput.cols || y2 < 0 || y2 >= parsedInput.rows)) {
                        antiNodes.add(Coordinates(x2, y2))
                    }
                }
            }
        }

        return antiNodes.distinct().count()
    }

    private fun part2(): Int {
        val antiNodes = mutableListOf<Coordinates>()

        val frequencies = parsedInput.distinctValues
            .filter { !listOf('.', '#').contains(it) }

        frequencies.forEach { frq ->
            val antennas = parsedInput.findAll(frq)

            antennas.forEach a1@{ antenna1 ->
                antiNodes.add(antenna1)

                antennas.forEach a2@{ antenna2 ->
                    if (antenna1 == antenna2)
                        return@a2

                    val distanceX = max(antenna1.x, antenna2.x) - min(antenna1.x, antenna2.x)
                    val distanceY = max(antenna1.y, antenna2.y) - min(antenna1.y, antenna2.y)

                    val frqNodes = mutableListOf<Coordinates>()

                    var currX1: Int? = null
                    var currY1: Int? = null
                    var currX2: Int? = null
                    var currY2: Int? = null

                    while ((currX1 in 0..<parsedInput.cols || currX2 in 0..<parsedInput.cols || currY1 in 0..<parsedInput.rows || currY2 in 0..<parsedInput.rows)
                        || listOf(currX1, currX2, currY1, currY2).any { it == null }
                    ) {
                        if (antenna1.y < antenna2.y) {
                            currY1 = (currY1 ?: antenna1.y) - distanceY
                            currY2 = (currY2 ?: antenna2.y) + distanceY
                        } else {
                            currY1 = (currY1 ?: antenna1.y) + distanceY
                            currY2 = (currY2 ?: antenna2.y) - distanceY
                        }

                        if (antenna1.x < antenna2.x) {
                            currX1 = (currX1 ?: antenna1.x) - distanceX
                            currX2 = (currX2 ?: antenna2.x) + distanceX
                        } else {
                            currX1 = (currX1 ?: antenna1.x) + distanceX
                            currX2 = (currX2 ?: antenna2.x) - distanceX
                        }

                        frqNodes.addAll(listOf(Coordinates(currX1, currY1), Coordinates(currX2, currY2)))
                    }

                    antiNodes.addAll(frqNodes.filter {
                        it.x in 0..<parsedInput.cols && it.y in 0..<parsedInput.rows
                    })
                }
            }
        }

        return antiNodes.distinct().count()
    }
}