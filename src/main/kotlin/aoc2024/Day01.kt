package aoc2024

import utils.solve
import kotlin.math.abs

/**
 * --- Day 1: Historian Hysteria ---
 */
object Day01 {
    fun part1() =
        solve { lines ->
            val (left, right) =
                lines
                    .map { line ->
                        line
                            .split(Regex("\\s+"))
                            .map { it.toInt() }
                            .let { it.first() to it.last() }
                    }.unzip()
            left
                .sorted()
                .zip(right.sorted())
                .sumOf { abs(it.first - it.second) }
        }

    fun part2() =
        solve { lines ->
            val (left, right) =
                lines
                    .map { line ->
                        line
                            .split(Regex("\\s+"))
                            .map { it.toLong() }
                            .let { it.first() to it.last() }
                    }.unzip()

            val frequencies = right.groupingBy { it }.eachCount()

            left.sumOf { number -> number * frequencies.getOrDefault(number, 0) }
        }
}
