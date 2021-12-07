package io.nozemi.aoc.puzzle

import io.nozemi.aoc.commandline.ANSI_BLUE
import io.nozemi.aoc.commandline.ANSI_RESET
import io.nozemi.aoc.commandline.basePackage
import io.nozemi.aoc.commandline.dayOfYearRegex
import java.nio.file.Path
import kotlin.reflect.KFunction0
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class Puzzle<T>(private var input: String? = null) {
    private val inputFilePath: Path

    private val puzzleName = this.javaClass.simpleName
    private val year: Int
    private val day: Int

    private val dayDirectory: String

    private val inputLoader: InputLoader

    var rawInput: T

    init {
        val dayAndYear = dayOfYearRegex.find(this.javaClass.packageName)
            ?: throw Exception(
                "Package name ${this.javaClass.packageName} is not valid. " +
                        "Follow the format: $basePackage.year0000.day00.Day00"
            )

        year = dayAndYear.groupValues[1].toInt()
        day = dayAndYear.groupValues[3].toInt()
        dayDirectory = "day" + day.toString().padStart(2, '0')

        inputFilePath = Path.of("./data/inputs/${this.year}/${dayDirectory}.txt")

        inputLoader = InputLoader(inputFilePath)

        if (inputLoader.inputData == null)
            inputLoader.downloadInput(year, day)

        rawInput = loadInput().parse()
    }

    private fun loadInput(): Sequence<String> {
        val data = if (input != null && input!!.isNotBlank()) {
            input!!.lineSequence()
        } else {
            inputLoader.inputData ?: emptySequence()
        }

        return data
    }

    abstract fun Sequence<String>.parse(): T
    abstract fun solutions(): List<KFunction0<Any>>

    @ExperimentalTime
    fun getAnswer(part: Int): TimedValue<Any> {
        if (rawInput.isEmpty()) return TimedValue("No input data.", Duration.INFINITE)
        return measureTimedValue {
            solutions()[part - 1].invoke()
        }
    }

    @OptIn(ExperimentalTime::class)
    fun printAnswer(part: Int) {
        val answer = getAnswer(part)
        println("$ANSI_BLUE[Part ${part}]$ANSI_RESET: ${answer.value} (took ${answer.duration})")
    }

    fun printAnswers() {
        for (i in 0 until solutions().size) {
            printAnswer(i + 1)
        }
    }
}

private fun <T> T.isEmpty(): Boolean {
    if (this == null) return true
    if (this is String) {
        return this.isBlank()
    } else if (this is List<*>) {
        return this.isEmpty()
    }
    return true
}
