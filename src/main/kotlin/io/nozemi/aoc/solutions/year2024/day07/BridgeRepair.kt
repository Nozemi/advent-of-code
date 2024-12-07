package io.nozemi.aoc.solutions.year2024.day07

import io.nozemi.aoc.puzzle.Puzzle

fun main() {
    BridgeRepair(
        """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent()
    ).printAnswers()
}

class BridgeRepair(input: String) : Puzzle<Map<Long, List<Long>>>(input) {

    override fun Sequence<String>.parse() = this.map {
        val (total, numbers) = it.split(':')

        return@map Pair(total.toLong(), numbers.split(' ').mapNotNull(String::toLongOrNull))
    }.toMap()

    override fun solutions() = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        var sum = 0L

        parsedInput.forEach calculationLoop@{ (total, numbers) ->
            if (numbers.sum() == total || numbers.reduce { a, b -> a * b } == total) {
                sum += total
                return@calculationLoop
            }

            if (numbers.size <= 2)
                return@calculationLoop

            val combos = generatePermutations(listOf(Operator.PLUS, Operator.MULTIPLY), numbers.size)

            combos.distinct().forEach { combo ->
                val thisTotal = numbers.reduceIndexed { index, a, b ->
                    val operator = combo[index]

                    if (operator == Operator.PLUS)
                        a + b
                    else a * b
                }

                if (thisTotal == total) {
                    sum += total
                    return@calculationLoop
                }
            }
        }

        return sum
    }

    private fun part2(): Long {
        var sum = 0L

        parsedInput.forEach calculationLoop@{ (total, numbers) ->
            if (numbers.sum() == total
                || numbers.reduce { a, b -> a * b } == total
                || numbers.joinToString("") { it.toString() }.toLong() == total
            ) {
                sum += total
                return@calculationLoop
            }

            if (numbers.size <= 2)
                return@calculationLoop

            val combos = generatePermutations(
                listOf(Operator.PLUS, Operator.MULTIPLY, Operator.CONCATENATION),
                numbers.size
            )

            combos.distinct().forEach { combo ->
                val thisTotal = numbers.reduceIndexed { index, a, b ->
                    val operator = combo[index]

                    if (operator == Operator.PLUS)
                        a + b
                    else if (operator == Operator.MULTIPLY)
                        a * b
                    else listOf(a, b).joinToString("") { it.toString() }.toLong()
                }

                if (thisTotal == total) {
                    sum += total
                    return@calculationLoop
                }
            }
        }

        return sum
    }

    private fun <T> generatePermutations(operators: List<T>, size: Int): List<List<T>> {
        if (size == 1) return operators.map { listOf(it) }

        val permutations = mutableListOf<List<T>>()
        for (op in operators) {
            val smallerPermutations = generatePermutations(operators, size - 1)
            for (perm in smallerPermutations) {
                permutations.add(listOf(op) + perm)
            }
        }
        return permutations
    }

    private enum class Operator {
        PLUS,
        MULTIPLY,
        CONCATENATION
    }
}