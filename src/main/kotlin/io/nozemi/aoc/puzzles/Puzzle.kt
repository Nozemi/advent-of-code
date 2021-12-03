package io.nozemi.aoc.puzzles

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.commandline.ANSI_BLUE
import io.nozemi.aoc.commandline.ANSI_RED
import io.nozemi.aoc.commandline.ANSI_RESET
import io.nozemi.aoc.commandline.token
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream


private val regex = Regex("io.nozemi.aoc.puzzles.year([\\d]{4}).(day0([\\d])|day([\\d]{2}))")

abstract class Puzzle<T : Any>(year: Int, private var input: String) {

    private var downloaded = false
    private val result = regex.find(this.javaClass.packageName)

    private val puzzleName = this.javaClass.simpleName

    val year: Int
        get() {
            if (result == null) return 0
            return result.groupValues[1].toInt()
        }

    val day: Int
        get() {
            if (result == null) return 0
            return result.groupValues[3].toInt()
        }

    protected val logger = InlineLogger()

    private val inputFilePath = Path.of("./data/inputs/${this.year}/${puzzleName.lowercase()}.txt")

    protected abstract var parsedInput: T

    init {
        prepare()
    }

    private fun prepare() {
        val data = loadInput() ?: return
        parsedInput = data.parse()
    }

    fun execute() {
        val partResults = arrayListOf(part1(), part2())
        for (i in 0 until partResults.size) {
            logger.info { "$ANSI_BLUE[Part ${i + 1}]$ANSI_RESET: ${partResults[i]}" }
        }
    }

    private fun loadInput(): Stream<String>? {
        logger.debug { "Loading solution data..." }

        var data: Stream<String>? = null

        if (input.isNotBlank()) {
            logger.debug { "Loading input directly..." }
            data = input.lines().stream()
        } else if (Files.exists(inputFilePath)) {
            logger.debug { "Loading input from file..." }
            data = Files.lines(inputFilePath)
        } else if (Files.notExists(inputFilePath) && token != null && !downloaded) {
            downloaded = true
            logger.debug { "Downloading input from Advent of Code..." }
            val url = URL("https://adventofcode.com/${this.year}/day/${this.day}/input")
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("Cookie", "session=$token")
                if (connection.responseCode == 200) {
                    if (Files.notExists(inputFilePath)) Files.createDirectories(inputFilePath.parent)
                    Files.newBufferedWriter(inputFilePath).use {
                        it.write(connection.inputStream.bufferedReader().readText())
                    }
                    return loadInput()
                }
            } catch (ignored: IOException) {}
        } else {
            logger.debug { "Probably encountered an error loading data from file." }
        }

        if (data == null) {
            logger.error { "Skipping puzzle due to no input." }
            return null
        }

        logger.debug { "Solution data was successfully loaded." }
        return data
    }

    abstract fun Stream<String>.parse(): T

    protected fun Stream<String>.toStringList(): List<String> {
        return this.map { it.trim() }.toList()
    }

    protected fun Stream<String>.toIntList(): List<Int> {
        return this.map { it.trim().toInt() }.toList()
    }

    open fun part1(): String {
        return "${ANSI_RED}Part 1 is not yet implemented."
    }

    open fun part2(): String {
        return "${ANSI_RED}Part 2 is not yet implemented."
    }
}