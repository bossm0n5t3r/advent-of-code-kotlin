package aoc2022

import utils.asInputStream
import utils.verify

/**
 * --- Day 8: Treetop Tree House ---
 */
fun main() {
    val trees = "2022/Day8.txt"
        .asInputStream()
        .bufferedReader()
        .readLines()
        .map { it.toCharArray().map { c -> c.digitToInt() } }

    fun part1(): Int {
        val (m, n) = (trees.size to trees.first().size)
        val seenTrees = Array(m) { BooleanArray(n) { false } }
        (0 until m).forEach { r ->
            seenTrees[r][0] = true
            seenTrees[r][n - 1] = true

            var tmpMaxLeft = trees[r][0]
            var tmpMaxRight = trees[r][n - 1]

            for (c in 1 until n - 1) {
                if (trees[r][c] > tmpMaxLeft) {
                    seenTrees[r][c] = true
                }
                tmpMaxLeft = trees[r][c].coerceAtLeast(tmpMaxLeft)
            }

            for (c in n - 2 downTo 1) {
                if (trees[r][c] > tmpMaxRight) {
                    seenTrees[r][c] = true
                }
                tmpMaxRight = trees[r][c].coerceAtLeast(tmpMaxRight)
            }
        }

        (0 until n).forEach { c ->
            seenTrees[0][c] = true
            seenTrees[m - 1][c] = true

            var tmpMaxUp = trees[0][c]
            var tmpMaxDown = trees[m - 1][c]

            for (r in 1 until m - 1) {
                if (trees[r][c] > tmpMaxUp) {
                    seenTrees[r][c] = true
                }
                tmpMaxUp = trees[r][c].coerceAtLeast(tmpMaxUp)
            }

            for (r in m - 2 downTo 1) {
                if (trees[r][c] > tmpMaxDown) {
                    seenTrees[r][c] = true
                }
                tmpMaxDown = trees[r][c].coerceAtLeast(tmpMaxDown)
            }
        }

        return seenTrees.sumOf { row -> row.count { it } }
    }

    data class Tree(
        val r: Int,
        val c: Int,
    ) {
        private val height = trees[r][c]

        fun getScenicScore(m: Int, n: Int): Int {
            if (r == 0 || r == m - 1 || c == 0 || c == n - 1) return 0
            val seenTrees = IntArray(4) { 0 }

            // up
            for (nr in r - 1 downTo 0) {
                if (trees[nr][c] >= height) {
                    seenTrees[0]++
                    break
                }
                seenTrees[0]++
            }

            // left
            for (nc in c - 1 downTo 0) {
                if (trees[r][nc] >= height) {
                    seenTrees[1]++
                    break
                }
                seenTrees[1]++
            }

            // right
            for (nc in c + 1 until n) {
                if (trees[r][nc] >= height) {
                    seenTrees[2]++
                    break
                }
                seenTrees[2]++
            }

            // down
            for (nr in r + 1 until m) {
                if (trees[nr][c] >= height) {
                    seenTrees[3]++
                    break
                }
                seenTrees[3]++
            }

            return seenTrees.foldRight(1) { i: Int, acc: Int -> i * acc }
        }
    }

    fun part2(): Int {
        val (m, n) = (trees.size to trees.first().size)
        var result = 0
        (0 until m).forEach { r ->
            (0 until n).forEach { c ->
                result = result.coerceAtLeast(Tree(r, c).getScenicScore(m, n))
            }
        }
        return result
    }

    (part1() to 1669).verify()
    (part2() to 331344).verify()
}
