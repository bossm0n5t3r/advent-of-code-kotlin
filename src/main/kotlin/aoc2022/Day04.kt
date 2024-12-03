package aoc2022

import utils.solve

/**
 * --- Day 4: Camp Cleanup ---
 */
object Day04 {
    fun part1(): Int =
        solve(2022) { lines ->
            var result = 0
            lines.forEach { line ->
                val (firstSections, secondSections) =
                    line.split(",").map { sectionPair ->
                        val (startSection, endSection) = sectionPair.split("-").map { intStr -> intStr.toInt() }
                        (startSection..endSection).toSet()
                    }
                if (firstSections.containsAll(secondSections) || secondSections.containsAll(firstSections)) {
                    result++
                }
            }
            result
        }

    fun part2(): Int =
        solve(2022) { lines ->
            var result = 0
            lines.forEach { line ->
                val (firstSections, secondSections) =
                    line.split(",").map { sectionPair ->
                        val (startSection, endSection) = sectionPair.split("-").map { intStr -> intStr.toInt() }
                        (startSection..endSection).toSet()
                    }
                if (firstSections.intersect(secondSections).isNotEmpty()) {
                    result++
                }
            }
            result
        }
}
