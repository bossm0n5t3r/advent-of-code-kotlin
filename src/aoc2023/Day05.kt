package aoc2023

import utils.solve
import utils.verify

/**
 * --- Day 5: If You Give A Seed A Fertilizer ---
 */
fun main() {
    val inputFile = "2023/Day05.txt"
    val whitespace = " "
    val newLine = "\n"

    data class Mapping(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
        fun contains(target: Long) = sourceRangeStart <= target && target < sourceRangeStart + rangeLength

        fun toDestination(source: Long): Long {
            return source - sourceRangeStart + destinationRangeStart
        }
    }

    fun List<String>.toMappingsList() =
        this
            .drop(2)
            .joinToString(newLine)
            .split("$newLine$newLine")
            .map { section ->
                section.lines().drop(1).map { line ->
                    line.split(whitespace).map { it.toLong() }.let { (dest, src, length) ->
                        Mapping(dest, src, length)
                    }
                }
            }

    fun part1() =
        solve(inputFile) { lines ->
            val seeds =
                lines
                    .first()
                    .substringAfter(whitespace)
                    .split(whitespace)
                    .map { it.toLong() }

            val mappingsList = lines.toMappingsList()

            seeds.minOf { seed ->
                mappingsList.fold(seed) { acc, mappings ->
                    mappings.firstOrNull { it.contains(acc) }
                        ?.toDestination(acc)
                        ?: acc
                }
            }
        }

    fun part2() =
        solve(inputFile) { lines ->
            data class Seed(val start: Long, val range: Long) {
                val seedRange = start..<(start + range)
            }

            val seeds =
                lines
                    .first()
                    .substringAfter(whitespace)
                    .split(whitespace)
                    .map { it.toLong() }
                    .chunked(2)
                    .map { Seed(it.first(), it.last()) }

            val mappingsList = lines.toMappingsList()

            seeds
                .flatMap { seed ->
                    mappingsList.fold(listOf(seed.seedRange)) { acc, mappings ->
                        acc.flatMap {
                            mappings.mapNotNull { (dest, src, range) ->
                                (maxOf(src, it.first) to minOf(src + range, it.last))
                                    .let { (start, end) ->
                                        if (start <= end) {
                                            (dest - src).let { (start + it)..(end + it) }
                                        } else {
                                            null
                                        }
                                    }
                            }
                        }
                    }
                }
                .minOf { it.first }
        }

    (part1() to 51752125L).verify()
    (part2() to 12634632L).verify()
}
