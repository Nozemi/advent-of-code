package io.nozemi.aoc.puzzle

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.michaelbull.logging.InlineLogger
import io.github.cdimascio.dotenv.dotenv
import io.nozemi.aoc.currentDay
import io.nozemi.aoc.currentYear
import io.nozemi.aoc.puzzle.exceptions.HasAlreadyDownloadedException
import io.nozemi.aoc.puzzle.exceptions.InputFileDownloadFailedException
import io.nozemi.aoc.puzzle.exceptions.NoDataProvidedException
import io.nozemi.aoc.puzzle.exceptions.NoDownloadTokenProvidedException
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private val logger = InlineLogger()

var token: String? = null
const val basePackage = "io.nozemi.aoc.solutions"
val dayOfYearRegex = Regex("$basePackage\\.year([\\d]{4})\\.day([\\d]{2})")

@OptIn(ExperimentalTime::class)
class PuzzleSelectScreen : CliktCommand() {
    private val year: String by option(
        "-y",
        "--year",
        help = "input year(s) [example: 2020,2021,... || 2020-2025 || both together]"
    ).prompt(default = currentYear.toString())
    private val day: String by option(
        "-d",
        "--day",
        help = "input day(s) [example: 1,2,... || 3-5 || both together]"
    ).prompt(default = currentDay.toString())
    private val aocToken: String by option(
        "-t",
        "--token",
        help = "Token used to download puzzle input data from AoC directly.",
    ).prompt(default = "N/A", hideInput = true, text = "AoC Token (default:", promptSuffix = "): ")

    private val puzzleResolver: PuzzleResolver

    init {
        context {
            helpOptionNames = setOf("--help")
        }

        println(
            """$ANSI_BLUE
███╗░░██╗░█████╗░███████╗███████╗███╗░░░███╗██╗██╗░██████╗  ░█████╗░░█████╗░░█████╗░
████╗░██║██╔══██╗╚════██║██╔════╝████╗░████║██║╚█║██╔════╝  ██╔══██╗██╔══██╗██╔══██╗
██╔██╗██║██║░░██║░░███╔═╝█████╗░░██╔████╔██║██║░╚╝╚█████╗░  ███████║██║░░██║██║░░╚═╝
██║╚████║██║░░██║██╔══╝░░██╔══╝░░██║╚██╔╝██║██║░░░░╚═══██╗  ██╔══██║██║░░██║██║░░██╗
██║░╚███║╚█████╔╝███████╗███████╗██║░╚═╝░██║██║░░░██████╔╝  ██║░░██║╚█████╔╝╚█████╔╝
╚═╝░░╚══╝░╚════╝░╚══════╝╚══════╝╚═╝░░░░░╚═╝╚═╝░░░╚═════╝░  ╚═╝░░╚═╝░╚════╝░░╚════╝░${ANSI_GREEN}
======================================================================================${ANSI_RESET}
        """.trimIndent()
        )
        println(
            """
            This is Nozemi's AoC implementation, and you may use it to test your own puzzle data.
        """.trimIndent()
        )
        println()
        println(
            """
            You're now asked to provide year(s) and day(s) you want solution for.
            You can press enter for default values (current year and day).
            $ANSI_BOLD$ANSI_YELLOW
            You can also provide your AoC token (found in the session cookie) if you want
            your input to be automatically downloaded.$ANSI_RESET
        """.trimIndent()
        )

        println()

        puzzleResolver = PuzzleResolver().resolvePuzzles()
    }

    override fun run() {
        println()
        logger.info { "Hold your $ANSI_BOLD${ANSI_UNDERLINE}beer$ANSI_RESET$ANSI_GREEN! Solutions coming up for day(s) $ANSI_PURPLE$ANSI_BOLD$ANSI_UNDERLINE$day$ANSI_RESET$ANSI_GREEN of year(s) $ANSI_PURPLE$ANSI_BOLD$ANSI_UNDERLINE$year$ANSI_RESET$ANSI_GREEN!" }
        if (aocToken.length == 128) {
            token = this.aocToken
            logger.info { "${ANSI_BOLD}${ANSI_YELLOW}Will attempt to fetch your puzzle inputs with the token you provided." }
        } else {
            val dotenv = dotenv {
                ignoreIfMissing = true
            }

            token = dotenv["AOC_TOKEN"] ?: null
            if (token?.length != 128) token = null
        }
        println()

        val years = parseInput(year)
        val days = parseInput(day)

        runPuzzles(years, days)
    }

    @ExperimentalTime
    private fun runPuzzles(years: List<Int>, days: List<Int>) {
        val timing = measureTime {
            years.forEach YearsLoop@ { year ->
                if (!puzzleResolver.hasPuzzlesForYear(year)) return@YearsLoop
                println("$ANSI_PURPLE$ANSI_BOLD==== Solutions for $year ====")
                println("$ANSI_PURPLE$ANSI_BOLD============================$ANSI_RESET")
                days.forEach DaysLoop@ { day ->
                    try {
                        val puzzle = puzzleResolver.getPuzzle(year, day) ?: return@DaysLoop
                        println("$ANSI_BLUE$ANSI_BOLD== Day $day - ${puzzle.javaClass.simpleName}$ANSI_RESET")
                        repeat(0) { // TODO: Easy way to toggle the warmup. We don't want to have this during debugging for example.
                            puzzle.solutions().forEach { it.invoke() }
                        }
                        puzzle.printAnswers()
                    } catch (ex: Exception) {
                        println("$ANSI_BLUE$ANSI_BOLD== Day $day - ${ANSI_RED}Error Occurred$ANSI_RESET")
                        print("$ANSI_RED${ANSI_BOLD}Skipping puzzle. $ANSI_RESET$ANSI_RED")
                        when(ex) {
                            is InputFileDownloadFailedException -> print("Failed to download input file: ${ex.message}.")
                            is HasAlreadyDownloadedException,
                            is NoDataProvidedException,
                            is NoDownloadTokenProvidedException -> print(ex.message)
                            else -> throw ex
                        }
                        println(ANSI_RESET)
                    }
                }
                println()
            }
        }
        println("$ANSI_UNDERLINE                                        $ANSI_RESET")
        println("$ANSI_GREEN${ANSI_BOLD}Solving above puzzles took $timing. Including initialization, loading and parsing input.$ANSI_RESET")
    }

    private fun parseInput(input: String): List<Int> {
        val values = mutableListOf<Int>()

        input.trim().replace(" ", "").split(",").forEach { value ->
            when (value.contains("-")) {
                true -> {
                    val range = value.split("-").map { it.toInt() }
                    for (i in range[0] until range[1] + 1) {
                        values.add(i)
                    }
                }
                false -> values.add(value.toInt())
            }
        }

        return values
    }
}

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BOLD = "\u001B[1m"
const val ANSI_UNDERLINE = "\u001B[4m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"