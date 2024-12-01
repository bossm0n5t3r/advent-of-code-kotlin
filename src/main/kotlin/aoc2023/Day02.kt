package aoc2023

import utils.getInputFile
import utils.verify

/**
 * --- Day 2: Cube Conundrum ---
 */
fun main() {
    fun part1(
        redCubes: Int,
        greenCubes: Int,
        blueCubes: Int,
    ): Int {
        return getInputFile().readLines().sumOf { line ->
            val splitLine = line.split(':', ';', ',').map { it.trim().split(" ") }
            val id = splitLine.first().last().toInt()
            splitLine.drop(1).forEach { (count, color) ->
                when (color) {
                    "red" -> if (count.toInt() > redCubes) return@sumOf 0
                    "green" -> if (count.toInt() > greenCubes) return@sumOf 0
                    "blue" -> if (count.toInt() > blueCubes) return@sumOf 0
                }
            }
            id
        }
    }

    fun part2(): Int =
        getInputFile().readLines().sumOf { line ->
            line
                .split(':', ';', ',')
                .map { it.trim().split(" ") }
                .drop(1)
                .groupBy { it.last() }
                .map { (_, values) ->
                    values
                        .maxOfOrNull { it.first().toInt() }
                        ?: 1
                }.let {
                    it.reduce { acc, i -> acc * i }
                }
        }

    (part1(12, 13, 14) to 2563).verify()
    (part2() to 70768).verify()
}
