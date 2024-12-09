package io.nozemi.aoc.solutions.year2024.day09

import io.nozemi.aoc.puzzle.Puzzle

fun main() {
    DiskFragmenter("2333133121414131402")
        .printAnswers()
}

class DiskFragmenter(input: String) : Puzzle<IntArray>(input) {

    override fun Sequence<String>.parse(): IntArray =
        this.joinToString("").map { it.digitToInt() }.toIntArray()

    override fun solutions() =
        listOf(
            ::part1
        )

    private fun part1(): Long {
        val diskMap = mutableListOf<Int>()
        
        var isFile = true
        var fileId = 0
        parsedInput.forEach { block ->
            repeat(block) {
                diskMap.add(if (isFile) fileId else -1)
            }
            
            if (isFile) fileId++
            
            isFile = !isFile
        }
        
        diskMap.mapIndexedNotNull { index, i -> if (i == -1) index else null }
            .forEach { freeSlot ->
                val lastFileBlock = diskMap.indexOfLast { it != -1 }
                diskMap[freeSlot] = diskMap[lastFileBlock]
                diskMap[lastFileBlock] = -1
            }

        return diskMap.filter { it != -1 }
            .mapIndexed { pos, id -> 1L * id * pos }
            .sum()
    }
}