package aoc2024

import utils.solve

/**
 * --- Day 4: Ceres Search ---
 */
object Day04 {
    private val direction =
        listOf(
            listOf(0, 0, 0, 0) to listOf(0, 1, 2, 3), // ->
            listOf(0, 1, 2, 3) to listOf(0, 0, 0, 0), // down
            listOf(0, 0, 0, 0) to listOf(0, -1, -2, -3), // <-
            listOf(0, -1, -2, -3) to listOf(0, 0, 0, 0), // up
            listOf(0, -1, -2, -3) to listOf(0, 1, 2, 3), // north-east
            listOf(0, 1, 2, 3) to listOf(0, 1, 2, 3), // east-south
            listOf(0, 1, 2, 3) to listOf(0, -1, -2, -3), // south-west
            listOf(0, -1, -2, -3) to listOf(0, -1, -2, -3), // west-north
        )

    private const val XMAS = "XMAS"

    private fun List<List<Char>>.count(
        m: Int,
        n: Int,
        r: Int,
        c: Int,
    ): Int {
        return direction.count label@{ (dr, dc) ->
            if (dr.all { it + r in 0 until m }.not()) return@label false
            if (dc.all { it + c in 0 until n }.not()) return@label false
            dr
                .map { it + r }
                .zip(dc.map { it + c })
                .map { this[it.first][it.second] }
                .joinToString("") == XMAS
        }
    }

    fun part1() =
        solve(2024) { lines ->
            val letters = lines.map { it.toList() }
            val (m, n) = letters.size to letters.first().size
            var result = 0L
            for (r in 0 until m) {
                for (c in 0 until n) {
                    if (letters[r][c] == 'X') {
                        result += letters.count(m, n, r, c)
                    }
                }
            }
            result
        }

    private val xShapeDirection = listOf(-1, -1, 0, 1, 1) to listOf(-1, 1, 0, -1, 1)
    private val xShapeStringSet = setOf("MSAMS", "MMASS", "SSAMM", "SMASM")

    fun part2() =
        solve(2024) { lines ->
            val letters = lines.map { it.toList() }
            val (m, n) = letters.size to letters.first().size
            var result = 0L
            for (r in 0 until m) {
                for (c in 0 until n) {
                    if (letters[r][c] == 'A') {
                        val (dr, dc) = xShapeDirection
                        if (dr.all { it + r in 0 until m }.not()) continue
                        if (dc.all { it + c in 0 until n }.not()) continue
                        val tmp =
                            dr
                                .map { it + r }
                                .zip(dc.map { it + c })
                                .map { letters[it.first][it.second] }
                                .joinToString("")
                        if (xShapeStringSet.contains(tmp)) result++
                    }
                }
            }
            result
        }
}
