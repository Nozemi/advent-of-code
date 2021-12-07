package io.nozemi.aoc.puzzle

import com.github.michaelbull.logging.InlineLogger
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

private val logger = InlineLogger()

class InputLoader(private val inputFile: Path) {

    private var hasDownloaded = false

    var inputData = loadFromFile()
        private set

    private fun loadFromFile(): Sequence<String>? {
        if (Files.notExists(inputFile)) return null
        return Files.lines(inputFile).asSequence()
    }

    /**
     * Downloads input from [Advent of Code (AoC)](https://adventofcode.com/) using the provided year and day.
     * If a token isn't specified, the download won't happen. It will also only download once each time the puzzle
     * is initialized.
     */
    fun downloadInput(year: Int, day: Int) {
        if (token == null || hasDownloaded) return

        hasDownloaded = true

        val url = URL("https://adventofcode.com/${year}/day/${day}/input")
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Cookie", "session=$token")

            if (connection.responseCode == 200) {
                if (Files.notExists(inputFile)) Files.createDirectories(inputFile.parent)
                Files.newBufferedWriter(inputFile).use {
                    it.write(connection.inputStream.bufferedReader().readText())
                }

                inputData = loadFromFile()
            }
        } catch (exception: IOException) {
            logger.debug { exception.message }
        }
    }
}