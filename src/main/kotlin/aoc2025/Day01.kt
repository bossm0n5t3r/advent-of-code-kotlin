package aoc2025

import utils.solve

object Day01 {
    private val directionSign = mapOf("L" to -1, "R" to 1)

    private fun String.toSign() = requireNotNull(directionSign[this])

    private fun String.toSignAndDistance() = this.take(1).toSign() to this.substring(1).toInt()

    fun part1() =
        solve(2025) { lines ->
            lines
                .runningFold(50) { acc, line ->
                    val (sign, distance) = line.toSignAndDistance()
                    ((acc + distance * sign) + 100) % 100
                }.count { it == 0 }
        }

    fun part2() =
        solve(2025) { lines ->
            var result = 0
            lines.runningFold(50) { acc, line ->
                val (sign, distance) = line.toSignAndDistance()

                result += zerosDuring(acc, sign, distance)

                var tmp = acc + sign * distance
                while (tmp < 0) {
                    tmp += 100
                }
                tmp % 100
            }

            result
        }

    private fun zerosDuring(
        start: Int,
        sign: Int,
        dist: Int,
    ): Int {
        var remainingDistance =
            if (sign > 0) {
                (100 - start) % 100
            } else {
                start % 100
            }

        if (remainingDistance == 0) {
            remainingDistance = 100
        }

        return if (dist < remainingDistance) {
            0
        } else {
            1 + (dist - remainingDistance) / 100
        }
    }
}
