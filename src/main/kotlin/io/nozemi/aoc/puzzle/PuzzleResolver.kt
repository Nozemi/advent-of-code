package io.nozemi.aoc.puzzle

import io.github.classgraph.ClassGraph
import io.nozemi.aoc.puzzle.exceptions.HasAlreadyDownloadedException
import io.nozemi.aoc.puzzle.exceptions.InputFileDownloadFailedException
import io.nozemi.aoc.puzzle.exceptions.NoDataProvidedException
import io.nozemi.aoc.puzzle.exceptions.NoDownloadTokenProvidedException
import io.nozemi.aoc.utils.get
import java.lang.reflect.InvocationTargetException
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTime

typealias PuzzleMap = MutableMap<Int, MutableMap<Int, String>>

@OptIn(ExperimentalTime::class)
class PuzzleResolver {

    private val puzzleMap: PuzzleMap = mutableMapOf()

    @ExperimentalTime
    fun resolvePuzzles(): PuzzleResolver {
        val dayAndYearRegex = Regex("$basePackage\\.year([\\d]{4})\\.day([\\d]{2})")

        val duration = measureTime {
            ClassGraph().enableAllInfo().acceptPackages(basePackage).scan()
                .getSubclasses(Puzzle::class.java).forEach {
                    val result = dayAndYearRegex.find(it.packageName) ?: return@forEach
                    val year = result.groupValues[1].toInt()
                    val day = result.groupValues[2].toInt()
                    val yearsPuzzles = puzzleMap[year] ?: mutableMapOf()
                    yearsPuzzles[day] = it.name
                    puzzleMap[year] = yearsPuzzles
                }
        }

        println("Resolved ${puzzleMap.flatMap { it.value.values }.size} puzzles in $duration.")
        println()

        return this
    }

    fun hasPuzzlesForYear(year: Int): Boolean {
        return puzzleMap.containsKey(year)
    }

    @Throws(NoDataProvidedException::class)
    fun getPuzzle(year: Int, day: Int): Puzzle<*>? {
        val puzzleClass = puzzleMap[year][day] ?: return null
        val puzzleInstance: Puzzle<*>
        try {
            puzzleInstance = Class.forName(puzzleClass).getDeclaredConstructor(String::class.java).newInstance("") as Puzzle<*>
        } catch(exception: InvocationTargetException) {
            if (exception.cause?.message?.contains("No data provided") == true) {
                throw NoDataProvidedException()
            }

            if (exception.cause?.message?.contains("provided for downloading") == true) {
                throw NoDownloadTokenProvidedException()
            }

            if (exception.cause?.message?.contains("attempt to download has already") == true) {
                throw HasAlreadyDownloadedException()
            }

            if (exception.cause != null && exception.cause.toString().contains("InputFileDownloadFailedException")) {
                throw InputFileDownloadFailedException(exception.cause?.message ?: "Downloading input file failed.")
            }

            throw exception
        }

        return puzzleInstance
    }

    fun getAllPuzzles(): List<String> {
        return puzzleMap.flatMap { it.value.values }
    }

    fun getSolutionForPuzzle(year: Int, day: Int, part: Int? = null): List<TimedValue<Any>> {
        val instance = getPuzzle(year, day) ?: return emptyList()
        if (part == null) return listOf(
            instance.getAnswer(1),
            instance.getAnswer(2)
        )
        return listOf(instance.getAnswer(part))
    }
}