package aoc2022

import utils.readInputForEachLine
import utils.verify

/**
 * --- Day 4: Camp Cleanup ---
 */
fun main() {
    fun part1(): Int {
        var result = 0
        "2022/Day04.txt".readInputForEachLine { line ->
            val (firstSections, secondSections) =
                line.split(",").map { sectionPair ->
                    val (startSection, endSection) = sectionPair.split("-").map { intStr -> intStr.toInt() }
                    (startSection..endSection).toSet()
                }
            if (firstSections.containsAll(secondSections) || secondSections.containsAll(firstSections)) {
                result++
            }
        }
        return result
    }

    fun part2(): Int {
        var result = 0
        "2022/Day04.txt".readInputForEachLine { line ->
            val (firstSections, secondSections) =
                line.split(",").map { sectionPair ->
                    val (startSection, endSection) = sectionPair.split("-").map { intStr -> intStr.toInt() }
                    (startSection..endSection).toSet()
                }
            if (firstSections.intersect(secondSections).isNotEmpty()) {
                result++
            }
        }
        return result
    }

    (part1() to 576).verify()
    (part2() to 905).verify()
}
