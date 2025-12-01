package aoc2025

import utils.solve

object Day01 {
    fun part1() =
        solve(2025) { lines ->
            lines
                .runningFold(50) { acc, line ->
                    val (rotation, distance) = (if (line.take(1) == "L") -1 else 1) to line.substring(1).toInt()
                    ((acc + distance * rotation) + 100) % 100
                }.count { it == 0 }
        }

    fun part2() =
        solve(2025) { lines ->
            var result = 0
            lines.runningFold(50) { acc, line ->
                val (rotation, distance) =
                    (if (line.take(1) == "L") -1 else 1) to line.substring(1).toInt()

                result += zerosDuring(acc, rotation, distance)

                var tmp = acc + rotation * distance
                while (tmp < 0) {
                    tmp += 100
                }
                tmp % 100
            }

            result
        }

    private fun zerosDuring(
        start: Int,
        dir: Int,
        dist: Int,
    ): Int {
        var remainingDistance =
            if (dir > 0) {
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
