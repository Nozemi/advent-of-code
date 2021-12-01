package io.nozemi.aoc

import com.github.michaelbull.logging.InlineLogger
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.notExists

abstract class Solution<T>(year: Int) {
    protected val logger = InlineLogger()
    private val inputFilePath: Path = Path.of("./data/year-$year/${this.javaClass.simpleName.lowercase()}-input.txt")

    protected abstract val solutionInput: T?

    init {
        execute()
    }

    private fun execute() {
        logger.debug { "Loading solution data..." }
        if(inputFilePath.notExists()) {
            logger.error { "Skipping ${this.javaClass.simpleName} because input file was not found at ${inputFilePath.absolutePathString()}." }
            return
        }

        loadInput(inputFilePath)
        logger.debug { "Solution data was successfully loaded." }
        logger.info { "[Part 1]: ${part1()}" }
        logger.info { "[Part 2]: ${part2()}" }
    }

    abstract fun loadInput(inputFilePath: Path)

    open fun part1(): String {
        return "Part 1 is not yet implemented."
    }

    open fun part2(): String {
        return "Part 2 is not yet implemented."
    }
}