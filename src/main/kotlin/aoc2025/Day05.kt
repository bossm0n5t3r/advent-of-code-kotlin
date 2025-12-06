package aoc2025

import utils.Problem
import utils.runProblem
import utils.solve
import java.util.TreeMap

object Day05 : Problem<Long> {
    private fun List<String>.toRawRangesAndRawNumbers(): Pair<List<String>, List<String>> =
        this
            .filter {
                it.isNotBlank()
            }.partition { it.contains("-") }

    private fun List<String>.toTreeMap(): TreeMap<Long, Long> {
        val ranges =
            this
                .map {
                    val (from, to) = it.split("-").map(String::toLong)
                    from to to
                }.sortedWith(compareBy<Pair<Long, Long>> { it.first }.thenBy { it.second })
        val treeMap = TreeMap<Long, Long>()
        for ((from, to) in ranges) {
            val floorEntry = treeMap.floorEntry(from)
            if (floorEntry == null) {
                treeMap[from] = to
                continue
            }
            if (floorEntry.value < from) {
                treeMap[from] = to
                continue
            }
            treeMap[floorEntry.key] = maxOf(floorEntry.value, to)
        }
        return treeMap
    }

    override fun part1(): Long =
        solve(2025) { lines ->
            val (ranges, numbers) = lines.toRawRangesAndRawNumbers()
            val treeMap = ranges.toTreeMap()
            numbers
                .count { numberString ->
                    val number = numberString.toLong()
                    treeMap.headMap(number).any { it.value >= number }
                }.toLong()
        }

    override fun part2(): Long =
        solve(2025) { lines ->
            val (ranges, _) = lines.toRawRangesAndRawNumbers()
            val treeMap = ranges.toTreeMap()
            treeMap
                .toList()
                .sumOf { it.second - it.first + 1 }
        }
}

fun main() {
    Day05.runProblem(707, 361615643045059)
}
