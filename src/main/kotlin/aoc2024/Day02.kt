package aoc2024

import utils.solve

/**
 * --- Day 2: Red-Nosed Reports ---
 */
object Day02 {
    fun part1() =
        solve(2024) { lines ->
            lines.count { it.isSafe() }
        }

    private fun String.isSafe(): Boolean =
        this
            .toIntList()
            .isSafe()

    private fun String.toIntList() = this.split(" ").map { it.toInt() }

    private fun List<Int>.isSafe(): Boolean {
        val sign = this.last() > this.first()
        return this
            .windowed(2)
            .let { chunked ->
                val diff = chunked.map { (r1, r2) -> r2 - r1 }
                diff.all { (it > 0) == sign && (if (sign) it else -it) in 1..3 }
            }
    }

    fun part2() =
        solve(2024) { lines ->
            lines.count { it.isSafeWithTolerate() }
        }

    private fun String.isSafeWithTolerate(): Boolean {
        if (this.isSafe()) return true
        val levels = this.toIntList()
        for (i in levels.indices) {
            if (levels.filterIndexed { index, _ -> index != i }.isSafe()) {
                return true
            }
        }
        return false
    }
}
