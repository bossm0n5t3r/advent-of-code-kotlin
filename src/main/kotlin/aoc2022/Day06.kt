package aoc2022

import utils.asInputStream
import utils.verify

/**
 * --- Day 6: Tuning Trouble ---
 */
fun main() {
    fun startOfMessageMarker(marker: Int): Int {
        val dataStreamBuffer = "2022/Day06.txt".asInputStream().bufferedReader().readText()
        (0 until dataStreamBuffer.length - marker).forEach {
            val tmp = dataStreamBuffer.slice(it until it + marker)
            if (tmp.toSet().size == marker) return it + marker
        }
        return -1
    }

    fun part1() = startOfMessageMarker(4)

    fun part2() = startOfMessageMarker(14)

    (part1() to 1210).verify()
    (part2() to 3476).verify()
}
