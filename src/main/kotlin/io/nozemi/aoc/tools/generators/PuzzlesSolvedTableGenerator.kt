package io.nozemi.aoc.tools.generators

import io.nozemi.aoc.puzzle.InputLoader
import io.nozemi.aoc.puzzle.Puzzle
import io.nozemi.aoc.puzzle.PuzzleResolver
import io.nozemi.aoc.puzzle.dayOfYearRegex
import java.nio.file.Files
import java.nio.file.Path
import kotlin.time.ExperimentalTime

typealias SolvedPuzzles = MutableMap<Int, MutableMap<Int, Pair<Boolean, Boolean>>>

/**
 * This will generate the table found in the [README.md](/README.md) that shows status of solved puzzles.
 */
@OptIn(ExperimentalTime::class)
class PuzzlesSolvedTableGenerator {

    private var solvedPuzzles: SolvedPuzzles = mutableMapOf()

    private val puzzleResolver = PuzzleResolver().resolvePuzzles()

    private var progressTable: ProgressTable? = null

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val generator = PuzzlesSolvedTableGenerator().generate()
            println(generator.progressTable?.getGeneratedString())
        }
    }

    fun generate(): PuzzlesSolvedTableGenerator {
        populateSolvedPuzzles()
        solvedPuzzles = solvedPuzzles.toSortedMap(compareByDescending { it })
        progressTable = ProgressTable(solvedPuzzles)
        return this
    }

    /**
     * Generated table will be injected into the provided string. As long as it contains **{{AUTOGENERATED_SOLVED_PUZZLES_TABLE}}**.
     *
     * @return Returns the string with the table injected.
     */
    fun injectGeneratedTable(input: String): String {
        if (progressTable == null) return input.replace("{{AUTOGENERATED_SOLVED_PUZZLES_TABLE}}", "Unable to generate.")
        return input.replace("{{AUTOGENERATED_SOLVED_PUZZLES_TABLE}}", progressTable!!.getGeneratedString())
    }

    @OptIn(ExperimentalTime::class)
    private fun testPuzzle(
        className: String, year: Int, day: Int, useActualData: Boolean = false
    ): Pair<Boolean, Boolean> {
        val inputLoader = getInputLoader(year, day, useActualData)
            ?: return if (useActualData) Pair(true, true) else Pair(false, false)

        val input = inputLoader.inputData.joinToString("\n")
        val rawAnswers = input.lines()[0]

        val answersRegex = Regex("\\[([\\d]+)]\\[([\\d]+)]")
        val matches = answersRegex.find(rawAnswers) ?: return Pair(false, false)

        val expectedAnswer1 = matches.groupValues[1]
        val expectedAnswer2 = matches.groupValues[2]

        val instance = Class.forName(className).getDeclaredConstructor(String::class.java)
            .newInstance(input.replace("$rawAnswers\n", "")) as Puzzle<*>
        val answer1 = instance.getAnswer(1).value.toString()
        val answer2 = instance.getAnswer(2).value.toString()

        val prefix = ""
        println("$prefix ${instance::class.simpleName} (Part 1): expected: $expectedAnswer1, actual: $answer1")
        println("$prefix ${instance::class.simpleName} (Part 2): expected: $expectedAnswer2, actual: $answer2")

        return Pair(answer1 == expectedAnswer1, answer2 == expectedAnswer2)
    }

    private fun populateSolvedPuzzles() {
        puzzleResolver.getAllPuzzles().forEach {
            val dayAndYear = dayOfYearRegex.find(it) ?: return@forEach
            val year = dayAndYear.groupValues[1].toInt()
            val day = dayAndYear.groupValues[3].toInt()

            val exampleDataResult = testPuzzle(it, year, day)
            val actualDataResult = testPuzzle(it, year, day, true)

            var status = mutableMapOf<Int, Pair<Boolean, Boolean>>()
            if (solvedPuzzles.containsKey(year)) status = solvedPuzzles[year]!!
            status[day] = Pair(
                exampleDataResult.first && actualDataResult.first,
                exampleDataResult.second && actualDataResult.second
            )
            solvedPuzzles[year] = status
        }
    }

    private fun getInputLoader(year: Int, day: Int, useActualData: Boolean = false): InputLoader? {
        val fileName = "day" + day.toString().padStart(2, '0') + if (useActualData) "-actual.txt" else ".txt"
        val inputFile = Path.of("./data/example-inputs/$year/$fileName")
        if (Files.notExists(inputFile)) return null
        return InputLoader(inputFile, year, day, shouldAttemptDownload = false)
    }
}