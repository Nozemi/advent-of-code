package io.nozemi.aoc.solutions.year2021.day08.impl

data class SegmentPattern(
    val outputPatterns: List<String>,
    val uniquePatterns: List<String>
) {

    fun amountOfDigits(vararg numbers: Int, outputOnly: Boolean = true): Int {
        return numbers.sumOf { number ->
            outputPatterns.count { it.length == Number.findByInt(number).letterCount }
        }
    }

    fun getOutputNumber(): Long {
        return 0L
    }

    enum class Number(val actual: Int, val letterCount: Int) {
        ZERO(actual = 0, letterCount = 6),
        ONE(actual = 1, letterCount = 2),
        TWO(actual = 2, letterCount = 5),
        THREE(actual = 3, letterCount = 5),
        FOUR(actual = 4, letterCount = 4),
        FIVE(actual = 5, letterCount = 5),
        SIX(actual = 6, letterCount = 6),
        SEVEN(actual = 7, letterCount = 3),
        EIGHT(actual = 8, letterCount = 7),
        NINE(actual = 9, letterCount = 6);

        companion object {

            fun findByInt(int: Int): Number {
                return values().first { it.actual == int }
            }
        }
    }

    companion object {
        fun String.toSegmentPattern(): SegmentPattern {
            val (first, last) = split(" | ")
            return SegmentPattern(
                uniquePatterns = first.split(' '),
                outputPatterns = last.split(' ')
            )
        }
    }
}