package io.nozemi.aoc

import com.github.michaelbull.logging.InlineLogger

abstract class Puzzle<T>(private val year: Int, private var input: String) {
    protected val logger = InlineLogger()

    protected abstract val solutionInput: T?

    fun execute() {
        logger.debug { "Loading solution data..." }

        val inputFile =
            this::class.java.getResource("/puzzle-input/year-$year/${this.javaClass.simpleName.lowercase()}-input.txt")
        if (input.isBlank() && inputFile != null) {
            input = inputFile.readText()
        } else {
            logger.debug { "Couldn't find input file" }
        }

        if (input.isBlank()) {
            logger.error { "Skipping puzzle because input was empty." }
            return
        }

        loadInput(input)
        logger.debug { "Solution data was successfully loaded." }

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

    abstract fun loadInput(input: String)

    open fun part1(): String {
        return "Part 1 is not yet implemented."
    }

    open fun part2(): String {
        return "Part 2 is not yet implemented."
    }
}