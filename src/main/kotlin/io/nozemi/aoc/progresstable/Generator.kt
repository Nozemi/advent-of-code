package io.nozemi.aoc.progresstable

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import io.nozemi.aoc.commandline.basePackage
import io.nozemi.aoc.commandline.dayOfYearRegex
import io.nozemi.aoc.puzzle.InputLoader
import io.nozemi.aoc.puzzle.Puzzle
import java.nio.file.Files
import java.nio.file.Path
import kotlin.time.ExperimentalTime

typealias SolvedPuzzles = MutableMap<Int, MutableMap<Int, Pair<Boolean, Boolean>>>

/**
 * This will generate the table found in the README.md that shows status of solved puzzles.
 */
class Generator {

    // year [ day [ part1, part2 ] ]
    private var solvedPuzzles: SolvedPuzzles = mutableMapOf()

    init {
        generate()
    }

    private fun generate() {
        populateSolvedPuzzles()
        print("")
        solvedPuzzles = solvedPuzzles.toSortedMap(compareByDescending { it })
        val progressTable = ProgressTable(solvedPuzzles)
        print(progressTable.getGeneratedString())
    }

    private fun populateSolvedPuzzles() {
        ClassGraph().enableAllInfo()
            .acceptPackages(basePackage)
            .scan()
            .getSubclasses(Puzzle::class.java).forEach {
                val dayAndYear = dayOfYearRegex.find(it.packageName) ?: return@forEach
                val year = dayAndYear.groupValues[1].toInt()
                val day = dayAndYear.groupValues[3].toInt()

                val exampleDataResult = testPuzzle(it, year)
                val actualDataResult = testPuzzle(it, year, true)

                var status = mutableMapOf<Int, Pair<Boolean, Boolean>>()
                if (solvedPuzzles.containsKey(year)) status = solvedPuzzles[year]!!
                status[day] = Pair(
                    exampleDataResult.first && actualDataResult.first,
                    exampleDataResult.second && actualDataResult.second
                )
                solvedPuzzles[year] = status
            }
    }

    @OptIn(ExperimentalTime::class)
    private fun testPuzzle(classInfo: ClassInfo, year: Int, useActualData: Boolean = false): Pair<Boolean, Boolean> {
        val inputLoader = getInputLoader(classInfo, year, useActualData)
            ?: return if (useActualData) Pair(true, true) else Pair(false, false)

        val input = inputLoader.loadFromFile()!!.joinToString("\n")
        val rawAnswers = input.lines()[0]

        val answersRegex = Regex("\\[([\\d]+)]\\[([\\d]+)]")
        val matches = answersRegex.find(rawAnswers) ?: return Pair(false, false)

        val expectedAnswer1 = matches.groupValues[1]
        val expectedAnswer2 = matches.groupValues[2]

        val instance = classInfo.loadClass().getDeclaredConstructor(String::class.java).newInstance(input.replace("$rawAnswers\n", "")) as Puzzle<*>
        val answer1 = instance.getAnswer(0).value.toString()
        val answer2 = instance.getAnswer(1).value.toString()

        return Pair(answer1 == expectedAnswer1, answer2 == expectedAnswer2)
    }

    private fun getInputLoader(classInfo: ClassInfo, year: Int, useActualData: Boolean = false): InputLoader? {
        val fileName = classInfo.simpleName.lowercase() + if (useActualData) "-actual.txt" else ".txt"
        val inputFile = Path.of("./data/example-inputs/$year/$fileName")
        if (Files.notExists(inputFile)) return null
        return InputLoader(inputFile)
    }

    private fun drawTable() {
        val columns = solvedPuzzles.keys.sortedDescending()
        val rows = 25

        val builder = StringBuilder()
        builder.append("| Days  |")
        columns.forEach {
            builder.append(" $it[$it]   |")
        }
        builder.append("\n|-------|")
        repeat(columns.size) {
            builder.append("--------------|")
        }
        for (i in 1 until rows + 1) {
            builder.append("\n| ${i.toString().padStart(2, ' ')}    | ❌           | ❌           |")
        }

        print(builder.toString())
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Generator()
        }
    }
}