package io.nozemi.aoc.solutions.year2024.day05

import io.nozemi.aoc.puzzle.Puzzle
import java.util.*
import kotlin.collections.ArrayDeque
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
    PrintQueue(example)
        .printAnswers()
}

class PrintQueue(input: String) : Puzzle<Pair<List<Pair<Int, Int>>, List<List<Int>>>>(input) {

    override fun Sequence<String>.parse(): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val updates = mutableListOf<List<Int>>()
        val order = mutableListOf<Pair<Int, Int>>()

        this.forEach {
            if (it.contains('|')) {
                val pages = it.split('|').map(String::toInt)
                order.add(Pair(pages[1], pages[0]))
            } else if (it.contains(',')) {
                updates.add(it.split(',').map(String::toInt))
            }
        }

        return Pair(order, updates)
    }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private val orderingRules get() = parsedInput.first
    private val updates get() = parsedInput.second

    private val correctlyOrderedUpdates
        get(): List<List<Int>> {
            val updated = mutableListOf<List<Int>>()

            updates.forEach { update ->
                val printed = mutableListOf<Int>()

                update.forEach { page ->
                    val mustBeBefore = orderingRules.filter { it.first == page }
                        .map { it.second }
                        .filter { update.contains(it) }
                        .toList()

                    if (mustBeBefore.isEmpty() || printed.containsAll(mustBeBefore)) {
                        printed.add(page)
                    }
                }

                if (printed.size == update.size)
                    updated.add(update)
            }

            return updated
        }

    private fun part1(): Int {
        return correctlyOrderedUpdates.sumOf { it[it.size / 2] }
    }

    private fun part2(): Int {
        val updated = mutableListOf<List<Int>>()

        updates.filter { !correctlyOrderedUpdates.contains(it) }
            .forEach { update ->
                val queue: ArrayDeque<Int> = ArrayDeque(update)
                val printed = mutableListOf<Int>()

                while (queue.isNotEmpty()) {
                    val page = queue.removeFirst()
                    val mustBeBefore = orderingRules.filter { it.first == page }
                        .map { it.second }
                        .filter { update.contains(it) }
                        .toList()

                    if (mustBeBefore.isEmpty() || printed.containsAll(mustBeBefore)) {
                        printed.add(page)
                    } else queue.add(page)

                }

                updated.add(printed)
            }

        return updated.sumOf { it[it.size / 2] }
    }
}