package io.nozemi.aoc.tools

import io.nozemi.aoc.tools.generators.PuzzlesSolvedTableGenerator
import io.nozemi.aoc.utils.toPath
import java.io.File
import java.nio.file.Files

/**
 * This will be the tool selector in the future. For now there won't be many options,
 * so the tool will pretty much only automatically write a new README.md without the ability to take any arguments.
 */
class ToolSelectorScreen {

    private val generator = PuzzlesSolvedTableGenerator().generate()

    init {
        writeNewReadme()
    }

    private fun writeNewReadme() {
        val templateUrl = ToolSelectorScreen::class.java.getResource("/templates/README.template.md") ?: return
        val templateFile = File(templateUrl.toURI())
        val readmeFileString = templateFile.readText()
        val processedReadme = generator.injectGeneratedTable(readmeFileString)
        Files.newBufferedWriter("./README.md".toPath()).use {
            it.write(processedReadme)
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            ToolSelectorScreen()
        }
    }
}