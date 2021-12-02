package io.nozemi.aoc

import com.github.michaelbull.logging.InlineLogger
import java.io.InputStream
import java.util.stream.Stream

abstract class Puzzle<T : Any>(year: Int, private var input: String) {

    protected val logger = InlineLogger()

    private val puzzleName = this.javaClass.simpleName
    private val inputFilePath = "/puzzle-input/year-$year/${puzzleName.lowercase()}-input.txt"

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
            val output = "[Part ${i + 1}]: ${partResults[i]}"
            if (output.contains("not yet implemented.")) {
                logger.error { output }
            } else {
                logger.info { output }
            }
        }
    }

    private fun loadInput(): Stream<String>? {
        logger.debug { "Loading solution data..." }

        val inputFile = inputFilePath.asResourceStream()

        var data: Stream<String>? = null

        if (input.isNotBlank()) {
            logger.debug { "Loading input directly..." }
            data = input.lines().stream()
        } else if (input.isBlank() && inputFile != null) {
            logger.debug { "Loading input from file..." }
            data = inputFile.bufferedReader().lines()
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

    private fun String.asResourceStream(): InputStream? {
        logger.debug { "Trying Resource (puzzleName=$puzzleName, path=$this)" }
        return Puzzle::class.java.getResourceAsStream(this)
    }

    open fun part1(): String {
        return "Part 1 is not yet implemented."
    }

    open fun part2(): String {
        return "Part 2 is not yet implemented."
    }
}