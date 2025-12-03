package aoc2025

import utils.Problem
import utils.runProblem
import utils.solve

object Day03 : Problem<Long> {
    private fun String.getLargestJoltagePossible(batteries: Int): Long {
        val n = this.length
        require(batteries in 1..n)

        var remove = n - batteries
        val stack = ArrayDeque<Char>(n)

        for (ch in this) {
            while (remove > 0 && stack.isNotEmpty() && stack.last() < ch) {
                stack.removeLast()
                remove--
            }
            stack.addLast(ch)
        }

        repeat(remove) {
            stack.removeLast()
        }

        return stack.take(batteries).joinToString("").toLong()
    }

    override fun part1(): Long =
        solve(2025) { lines ->
            lines.sumOf { line -> line.getLargestJoltagePossible(2) }
        }

    override fun part2(): Long =
        solve(2025) { lines ->
            lines.sumOf { line -> line.getLargestJoltagePossible(12) }
        }
}

fun main() {
    Day03.runProblem(17383, 172601598658203)
}
