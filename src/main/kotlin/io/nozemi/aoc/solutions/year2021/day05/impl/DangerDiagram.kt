package io.nozemi.aoc.solutions.year2021.day05.impl

import io.nozemi.aoc.puzzle.ANSI_BLUE
import io.nozemi.aoc.puzzle.ANSI_BOLD
import io.nozemi.aoc.puzzle.ANSI_RESET
import io.nozemi.aoc.utils.addIfNotExists
import java.nio.file.Files
import java.nio.file.Path

class DangerDiagram(segments: List<LineSegment>, draw: Boolean = false, private val drawOnlyDangers: Boolean = false) {

    private val coordinates: List<Coordinate>
    private val widestCoordinate: Coordinate
    private val grid: Array<IntArray>
    val dangerousCoordinates: MutableList<Coordinate> = mutableListOf()

    init {
        coordinates = segments.flatMap { it.coordinates }.toMutableList()
        widestCoordinate = findWidestCoordinate()
        grid = createGridAndFindDangers()
        if (draw) draw()
    }

    /**
     * Creates a visual representation of the danger diagram.
     */
    private fun draw() {
        val builder = StringBuilder()

        builder.append("$ANSI_BOLD$ANSI_BLUE[] ")
        for (i in 0 until grid[0].size) {
            builder.append("$i ")
        }
        builder.appendLine(ANSI_RESET)
        for ((line, y) in grid.withIndex()) {
            builder.append("$ANSI_BOLD$ANSI_BLUE${line}]$ANSI_RESET ")
            for (x in y) {
                when (drawOnlyDangers) {
                    true -> {
                        if (x < 2) builder.append(". ")
                        if (x >= 2) builder.append("$x ")
                    }
                    false -> {
                        if (x == 0) builder.append(". ")
                        if (x > 0) builder.append("$x ")
                    }
                }
            }
            builder.appendLine()
        }
        print(builder.toString())
        val outputFile = Path.of("./data/day05_cool.txt")
        if (Files.notExists(outputFile)) Files.createDirectories(outputFile.parent)
        Files.newBufferedWriter(outputFile).use {
            it.write(builder.toString())
        }
    }

    private fun findWidestCoordinate(): Coordinate {
        var largestX = coordinates[0].x
        var largestY = coordinates[0].y

        for (coordinate in coordinates) {
            if (coordinate.x > largestX) largestX = coordinate.x
            if (coordinate.y > largestY) largestY = coordinate.y
        }

        return Coordinate(largestX, largestY)
    }

    private fun createGridAndFindDangers(): Array<IntArray> {
        val grid = Array(widestCoordinate.y + 1) { IntArray(widestCoordinate.x + 1) }

        coordinates.forEach {
            grid[it.y][it.x]++
            if (grid[it.y][it.x] >= 2) {
                dangerousCoordinates.addIfNotExists(Coordinate(it.x, it.y))
            }
        }

        return grid
    }

    companion object {

        fun fromRawData(input: List<String>, draw: Boolean = false, drawOnlyDangers: Boolean = false, considerDiagonals: Boolean = false): DangerDiagram =
            DangerDiagram(input.map { LineSegment.parseStartToEnd(it, considerDiagonals) }, draw, drawOnlyDangers)
    }
}