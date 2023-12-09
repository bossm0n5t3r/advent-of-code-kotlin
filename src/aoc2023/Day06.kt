package aoc2023

import utils.solve
import utils.verify

/**
 * --- Day 6: Wait For It ---
 */
fun main() {
    val inputFile = "2023/Day06.txt"

    fun travelDistance(
        time: Long,
        hold: Long,
    ) = hold * (time - hold)

    fun part1(): Int {
        return solve(inputFile) { lines ->
            val times =
                lines.first().substringAfter(":")
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            val distances =
                lines.last().substringAfter(":")
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            times.zip(distances)
        }.map {
            (1..it.first).count { hold ->
                travelDistance(it.first.toLong(), hold.toLong()) >= it.second
            }
        }
            .fold(1) { acc, i -> acc * i }
    }

    fun part2(): Int {
        return solve(inputFile) { lines ->
            val time =
                lines.first().substringAfter(":")
                    .replace(" ", "")
                    .toLong()
            val distance =
                lines.last().substringAfter(":")
                    .replace(" ", "")
                    .toLong()
            time to distance
        }.let {
            (1..it.first).count { hold ->
                travelDistance(it.first, hold) >= it.second
            }
        }
    }

    (part1() to 114400).verify()
    (part2() to 21039729).verify()
}
