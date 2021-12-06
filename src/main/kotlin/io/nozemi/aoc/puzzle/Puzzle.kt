package io.nozemi.aoc.puzzle

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.commandline.ANSI_BLUE
import io.nozemi.aoc.commandline.ANSI_RESET
import io.nozemi.aoc.commandline.basePackage
import io.nozemi.aoc.commandline.dayOfYearRegex
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.KFunction0
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

private val logger = InlineLogger()

abstract class Puzzle<T : List<*>>(private var input: String? = null) {
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

        if (Files.notExists(inputFilePath)) inputLoader.download(year, day)

        loadInput()
    }

    private fun loadInput() {
        val data = if (input != null && input!!.isNotBlank()) {
            input!!.lineSequence()
        } else {
            inputLoader.loadFromFile() ?: emptySequence()
        }

        rawInput = data.parse()
    }

    abstract fun Sequence<String>.parse(): T
    abstract fun solutions(): List<KFunction0<Any>>

    @ExperimentalTime
    fun getAnswer(part: Int): TimedValue<Any> {
        if (rawInput.isEmpty()) return TimedValue("No input data.", Duration.INFINITE)
        return measureTimedValue {
            solutions()[part].invoke()
        }
    }

    @OptIn(ExperimentalTime::class)
    fun printAnswer(part: Int) {
        val answer = getAnswer(part)
        logger.info { "$ANSI_BLUE[Part ${part + 1}]$ANSI_RESET: ${answer.value} (took ${answer.duration})" }
    }

    fun printAnswers() {
        for (i in 0 until solutions().size) {
            printAnswer(i)
        }
    }
}