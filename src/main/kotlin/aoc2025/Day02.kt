package aoc2025

import utils.Problem
import utils.runProblem
import utils.solve

object Day02 : Problem<Long> {
    private fun Long.isSimpleInvalid(): Boolean = this.toString().let { it.take(it.length / 2).repeat(2) == it }

    private fun Pair<Long, Long>.findInvalidIds(filterFunction: (Long) -> Boolean): Long =
        (this.first..this.second)
            .filter {
                filterFunction.invoke(it)
            }.sum()

    override fun part1(): Long =
        solve(2025) { lines ->
            lines
                .first()
                .split(",")
                .sumOf { range ->
                    range
                        .split("-")
                        .map { it.toLong() }
                        .let { it[0] to it[1] }
                        .findInvalidIds { it.isSimpleInvalid() }
                }
        }

    private fun Long.isInvalid(): Boolean {
        val str = this.toString()
        var n = 2
        while (n <= str.length) {
            if (str.length % n == 0 && str.take(str.length / n).repeat(n) == str) {
                return true
            }
            n++
        }
        return false
    }

    override fun part2(): Long =
        solve(2025) { lines ->
            lines
                .first()
                .split(",")
                .sumOf { range ->
                    range
                        .split("-")
                        .map { it.toLong() }
                        .let { it[0] to it[1] }
                        .findInvalidIds { it.isInvalid() }
                }
        }
}

fun main() {
    Day02.runProblem(29940924880, 48631958998)
}
