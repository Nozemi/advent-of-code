package io.nozemi.aoc.solutions.year2024.day05

import io.nozemi.aoc.types.puzzle.Puzzle
import io.nozemi.aoc.utils.takeFirst
import kotlin.reflect.KFunction0

private val example = """
    47|53
    97|13
    97|61
    97|47
    75|29
    61|13
    75|53
    29|13
    97|29
    53|29
    61|53
    97|53
    61|29
    47|13
    75|47
    97|75
    47|61
    75|61
    47|29
    75|13
    53|13

    75,47,61,53,29
    97,61,53,29,13
    75,29,13
    75,97,47,61,53
    61,13,29
    97,13,75,29,47
""".trimIndent()

fun main() {
    PrintQueue(example).printAnswers()
}

class PrintQueue(input: String) : Puzzle<Pair<Map<Int, List<Int>>, List<List<Int>>>>(input) {

    override fun Sequence<String>.parse(): Pair<Map<Int, MutableList<Int>>, List<List<Int>>> {
        val updates = mutableListOf<List<Int>>()
        val orderRules = mutableMapOf<Int, MutableList<Int>>()

        this.forEach {

            when {
                it.contains('|') -> {
                    val (before, after) = it.split('|').map(String::toInt)
                    val list = orderRules[after] ?: mutableListOf()
                    if (!list.contains(before)) list.add(before)

                    orderRules[after] = list
                }

                it.contains(',') -> updates.add(it.split(',').map(String::toInt))
            }
        }

        return Pair(orderRules, updates)
    }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1, ::part2
    )

    private val orderingRules get() = parsedInput.first
    private val updates get() = parsedInput.second

    private val alreadySortedUpdates
        get() = updates.mapNotNull { update ->
            val orderedPage = mutableListOf<Int>()

            update.forEach { page ->
                val shouldComeAfter = orderingRules[page]?.filter { update.contains(it) }
                if (shouldComeAfter.isNullOrEmpty() || orderedPage.containsAll(shouldComeAfter))
                    orderedPage.add(page)
            }

            if (orderedPage.size == update.size) return@mapNotNull orderedPage

            return@mapNotNull null
        }

    private val unsortedUpdatesSorted
        get() = updates.filter {
            !alreadySortedUpdates.contains(it)
        }.map { update ->
            val remainingPages = update.toMutableList()
            val orderedPage = mutableListOf<Int>()

            while (remainingPages.isNotEmpty()) {
                val page = remainingPages.takeFirst()

                val shouldComeAfter = orderingRules[page]?.filter { update.contains(it) }
                if (shouldComeAfter.isNullOrEmpty() || orderedPage.containsAll(shouldComeAfter)) {
                    orderedPage.add(page)
                } else remainingPages.add(page)
            }

            return@map orderedPage
        }

    private fun part1() = alreadySortedUpdates.sumOf { it[it.size / 2] }

    private fun part2() = unsortedUpdatesSorted.sumOf { it[it.size / 2] }
}