package io.nozemi.aoc.solutions.year2021.day08.impl

class SegmentPattern(
    val outputPatterns: Array<Set<Char>>,
    val signalPatterns: Array<Set<Char>>,
) {
    private val knownLetters: MutableMap<Char, Char> = mutableMapOf()
    private val knownNumbers: Array<Set<Char>> = Array(10) { "".toSet() }

    init {
        rewireOutputPattern()
    }

    /**
     * [AoC - Day 8, Part 2](https://adventofcode.com/2021/day/8)
     *
     * The wire/segment connections are scrambled. They no longer match the correct one. (See below)
     * ```
     *  aaaa
     * b    c
     * b    c
     *  dddd
     * e    f
     * e    f
     *  gggg
     * ```
     *
     * We need to map the scrambled letters to the actual one to get the correct numbers for the output number.
     */
    private fun rewireOutputPattern() {
        mapKnownNumbers()
        mapRemainingNumbers()
        mapLetters()
    }

    private fun mapKnownNumbers() {
        // We already know that the numbers 1, 4, 7 & 8 are easy to differentiate from the rest.
        signalPatterns.forEach {
            when (it.size) {
                2 -> knownNumbers[1] = it
                4 -> knownNumbers[4] = it
                3 -> knownNumbers[7] = it
                7 -> knownNumbers[8] = it
            }
        }
    }

    private fun mapRemainingNumbers() {
        while (knownNumbers.any { it.isEmpty() }) {
            signalPatterns.forEach {
                if (knownNumbers.contains(it)) return@forEach

                when (it.size) {
                    6 -> {
                        // We'll process the numbers with 6 letter patterns, being 0, 6 & 9.
                        if (!it.containsAll(knownNumbers[1])) {
                            // We know that 6 is the only from these 3 that does not contain both letters of 1.
                            knownNumbers[6] = it
                        } else if (it.containsAll(knownNumbers[4])) {
                            // We know that 9 is the only from these 3 that contains all the letters from 4.
                            knownNumbers[9] = it
                        }
                        // We'll eventually find the 6 and 9, and then we know that the last one is 0
                        if (knownNumbers[6].isNotEmpty() && knownNumbers[9].isNotEmpty()) {
                            knownNumbers[0] = it
                        }
                    }
                    5 -> {
                        // We'll process the numbers with 5 letter patterns, being 2, 3 & 5.
                        if (it.containsAll(knownNumbers[7].toList())) {
                            // We know that this is 3, because it contains all 3 letters from 7's pattern.
                            knownNumbers[3] = it
                        } else if (it.intersect(knownNumbers[6]).size == 4) {
                            knownNumbers[2] = it
                        } else if (it.intersect(knownNumbers[6]).size == 5) {
                            knownNumbers[5] = it
                        }
                    }
                }
            }
        }
    }

    private fun mapLetters() {
        // We know that 1 has only "c" and "f", so the last one in 7 is the letter "a".
        knownLetters['a'] = findLetter(from = 7, and = 1)
        // We know that the only missing letter from 1 in six is "c", which gets us the position of "c".
        knownLetters['c'] = findLetter(from = 1, and = 6)
        // Number 0 will never have "d", and number 8 has all letters, which gets us the position of "d".
        knownLetters['d'] = findLetter(from = 8, and = 0)
        // We know that the only missing letter from 9 in 3 is "b", which gets us the position of "b".
        knownLetters['b'] = findLetter(from = 9, and = 3)
        // We know that the only missing letter from 8 in 9 is "e", which gets us the position of "e".
        knownLetters['e'] = findLetter(from = 8, and = 9)
        // We know that the only missing letter from 9 in 4 that we don't already know about is "g".
        knownLetters['g'] = findLetter(from = 9, and = 4, additionalCondition = { char -> knownLetters['a'] != char })
        // We know that the only letter 6 and 1 has in common is "f", which gets us the position of "f".
        knownLetters['f'] = findLetter(from = 6, and = 1, alternativeCondition = { char, number ->
            knownNumbers[number].contains(char)
        })
    }

    private fun findLetter(
        from: Int, and: Int,
        additionalCondition: (char: Char) -> Boolean = { _ -> true },
        alternativeCondition: (char: Char, number: Int) -> Boolean? = { _, _ -> null }
    ): Char {
        return knownNumbers[from].single {
            alternativeCondition(it, and) ?: !knownNumbers[and].contains(it) && additionalCondition(it)
        }
    }

    fun getOutputNumber(): Int = outputPatterns.map {
        when (it.size) {
            5 -> {
                if (it.containsAll(knownNumbers[2])) 2
                else if (it.containsAll(knownNumbers[3])) 3
                else 5
            }
            6 -> {
                if (it.containsAll(knownNumbers[6])) 6
                else if (it.containsAll(knownNumbers[9])) 9
                else 0
            }
            2 -> 1
            3 -> 7
            4 -> 4
            7 -> 8
            else -> throw RuntimeException("You fucked up!")
        }
    }.joinToString("").toInt()

    fun uniqueLengthPatternsInOutput(): Int {
        return outputPatterns.count {
            when (it.size) {
                2, 3, 4, 7 -> true
                else -> false
            }
        }
    }

    /**
     * Override the normal set method for this one, so we can also remove from signalPatterns at the same time.
     */
    private operator fun MutableMap<Int, String>.set(key: Int, value: String) {
        this.put(key, value)
    }

    companion object {
        fun String.toSegmentPattern(): SegmentPattern {
            val (first, last) = split(" | ")
            return SegmentPattern(
                signalPatterns = first.split(' ').map { it.toSet() }.toTypedArray(),
                outputPatterns = last.split(' ').map { it.toSet() }.toTypedArray()
            )
        }
    }
}