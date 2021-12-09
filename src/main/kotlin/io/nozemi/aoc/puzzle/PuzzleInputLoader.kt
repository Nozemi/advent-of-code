package io.nozemi.aoc.puzzle

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.puzzle.exceptions.HasAlreadyDownloadedException
import io.nozemi.aoc.puzzle.exceptions.InputFileDownloadFailedException
import io.nozemi.aoc.puzzle.exceptions.NoDataProvidedException
import io.nozemi.aoc.puzzle.exceptions.NoDownloadTokenProvidedException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

private val logger = InlineLogger()

class InputLoader(private val inputFile: Path, year: Int, day: Int, private val shouldAttemptDownload: Boolean = true) {

    private var hasDownloaded = false

    var inputData = loadFromFile(year, day)
        private set

    private fun loadFromFile(year: Int, day: Int): Sequence<String> {
        // If file exists and is empty or doesn't exist, we need to download the file.
        if ((Files.exists(inputFile) && Files.readString(inputFile).isEmpty()) || !Files.exists(inputFile)) {
            if (shouldAttemptDownload) {
                // If download fails, we throw a NoDataProvidedException that is later caught.
                if (!downloadInput(year, day)) throw NoDataProvidedException()
            } else {
                throw NoDataProvidedException()
            }
        }

        return Files.lines(inputFile).asSequence()
    }

    /**
     * Downloads input from [Advent of Code (AoC)](https://adventofcode.com/) using the provided year and day.
     * If a token isn't specified, the download won't happen. It will also only download once each time the puzzle
     * is initialized.
     */
    private fun downloadInput(year: Int, day: Int): Boolean {
        if (hasDownloaded) throw HasAlreadyDownloadedException()
        else if (token == null) throw NoDownloadTokenProvidedException()

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

                return true
            } else {
                throw InputFileDownloadFailedException("${connection.responseCode} - ${connection.responseMessage}")
            }
        } catch (exception: IOException) {
            logger.debug { exception.message }
        }

        return false
    }
}