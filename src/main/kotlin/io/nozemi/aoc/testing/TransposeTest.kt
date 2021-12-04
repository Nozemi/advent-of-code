package io.nozemi.aoc.testing

import com.github.michaelbull.logging.InlineLogger
import java.util.*

private val logger = InlineLogger()

class TransposeTest {


    private val input = listOf(
        "011000110100",
        "000010010001",
        "101010101110",
        "111100111111",
        "000010100010",
        "011010111101"
    )

    private inline fun <reified T> Array<T>.append(value: T): Array<T> {
        val list = this.toMutableList()
        list.add(value)
        return list.toTypedArray()
    }

    private fun List<String>.createMatrix(): Array<IntArray> {
        return this.map { entry -> entry.toCharArray().map { bit -> bit.digitToInt() }.toIntArray() }.toTypedArray()
    }

    private fun display(matrix: Array<IntArray>) {
        println("The matrix is: ")
        for (row in matrix) {
            for (column in row) {
                print("$column    ")
            }
            println()
        }
    }

    //companion object {

    //    @JvmStatic
    //    fun main(args: Array<String>) {
    //        TransposeTest().execute()
    //    }
    //}
}