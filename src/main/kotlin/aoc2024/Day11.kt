package aoc2024

import utils.solve

/**
 * --- Day 11: Plutonian Pebbles ---
 */
object Day11 {
    private fun Long.splitInTwo(): List<Long> {
        val whole = toString()
        return listOf(
            whole.substring(0, whole.length / 2).toLong(),
            whole.substring(whole.length / 2).toLong(),
        )
    }

    private fun Long.blink() =
        when {
            this == 0L -> listOf(1L)
            this.toString().length % 2 == 0 -> this.splitInTwo()
            else -> listOf(this * 2024)
        }

    private data class CacheKey(
        val stone: Long,
        val i: Int,
    )

    private fun countStones(
        cache: MutableMap<CacheKey, Long>,
        stone: Long,
        i: Int,
        limit: Int,
    ): Long =
        cache.getOrPut(CacheKey(stone, i)) {
            if (i == limit) 1L else stone.blink().sumOf { countStones(cache, it, i + 1, limit) }
        }

    fun part1() =
        solve(2024) { lines ->
            val stones = lines.first().split(" ").map { it.toLong() }
            val cache = mutableMapOf<CacheKey, Long>()
            stones.sumOf { countStones(cache, it, 0, 25) }
        }

    fun part2() =
        solve(2024) { lines ->
            val stones = lines.first().split(" ").map { it.toLong() }
            val cache = mutableMapOf<CacheKey, Long>()
            stones.sumOf { countStones(cache, it, 0, 75) }
        }
}
