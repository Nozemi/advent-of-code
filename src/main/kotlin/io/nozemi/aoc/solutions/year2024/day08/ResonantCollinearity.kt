package io.nozemi.aoc.solutions.year2024.day08

import io.nozemi.aoc.puzzle.Puzzle
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

class ResonantCollinearity(input: String) : Puzzle<Array<CharArray>>(input) {

    override fun Sequence<String>.parse(): Array<CharArray> =
        this.toList()
            .map { it.toCharArray() }
            .toTypedArray()

    override fun solutions(): List<KFunction0<Any>> =
        listOf(
            ::part1
        )

    private fun part1(): Int {
        val rows = parsedInput.size
        val cols = parsedInput.first().size
        
        val antiNodes = mutableListOf<Coordinate>()
        
        val frequencies = parsedInput.map { 
            it.distinct()
        }.distinct()
            .flatten()
            .filter { !listOf('.', '#').contains(it) }
        
        val grid = parsedInput.copyOf()
        frequencies.forEach { frq -> 
            val antennas = parsedInput.findAll(frq)
            
            antennas.forEach a1@ { antenna1 ->
                antennas.forEach a2@ { antenna2 ->
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
                    
                    if (!(x1 < 0 || x1 >= cols || y1 < 0 || y1 >= rows)) {
                        antiNodes.add(Coordinate(x1, y1))
                    }
                    
                    if (!(x2 < 0 || x2 >= cols || y2 < 0 || y2 >= rows)) {
                        antiNodes.add(Coordinate(x2, y2))
                    }
                }
            }
        }
        
        antiNodes.forEach { 
            grid[it.y][it.x] = '#'
        }
        
        grid.forEach { row ->
            row.forEach { column ->
                print(column)
            }
            println()
        }
        
        return antiNodes.distinct().count()
    }

    private fun Array<CharArray>.findAll(char: Char): List<Coordinate> {
        val occurrences = mutableListOf<Coordinate>()
        forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (char == c)
                    occurrences.add(Coordinate(x, y))
            }
        }

        return occurrences.toList()
    }

    private data class Coordinate(val x: Int, val y: Int) {

        override fun toString(): String {
            return "Coordinate(x=${x + 1}, y=${y + 1})"
        }
    }
}