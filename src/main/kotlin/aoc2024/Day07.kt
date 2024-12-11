package aoc2024

import utils.solve

/**
 * --- Day 7: Bridge Repair ---
 */
object Day07 {
    private fun dfs(
        candidates: List<Long>,
        expectResult: Long,
        cur: Long,
        idx: Int,
        includeConcat: Boolean = false,
    ): Long {
        if (idx == candidates.size) {
            return if (cur == expectResult) cur else 0
        }
        val candidate = candidates[idx]
        return maxOf(
            dfs(candidates, expectResult, cur + candidate, idx + 1, includeConcat),
            dfs(candidates, expectResult, cur * candidate, idx + 1, includeConcat),
            if (includeConcat) dfs(candidates, expectResult, "$cur$candidate".toLong(), idx + 1, true) else 0,
        )
    }

    private fun String.getCalibrationResult(): Long {
        val (rawExpectResult, rawCandidates) = split(": ").map { it.trim() }
        val expectResult = rawExpectResult.toLong()
        val candidates = rawCandidates.split(" ").map { it.toLong() }
        return dfs(candidates, expectResult, candidates[0], 1)
    }

    fun part1() =
        solve(2024) { lines ->
            lines.sumOf { it.getCalibrationResult() }
        }

    private fun String.getCalibrationResultWithConcat(): Long {
        val (rawExpectResult, rawCandidates) = split(": ").map { it.trim() }
        val expectResult = rawExpectResult.toLong()
        val candidates = rawCandidates.split(" ").map { it.toLong() }
        return dfs(candidates, expectResult, candidates[0], 1, true)
    }

    fun part2() =
        solve(2024) { lines ->
            lines.sumOf { line -> line.getCalibrationResultWithConcat() }
        }
}
