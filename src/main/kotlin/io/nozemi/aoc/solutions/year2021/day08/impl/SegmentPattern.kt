package io.nozemi.aoc.solutions.year2021.day08.impl

class SegmentPattern(
    val outputPatterns: List<String>,
    val signalPatterns: List<String>,
) {
    val outputNumbers: MutableList<Int> = mutableListOf()

    init {

        // be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        val knownNumbers: MutableMap<Int, String> = mutableMapOf(
            Pair(0, ""),
            Pair(1, ""),
            Pair(2, ""),
            Pair(3, ""),
            Pair(4, ""),
            Pair(5, ""),
            Pair(6, ""),
            Pair(7, ""),
            Pair(8, ""),
            Pair(9, ""),
        )

        signalPatterns.forEach {
            when (it.length) {
                2 -> knownNumbers[1] = it
                4 -> knownNumbers[4] = it
                3 -> knownNumbers[7] = it
                7 -> knownNumbers[8] = it
            }
        }

        val knownLetters: MutableMap<Char, Char> = mutableMapOf()

        // we have 3 numbers with 6 of length, we don't know the order they come in
        // thus the repeat of 3. I know it's shitty dw!!!!
        repeat(3) {
            signalPatterns.filter { it.length == 6 }.forEach {
                // If the current number's pattern already is known to us, we don't need to continue
                if (knownNumbers.values.contains(it)) return@forEach

                val one = knownNumbers[1]!!
                val four = knownNumbers[4]!!

                // if this pattern contains all the letters in 1, it'll be 6.
                // both 9 and 0 contains both c and f. 6 is missing c.
                if (!it.toList().containsAll(one.toList()) && knownNumbers[6]!!.isEmpty()
                && !knownNumbers.values.contains(it))  {
                    knownNumbers[6] = it
                }

                // if this pattern contains all letters of 4, we know it's 9.
                // 0 is missing d, 6 is missing c
                if (it.toList().containsAll(four.toList()) && knownNumbers[9]!!.isEmpty()
                && !knownNumbers.values.contains(it)) {
                    knownNumbers[9] = it
                }

                // Now that we know 6 and 9, we can easily tell that the last one is 0.
                if (knownNumbers[6]!!.isNotEmpty() && knownNumbers[9]!!.isNotEmpty()
                && !knownNumbers.values.contains(it)) {
                    knownNumbers[0] = it
                }
            }
        }

        val one = knownNumbers[1]!!
        val six = knownNumbers[6]!!
        val seven = knownNumbers[7]!!

        knownLetters['a'] = seven.toList().first { char -> !one.toList().contains(char) && !knownLetters.values.contains(char) }
        knownLetters['c'] = one.toList().first { char -> !six.toList().contains(char) && !knownLetters.values.contains(char) }
        knownLetters['f'] = six.toList().first { char -> one.toList().contains(char) && !knownLetters.values.contains(char) }

        // We know that we have 3 patterns with 5 letters, so we run the code 3 times to make sure.
        // We don't know the order we are given them in so.
        repeat(3) {
            signalPatterns.filter { it.length == 5 }.forEach {
                // If we already know this pattern, we don't need to continue.
                if (knownNumbers.values.contains(it)) return@forEach

                val four = knownNumbers[4]!!

                // if this pattern contains all numbers of 7, it'll be a 3.
                // neither 2 or 5 has all letters of 7
                if (it.toList().containsAll(seven.toList()) && knownNumbers[3]!!.isEmpty()
                    && !knownNumbers.values.contains(it)) {
                    knownNumbers[3] = it
                }

                // We know that 2 and 3 contains the letter c, so it has to be 5 if the pattern contains c
                if (!it.toList().contains(knownLetters['c']) && knownNumbers[5]!!.isEmpty()) {
                    knownNumbers[5] = it
                }
            }
        }

        knownNumbers[2] = signalPatterns.firstOrNull {
            !knownNumbers.values.contains(it)
        } ?: ""

        knownLetters['d'] = knownNumbers[8]!!.toList().first { !knownNumbers[0]!!.toList().contains(it) && !knownLetters.values.contains(it) }
        knownLetters['b'] = knownNumbers[9]!!.toList().first { !knownNumbers[3]!!.toList().contains(it) && !knownLetters.values.contains(it) }
        knownLetters['e'] = knownNumbers[8]!!.toList().first { !knownNumbers[9]!!.toList().contains(it) && !knownLetters.values.contains(it) }
        knownLetters['g'] = knownNumbers[9]!!.toList().first { !knownNumbers[7]!!.toList().contains(it) && !knownLetters.values.contains(it) }

        // a=d, b=g, c=c, d=g, e=f, f=b, g=c
        // a=d, b=e, c=a, d=f, e=g, f=b, g=c

        val lol = knownLetters.toSortedMap()
        val lol2 = knownNumbers.toSortedMap()

        //lol.onEachIndexed { _, entry ->
        //    print("$entry, ")
        //}
        //println()
        //lol2.forEach { entry ->
        //    print("$entry, ")
        //}
        //println(outputPatterns)
        outputPatterns.forEach {
            outputNumbers.add(when (it.length) {
                2 -> 1
                3 -> 7
                4 -> 4
                7 -> 8
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
                else -> throw RuntimeException("You fucked up!")
            })
        }
    }

    fun getOutputNumber(): Int = outputNumbers.joinToString("").toInt()

    fun amountOfDigitsInOutput(vararg numbers: Int, applyRule: (number: Number, code: String) -> Boolean): Int {
        return numbers.sumOf { number ->
            outputPatterns.count {
                applyRule(Number.findByInt(number), it)
            }
        }
    }

    enum class Number(val actual: Int, val letterCount: Int, letterCode: CharArray = CharArray(0)) {
        ZERO(actual = 0, letterCount = 6, "abcef"),
        ONE(actual = 1, letterCount = 2),
        TWO(actual = 2, letterCount = 5, "acdeg"),
        THREE(actual = 3, letterCount = 5, "acdfg"),
        FOUR(actual = 4, letterCount = 4),
        FIVE(actual = 5, letterCount = 5, "abdfg"),
        SIX(actual = 6, letterCount = 6, "abdefg"),
        SEVEN(actual = 7, letterCount = 3),
        EIGHT(actual = 8, letterCount = 7),
        NINE(actual = 9, letterCount = 6, "abcdfg");

        constructor(actual: Int, letterCount: Int, letterCode: String)
                : this(actual, letterCount, letterCode.toList().toCharArray())

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
                signalPatterns = first.split(' '),
                outputPatterns = last.split(' ')
            )
        }
    }
}