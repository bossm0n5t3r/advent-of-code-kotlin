package aoc2025

import utils.Problem
import utils.runProblem
import utils.solve

object Day04 : Problem<Int> {
    private val allDirections =
        listOf(
            -1 to -1,
            -1 to 0,
            -1 to 1,
            0 to -1,
            0 to 1,
            1 to -1,
            1 to 0,
            1 to 1,
        )

    private fun List<String>.toGrid(): Array<CharArray> = this.map { it.toCharArray() }.toTypedArray()

    private fun Array<CharArray>.canBeAccessedByAForklift(
        r: Int,
        c: Int,
    ): Boolean {
        val (m, n) = this.size to this[0].size
        var rolls = 0
        for ((dr, dc) in allDirections) {
            val nr = r + dr
            val nc = c + dc
            if (nr in 0 until m && nc in 0 until n) {
                if (this[nr][nc] == '@') rolls++
            }
        }
        return rolls < 4
    }

    private fun Array<CharArray>.getAllRollsOfPaper(): Int {
        val (m, n) = this.size to this[0].size
        val updated = ArrayDeque<Pair<Int, Int>>()
        for (r in 0 until m) {
            for (c in 0 until n) {
                if (this[r][c] == '@' && this.canBeAccessedByAForklift(r, c)) {
                    updated.addFirst(r to c)
                }
            }
        }
        return updated.size.also {
            while (updated.isNotEmpty()) {
                val (r, c) = updated.removeFirst()
                this[r][c] = '.'
            }
        }
    }

    override fun part1(): Int =
        solve(2025) {
            it.toGrid().getAllRollsOfPaper()
        }

    private fun Array<CharArray>.removeAllRollsOfPaper(): Int {
        var result = 0
        var removed = this.getAllRollsOfPaper()
        while (true) {
            if (removed == 0) return result
            result += removed
            removed = getAllRollsOfPaper()
        }
    }

    override fun part2(): Int =
        solve(2025) {
            it.toGrid().removeAllRollsOfPaper()
        }
}

fun main() {
    Day04.runProblem(1523, 9290)
}
