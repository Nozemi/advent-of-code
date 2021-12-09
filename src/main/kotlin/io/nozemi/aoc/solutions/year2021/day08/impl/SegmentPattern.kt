package io.nozemi.aoc.solutions.year2021.day08.impl

class SegmentPattern(
    val outputPatterns: List<String>,
    val signalPatterns: MutableList<String>,
) {
    private val knownLetters: MutableMap<Char, Char> = mutableMapOf()
    private val knownNumbers: MutableMap<Int, String> = mutableMapOf()

    init {
        repeat(10) { index ->
            knownNumbers[index] = ""
        }
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
        // We already know that the numbers 1, 4, 7 & 8 are easy to differentiate from the rest.
        signalPatterns.forEach {
            when (it.length) {
                2 -> knownNumbers[1] = it
                4 -> knownNumbers[4] = it
                3 -> knownNumbers[7] = it
                7 -> knownNumbers[8] = it
            }
        }

        while (knownNumbers.any { it.value == "" }) {
            signalPatterns.forEach {
                if (knownNumbers.values.contains(it)) return@forEach

                when (it.length) {
                    6 -> {
                        // We'll process the numbers with 6 letter patterns, being 0, 6 & 9.
                        if (!it.toList().containsAll(knownNumbers[1]!!.toList())) {
                            // We know that 6 is the only from these 3 that does not contain both letters of 1.
                            knownNumbers[6] = it
                        } else if (it.toList().containsAll(knownNumbers[4]!!.toList())) {
                            // We know that 9 is the only from these 3 that contains all the letters from 4.
                            knownNumbers[9] = it
                        }
                        // We'll eventually find the 6 and 9, and then we know that the last one is 0
                        if (knownNumbers[6]!!.isNotBlank() && knownNumbers[9]!!.isNotBlank()) {
                            knownNumbers[0] = it
                        }
                    }
                    5 -> {
                        // We'll process the numbers with 5 letter patterns, being 2, 3 & 5.
                        if (it.toList().containsAll(knownNumbers[7]!!.toList())) {
                            // We know that this is 3, because it contains all 3 letters from 7's pattern.
                            knownNumbers[3] = it
                        } else if (it.toList().intersect(knownNumbers[6]!!.toSet()).size == 4) {
                            knownNumbers[2] = it
                        } else if (it.toList().intersect(knownNumbers[6]!!.toSet()).size == 5) {
                            knownNumbers[5] = it
                        }
                    }
                }
            }
        }

        /*
         * Now we know all the numbers, now we can map the letters.
         */

        // We know that 1 has only "c" and "f", so the last one in 7 is the letter "a".
        knownLetters['a'] = knownNumbers[7]!!.toList().first { !knownNumbers[1]!!.contains(it) }
        // We know that the only missing letter from 1 in six is "c", which gets us the position of "c".
        knownLetters['c'] = knownNumbers[1]!!.toList().first { !knownNumbers[6]!!.contains(it) }
        // We know that the only letter 6 and 1 has in common is "f", which gets us the position of "f".
        knownLetters['f'] = knownNumbers[6]!!.toList().first { knownNumbers[1]!!.contains(it) }
        // Number 0 will never have "d", and number 8 has all letters, which gets us the position of "d".
        knownLetters['d'] = knownNumbers[8]!!.toList().first { !knownNumbers[0]!!.contains(it) }
        // We know that the only missing letter from 9 in 3 is "b", which gets us the position of "b".
        knownLetters['b'] = knownNumbers[9]!!.toList().first { !knownNumbers[3]!!.contains(it) }
        // We know that the only missing letter from 8 in 9 is "e", which gets us the position of "e".
        knownLetters['e'] = knownNumbers[8]!!.toList().first { !knownNumbers[9]!!.contains(it) }
        // We know that the only missing letter from 9 in 4 that we don't already know about is "g".
        knownLetters['g'] =
            knownNumbers[9]!!.toList().first { !knownNumbers[4]!!.contains(it) && knownLetters['a'] != it }
    }

    fun getOutputNumber(): Int = outputPatterns.map {
        when (it.length) {
            5 -> {
                if (it.toList().containsAll(knownNumbers[2]!!.toList())) 2
                else if (it.toList().containsAll(knownNumbers[3]!!.toList())) 3
                else 5
            }
            6 -> {
                if (it.toList().containsAll(knownNumbers[6]!!.toList())) 6
                else if (it.toList().containsAll(knownNumbers[9]!!.toList())) 9
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
            when (it.length) {
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
                signalPatterns = first.split(' ').toMutableList(),
                outputPatterns = last.split(' ')
            )
        }
    }
}