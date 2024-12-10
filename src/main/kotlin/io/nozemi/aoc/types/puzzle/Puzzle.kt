package io.nozemi.aoc.types.puzzle

import java.nio.file.Path
import kotlin.reflect.KFunction0
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class Puzzle<T : Any>(private var input: String? = null) {
    private val inputFilePath: Path

    private val puzzleName = this.javaClass.simpleName
    private val year: Int
    private val day: Int

    private val dayDirectory: String

    private val inputLoader: InputLoader?

    var parsedInput: T

    init {
        val dayAndYear = dayOfYearRegex.find(this.javaClass.packageName)
            ?: throw Exception(
                "Package name ${this.javaClass.packageName} is not valid. " +
                        "Follow the format: $basePackage.year0000.day00.Day00"
            )

        year = dayAndYear.groupValues[1].toInt()
        day = dayAndYear.groupValues[2].toInt()
        dayDirectory = "day" + day.toString().padStart(2, '0')

        inputFilePath = Path.of("./data/inputs/${this.year}/${dayDirectory}.txt")

        inputLoader = if (input?.isNotEmpty() == true) null else InputLoader(inputFilePath, year, day)

        parsedInput = loadInput().parse()
    }

    private fun loadInput(): Sequence<String> {
        val input = input

        val data = if (input?.isNotEmpty() == true) {
            input.lineSequence()
        } else {
            inputLoader?.inputData ?: emptySequence()
        }

        return data
    }

    abstract fun Sequence<String>.parse(): T
    abstract fun solutions(): List<KFunction0<Any>>

    @ExperimentalTime
    fun getAnswer(part: Int): TimedValue<Any> {
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