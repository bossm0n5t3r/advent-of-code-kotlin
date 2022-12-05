package aoc2022

import utils.PuzzleInput
import utils.readInputForEachLine

/**
 * --- Day 4: Camp Cleanup ---
 */
fun main() {
    fun part1(puzzleInput: PuzzleInput): Int {
        var result = 0
        puzzleInput.readInputForEachLine { line ->
            val (firstSections, secondSections) = line.split(",").map { sectionPair ->
                val (startSection, endSection) = sectionPair.split("-").map { intStr -> intStr.toInt() }
                (startSection..endSection).toSet()
            }
            if (firstSections.containsAll(secondSections) || secondSections.containsAll(firstSections)) {
                result++
            }
        }
        return result
    }

    fun part2(puzzleInput: PuzzleInput): Int {
        var result = 0
        puzzleInput.readInputForEachLine { line ->
            val (firstSections, secondSections) = line.split(",").map { sectionPair ->
                val (startSection, endSection) = sectionPair.split("-").map { intStr -> intStr.toInt() }
                (startSection..endSection).toSet()
            }
            if (firstSections.intersect(secondSections).isNotEmpty()) {
                result++
            }
        }
        return result
    }

    val puzzleInput = PuzzleInput(2022, "Day4.txt")
    part1(puzzleInput).run {
        println(this)
        require(this == 576)
    }
    part2(puzzleInput).run {
        println(this)
        require(this == 905)
    }
}
