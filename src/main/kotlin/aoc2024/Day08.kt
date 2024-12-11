package aoc2024

import utils.solve

/**
 * --- Day 8: Resonant Collinearity ---
 */
object Day08 {
    private fun List<String>.toGrid() = this.map { it.toCharArray() }.toTypedArray()

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

    private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = first - other.first to second - other.second

    private operator fun Array<CharArray>.contains(p: Pair<Int, Int>) = p.first in indices && p.second in this[0].indices

    private fun Array<CharArray>.getAntennaMap() =
        mutableMapOf<Char, MutableSet<Pair<Int, Int>>>().also { antennaMap ->
            for (r in indices) {
                for (c in this[0].indices) {
                    if (this[r][c].isLetterOrDigit()) {
                        antennaMap.getOrPut(this[r][c]) { mutableSetOf() }.add(r to c)
                    }
                }
            }
        }

    fun part1() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val antennaMap = grid.getAntennaMap()
            val result = mutableSetOf<Pair<Int, Int>>()
            for (collection in antennaMap.values) {
                if (collection.size < 2) continue
                val tmp = collection.toList()
                for (i in 0 until tmp.size - 1) {
                    for (j in i + 1 until tmp.size) {
                        val vector = tmp[i] - tmp[j]
                        if (tmp[i] + vector in grid) {
                            result.add(tmp[i] + vector)
                        }
                        if (tmp[j] - vector in grid) {
                            result.add(tmp[j] - vector)
                        }
                    }
                }
            }
            result.size
        }

    fun part2() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val antennaMap = grid.getAntennaMap()
            val result = mutableSetOf<Pair<Int, Int>>()
            for (collection in antennaMap.values) {
                if (collection.size < 2) continue
                val tmp = collection.toList()
                for (i in 0 until tmp.size - 1) {
                    for (j in i + 1 until tmp.size) {
                        result.add(tmp[i])
                        result.add(tmp[j])
                        val vector = tmp[i] - tmp[j]
                        var next = tmp[i] + vector
                        while (next in grid) {
                            result.add(next)
                            next += vector
                        }
                        next = tmp[j] - vector
                        while (next in grid) {
                            result.add(next)
                            next -= vector
                        }
                    }
                }
            }
            result.size
        }
}
