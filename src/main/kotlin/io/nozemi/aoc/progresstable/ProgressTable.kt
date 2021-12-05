package io.nozemi.aoc.progresstable

class ProgressTable(private val solvedPuzzles: SolvedPuzzles) {
    private val rows = 25
    private var table: String = ""

    init {
        generateHeading()
        generateAndPopulateRows()
    }

    fun getGeneratedString(): String = table

    private fun generateHeading() {
        table += "| Days  |"
        solvedPuzzles.keys.forEach {
            table += " $it[$it]   |"
        }
        table += "\n|-------|"
        repeat(solvedPuzzles.keys.size) {
            table += "--------------|"
        }
    }

    private fun generateAndPopulateRows() {
        for (i in 1 until rows + 1) {
            table += "\n| ${i.toString().padStart(2, ' ')}    |"
            solvedPuzzles.forEach {
                val puzzle = it.value[i - 1]
                if (puzzle != null) {
                    if (puzzle.first && puzzle.second) table += " ✅           |"
                    if (puzzle.first && !puzzle.second) table += " \uD83D\uDEA7           |"
                    if (!puzzle.first) table += " ❌           |"
                } else {
                    table += " ❌           |"
                }
            }
        }
    }

    private val emojiIncomplete = "❌"
    private val EMOJI_PART1ONLY = "\uD83D\uDEA7"
    private val EMOJI_COMPLETED = "✅"
}