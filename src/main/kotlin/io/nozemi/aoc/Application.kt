package io.nozemi.aoc

import com.github.michaelbull.logging.InlineLogger
import io.github.classgraph.ClassGraph
import java.time.Year

private val logger = InlineLogger()

class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            var year = "${Year.now().value}"
            if (args.isNotEmpty() && args[0].isNotBlank()) {
                year = args[0]
            }

            if (year.contains(",")) {
                year.replace(" ", "")
                    .split(",").forEach {
                        executeApplication(it.toInt())
                    }
                return
            }

            executeApplication(year.toInt())
        }

        private fun executeApplication(year: Int) {
            logger.warn { "==== Solutions for $year ====" }
            logger.warn { "============================" }

            val classes = ClassGraph()
                .enableAllInfo()
                .acceptPackages("io.nozemi.aoc.year$year")
                .scan().getSubclasses(Puzzle::class.java)

            if (classes.size == 0) {
                logger.error { "No puzzles to solve for $year." }
            } else {
                classes.forEach {
                    logger.warn { "==   Solution for ${it.simpleName}   ==" }
                    val instance: Puzzle<Any> = it.loadClass()
                        .getConstructor(Int::class.java, String::class.java)
                        .newInstance(year, "") as Puzzle<Any>
                    instance.execute()
                }
            }
        }
    }
}