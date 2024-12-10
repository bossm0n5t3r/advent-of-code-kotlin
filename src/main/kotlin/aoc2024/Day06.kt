package aoc2024

import utils.solve

/**
 * --- Day 6: Guard Gallivant ---
 */
object Day06 {
    private fun List<String>.toGrid() = this.map { it.toCharArray() }.toTypedArray()

    private val diff = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

    private fun Array<CharArray>.getStart(): Pair<Int, Int> {
        val (m, n) = this.size to this.first().size
        for (r in 0 until m) {
            for (c in 0 until n) {
                if (this[r][c] == '^') return r to c
            }
        }
        error("No start found")
    }

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second

    private fun Pair<Int, Int>.next(direction: Int): Pair<Int, Int> = this + diff[direction]

    private fun justWalk(
        grid: Array<CharArray>,
        startPoint: Pair<Int, Int>,
        checkLoop: Boolean = false,
    ): Set<Pair<Pair<Int, Int>, Int>> {
        val (m, n) = grid.size to grid.first().size
        var cur = startPoint
        var direction = 0 // (north, east, south, west) = (0, 1, 2, 3)
        val location = mutableSetOf(cur to 0)
        while (true) {
            val next = cur.next(direction)
            if (next.first < 0 || next.first >= m || next.second < 0 || next.second >= n) break
            if (grid[next.first][next.second] == '#') {
                direction = (direction + 1) % 4
                continue
            }
            cur = next
            if (checkLoop && cur to direction in location) return emptySet()
            location.add(cur to direction)
        }
        return location
    }

    fun part1() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val (m, n) = grid.size to grid.first().size
            val startPoint = grid.getStart()
            val visited = justWalk(grid, startPoint)
            visited.map { it.first }.toSet().size
        }

    fun part2() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val (m, n) = grid.size to grid.first().size
            val startPoint = grid.getStart()
            val candidates = justWalk(grid, startPoint).map { it.first }.toSet()
            (candidates - startPoint).count { obstruction ->
                grid[obstruction.first][obstruction.second] = '#'
                justWalk(grid, startPoint, true).isEmpty().also { grid[obstruction.first][obstruction.second] = '.' }
            }
        }
}
