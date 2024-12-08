package io.nozemi.aoc.solutions.year2024.day07

interface Operator {
    fun call(numbers: List<Long>): Long
}

class Plus : Operator {
    override fun call(numbers: List<Long>) = numbers.sum()
}

class Multiply : Operator {
    override fun call(numbers: List<Long>) = numbers.reduce { a, b -> a * b }
}

class Concatenate : Operator {
    override fun call(numbers: List<Long>) = numbers.joinToString("").toLong()
}