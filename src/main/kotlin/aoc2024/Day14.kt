package aoc2024

import utils.solve

/**
 * --- Day 14: Restroom Redoubt ---
 */
object Day14 {
    private const val MAX_ROW = 103
    private const val MID_ROW = MAX_ROW / 2
    private const val MAX_COLUMN = 101
    private const val MID_COLUMN = MAX_COLUMN / 2

    private data class Robot(
        var r: Int,
        var c: Int,
        val dr: Int,
        val dc: Int,
    ) {
        fun move() {
            r += dr
            c += dc
            while (r < 0) r += MAX_ROW
            while (c < 0) c += MAX_COLUMN
            while (r >= MAX_ROW) r %= MAX_ROW
            while (c >= MAX_COLUMN) c %= MAX_COLUMN
        }

        fun quadrant() =
            when {
                r < MID_ROW && c < MID_COLUMN -> 0
                r < MID_ROW && c > MID_COLUMN -> 1
                r > MID_ROW && c < MID_COLUMN -> 2
                r > MID_ROW && c > MID_COLUMN -> 3
                else -> -1
            }
    }

    private fun String.toRobot(): Robot {
        val (p, v) = split(" ")
        val (pc, pr) = p.substringAfter("p=").split(",").map { it.toInt() }
        val (vc, vr) = v.substringAfter("v=").split(",").map { it.toInt() }
        return Robot(pr, pc, vr, vc)
    }

    fun part1() =
        solve(2024) { lines ->
            val robots = lines.map { it.toRobot() }
            repeat(100) {
                robots.forEach { it.move() }
            }
            robots
                .map { it.quadrant() }
                .filter { it != -1 }
                .groupBy { it }
                .values
                .map { it.size }
                .reduce { acc, i -> acc * i }
        }

    private fun List<Robot>.isChristmasTree(): Boolean {
        val grid = Array(MAX_ROW) { CharArray(MAX_COLUMN) { '.' } }
        forEach { grid[it.r][it.c] = '#' }
        return grid.any { row -> row.concatToString().contains("####################") }
    }

    fun part2() =
        solve(2024) { lines ->
            val robots = lines.map { it.toRobot() }
            var time = 0
            while (true) {
                robots.forEach { it.move() }
                time++
                if (robots.isChristmasTree()) {
                    return@solve time
                }
                if (time > 100_000) {
                    break
                }
            }
            error("Christmas tree not found")
        }
}
