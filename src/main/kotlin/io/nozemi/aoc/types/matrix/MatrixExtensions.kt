package io.nozemi.aoc.types.matrix

fun intMatrix(rows: Int, cols: Int, defaultValue: Int = 0) =
    IntMatrix(Array(rows) { IntArray(cols) { defaultValue } })

fun intMatrix(raw: String) =
    IntMatrix(raw.split("\n").map { row ->
        row.map { it.digitToInt() }.toIntArray()
    }.toTypedArray())

fun intMatrix(raw: Sequence<String>) =
    IntMatrix(raw.map {
        it.map { row ->
            row.digitToInt()
        }.toIntArray()
    }.toList().toTypedArray())

fun charMatrix(rows: Int, cols: Int, defaultValue: Char = Char.MIN_VALUE) =
    CharMatrix(Array(rows) { CharArray(cols) { defaultValue } })

fun charMatrix(raw: String) =
    CharMatrix(raw.split("\n").map { it.toCharArray() }.toTypedArray())

fun charMatrix(raw: Sequence<String>) =
    CharMatrix(raw.map { it.toCharArray() }.toList().toTypedArray())