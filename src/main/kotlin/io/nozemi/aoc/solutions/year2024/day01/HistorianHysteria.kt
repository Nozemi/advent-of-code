package io.nozemi.aoc.solutions.year2024.day01

import io.nozemi.aoc.types.puzzle.Puzzle
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KFunction0

fun main() {
    HistorianHysteria(
        """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()
    ).printAnswers()
}

class HistorianHysteria(input: String) : Puzzle<List<List<Int>>>(input) {

    override fun Sequence<String>.parse(): List<List<Int>> {
        val pattern = Regex(" +")
        
        return this.map { line ->
            pattern.replace(line, " ").split(" ")
                .map { it.toInt() }
        }.toList()
    }

    override fun solutions(): List<KFunction0<Any>> = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        
        parsedInput.forEach { 
            left.add(it.first())
            right.add(it.last())
        }
        
        val sortedL = left.sorted()
        val sortedR = right.sorted()
        
        val distances = mutableListOf<Int>()
        for (i in sortedL.indices) {
            distances.add(max(sortedL[i], sortedR[i]) - min(sortedL[i], sortedR[i]))
        }
        
        return distances.sum()
    }
    
    private fun part2(): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        parsedInput.forEach {
            left.add(it.first())
            right.add(it.last())
        }
        
        var score = 0
        left.forEach { leftId ->
            score += leftId * right.count { it == leftId }
        }
        
        return score
    }
}