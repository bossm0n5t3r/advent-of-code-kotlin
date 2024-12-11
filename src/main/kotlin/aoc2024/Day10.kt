package aoc2024

import utils.solve
import java.util.LinkedList
import java.util.Queue

/**
 * --- Day 10: Hoof It ---
 */
object Day10 {
    private fun List<String>.toGrid() = this.map { line -> line.map { if (it == '.') -1 else it - '0' } }

    private val diff = listOf(0, 0, 1, -1).zip(listOf(1, -1, 0, 0))

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second

    private operator fun List<List<Int>>.contains(p: Pair<Int, Int>) = p.first in indices && p.second in this[0].indices

    private fun bfs(
        grid: List<List<Int>>,
        cur: Pair<Int, Int>,
        checkDuplicate: Boolean = true,
    ): Int {
        var result = 0
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
        val queue: Queue<Pair<Pair<Int, Int>, Int>> = LinkedList()
        queue.offer(cur to 0)
        while (queue.isNotEmpty()) {
            val (point, position) = queue.poll()
            visited.add(point to position)
            if (position == 9) {
                result++
                continue
            }
            for (i in 0 until 4) {
                val next = point + diff[i]
                val (nr, nc) = next
                if (next !in grid || grid[nr][nc] != position + 1 || visited.contains(next to position + 1)) {
                    continue
                }
                if (checkDuplicate) {
                    visited.add(next to position + 1)
                }
                queue.offer(next to position + 1)
            }
        }
        return result
    }

    fun part1() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val (m, n) = grid.size to grid[0].size
            var result = 0
            for (r in 0 until m) {
                for (c in 0 until n) {
                    if (grid[r][c] == 0) {
                        result += bfs(grid, r to c)
                    }
                }
            }
            result
        }

    fun part2() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val (m, n) = grid.size to grid[0].size
            var result = 0
            for (r in 0 until m) {
                for (c in 0 until n) {
                    if (grid[r][c] == 0) {
                        result += bfs(grid, r to c, false)
                    }
                }
            }
            result
        }
}
