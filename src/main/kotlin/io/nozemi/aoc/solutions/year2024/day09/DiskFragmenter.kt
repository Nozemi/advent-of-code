package io.nozemi.aoc.solutions.year2024.day09

import io.nozemi.aoc.types.puzzle.Puzzle
import kotlin.math.max
import kotlin.math.min

fun main() {
    DiskFragmenter("2333133121414131402")
        .printAnswers()
}

class DiskFragmenter(input: String) : Puzzle<IntArray>(input) {

    override fun Sequence<String>.parse(): IntArray =
        this.joinToString("").map { it.digitToInt() }.toIntArray()

    override fun solutions() = listOf(
        ::part1,
        ::part2
    )

    private fun part1(): Long {
        val ffs = parsedInput.fragment()
        
        ffs.mapIndexed { index, i -> if (i == -1) index else null }
            .filterNotNull()
            .forEach { freeSlot ->
                val lastFileBlock = ffs.indexOfLast { it != -1 }
                ffs[freeSlot] = ffs[lastFileBlock]
                ffs[lastFileBlock] = -1
            }

        return ffs.filter { it != -1 }
            .mapIndexed { pos, id -> 1L * id * pos }
            .sum()
    }

    private fun part2(): Long {
        val ffs = parsedInput.fragment()
        
        fun findFreeSlots(): List<IntArray> {
            val freeGroups = mutableListOf<IntArray>()
            val currentList = mutableListOf<Int>()
            var previousId = Int.MIN_VALUE
            
            ffs.mapIndexed { index, i -> if (i == -1) index else null }
                .filterNotNull()
                .forEach { index ->
                    if (max(previousId, index) - min(previousId, index) == 1 || currentList.isEmpty())
                        currentList.add(index)
                    else {
                        freeGroups.add(currentList.toIntArray())
                        currentList.clear()
                        currentList.add(index)
                    }

                    previousId = index
                }
            
            return freeGroups
        }
        
        val fileIdIndexes = mutableMapOf<Int, MutableList<Int>>()
        
        ffs.forEachIndexed { index, i -> 
            val list = fileIdIndexes[i] ?: mutableListOf()
            list.add(index)
            fileIdIndexes[i] = list
        }
        
        fileIdIndexes.toSortedMap().reversed().forEach { (fileId, blocks) ->
            val freeSlots = findFreeSlots()
            val freeGroupIndex = freeSlots.indexOfFirst { it.size >= blocks.size && it.min() < blocks.min() }
            if (freeGroupIndex == -1)
                return@forEach
            
            val freeGroup = freeSlots[freeGroupIndex].toMutableList()
            
            blocks.forEach { 
                val slot = freeGroup.removeFirst()
                ffs[slot] = fileId
                ffs[it] = -1
            }
        }

        return ffs.mapIndexed { pos, id -> if (id == -1) 0 else 1L * id * pos }
            .sum()
    }
    
    private fun IntArray.fragment(): IntArray {
        val diskMap = mutableListOf<Int>()

        var isFile = true
        var fileId = 0
        this.forEach { block ->
            repeat(block) {
                diskMap.add(if (isFile) fileId else -1)
            }

            if (isFile) fileId++

            isFile = !isFile
        }

        return diskMap.toIntArray()
    }
}