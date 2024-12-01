package aoc2023

import utils.getInputFile
import utils.verify

/**
 * --- Day 1: Trebuchet?! ---
 */
fun main() {
    fun part1(): Int =
        getInputFile().readLines().sumOf { line ->
            line
                .filter { it.isDigit() }
                .let { "${it.first()}${it.last()}".toInt() }
        }

    fun part2(): Int {
        val numbers =
            listOf(
                "zero",
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
            )
        return getInputFile().readLines().sumOf { line ->
            val indexToInt =
                numbers.mapIndexed { index, number ->
                    line.indexOf(number) to index
                } +
                    numbers.mapIndexed { index, number ->
                        line.lastIndexOf(number) to index
                    } +
                    line.mapIndexed { index, c ->
                        if (c.isDigit()) {
                            index to c.digitToInt()
                        } else {
                            -1 to -1
                        }
                    }

            indexToInt
                .filter { it.first != -1 }
                .sortedBy { it.first }
                .let { it.first().second * 10 + it.last().second }
        }
    }

    (part1() to 55477).verify()
    (part2() to 54431).verify()
}
