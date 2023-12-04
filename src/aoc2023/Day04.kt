package aoc2023

import utils.readInput
import utils.verify
import java.math.BigInteger

/**
 * --- Day 4: Scratchcards ---
 */
fun main() {
    val inputFile = "2023/Day04.txt"

    fun String.toSet() =
        this
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.trim().toInt() }
            .toSet()

    fun part1(): Long {
        return inputFile.readInput().sumOf { line ->
            line.split(":", "|")
                .drop(1)
                .let { (winningNumbers, numbers) ->
                    winningNumbers.toSet().intersect(numbers.toSet())
                        .size
                }
                .let { size ->
                    (size - 1)
                        .takeIf { it >= 0 }
                        ?.let { BigInteger.valueOf(2).pow(it) }
                        ?: BigInteger.ZERO
                }
                .toLong()
        }
    }

    fun part2(): Int {
        val result = mutableMapOf<Int, Int>()
        inputFile.readInput().forEachIndexed { index, line ->
            val cardIndex = index + 1
            val currentCards = result.getOrDefault(cardIndex, 0) + 1
            result[cardIndex] = currentCards

            line.split(":", "|")
                .drop(1)
                .let { (winningNumbers, numbers) ->
                    winningNumbers.toSet().intersect(numbers.toSet())
                        .size
                }
                .let {
                    for (i in 1..it) {
                        result[cardIndex + i] = result.getOrDefault(cardIndex + i, 0) + currentCards
                    }
                }
        }

        return result.values.sum()
    }

    (part1() to 24542L).verify()
    (part2() to 8736438).verify()
}
