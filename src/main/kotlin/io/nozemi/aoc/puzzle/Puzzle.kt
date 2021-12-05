package io.nozemi.aoc.puzzle

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.commandline.ANSI_BLUE
import io.nozemi.aoc.commandline.ANSI_RESET
import io.nozemi.aoc.commandline.basePackage
import io.nozemi.aoc.commandline.dayOfYearRegex
import java.nio.file.Path
import kotlin.reflect.KFunction0

private val logger = InlineLogger()

abstract class Puzzle<T : List<*>>(private var input: String) {
    private val inputFilePath: Path

    private val puzzleName = this.javaClass.simpleName
    private val year: Int
    private val day: Int

    private val inputLoader: InputLoader

    lateinit var rawInput: T

    init {
        val dayAndYear = dayOfYearRegex.find(this.javaClass.packageName)
            ?: throw Exception("Package name ${this.javaClass.packageName} is not valid. " +
                    "Follow the format: $basePackage.year0000.day00.Day00")

        year = dayAndYear.groupValues[1].toInt()
        day = dayAndYear.groupValues[3].toInt()

        inputFilePath = Path.of("./data/inputs/${this.year}/${puzzleName.lowercase()}.txt")

        inputLoader = InputLoader(inputFilePath)
        inputLoader.download(year, day)

        loadInput()
    }

    private fun loadInput() {
        val data = if (input.isNotBlank()) {
            input.lineSequence()
        } else {
            inputLoader.loadFromFile() ?: emptySequence()
        }

        rawInput = data.parse()
    }

    abstract fun solutions(): List<KFunction0<Any>>
    abstract fun Sequence<String>.parse(): T

    fun getAnswer(part: Int): Any {
        if (rawInput.isEmpty()) return "No input data."
        return solutions()[part - 1].invoke()
    }

    fun printAnswer(part: Int) {
        logger.info { "$ANSI_BLUE[Part $part]$ANSI_RESET: ${getAnswer(part)}" }
    }

    fun printAnswers() {
        for (i in 1 until solutions().size + 1) {
            printAnswer(i)
        }
    }
}