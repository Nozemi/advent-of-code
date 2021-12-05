package io.nozemi.aoc.puzzle

import com.github.michaelbull.logging.InlineLogger
import io.nozemi.aoc.commandline.token
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

private val logger = InlineLogger()

class InputLoader(private val inputFile: Path? = null) {

    private var hasDownloaded = false

    fun loadFromFile(): Sequence<String>? {
        if (inputFile == null || Files.notExists(inputFile)) return null
        return Files.lines(inputFile).asSequence()
    }

    fun download(year: Int, day: Int) {
        if (token == null || hasDownloaded || inputFile == null) return

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
            }
        } catch (exception: IOException) {
            logger.debug { exception.message }
        }
    }
}