package io.nozemi.aoc

import com.github.michaelbull.logging.InlineLogger
import io.github.classgraph.ClassGraph
import java.time.Year

private val logger = InlineLogger()

class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            var year = Year.now().value

            if(args.isNotEmpty() && args[0].isNotBlank())
                year = args[0].toInt()

            logger.warn { "==== Solutions for $year ====" }
            logger.warn { "============================" }

            ClassGraph()
                .enableAllInfo()
                .acceptPackages("io.nozemi.aoc.year$year")
                .scan().getSubclasses(Solution::class.java).forEach {
                    logger.warn { "==   Solution for ${it.simpleName}   ==" }
                    it.loadClass().getConstructor(Int::class.java).newInstance(year)
                }
        }
    }
}