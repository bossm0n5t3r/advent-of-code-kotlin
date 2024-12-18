package aoc2022

import utils.solve

/**
 * --- Day 3: Rucksack Reorganization ---
 */
object Day03 {
    fun part1(): Long =
        solve(2022) { lines ->
            var totalPriorities = 0L
            lines.forEach {
                val length = it.length
                val firstCompartment = it.subSequence(0, length / 2).toSet()
                val secondCompartment = it.subSequence(length / 2, length).toSet()

                val intersection = firstCompartment.intersect(secondCompartment).first()
                totalPriorities +=
                    if (intersection.code >= 'a'.code) {
                        intersection.code - 'a'.code + 1
                    } else {
                        intersection.code - 'A'.code + 27
                    }
            }
            totalPriorities
        }

    fun part2(): Long =
        solve(2022) { lines ->
            var totalPriorities = 0L
            var cnt = 0
            var intersection = ('a'..'z').toSet() + ('A'..'Z').toSet()
            lines.forEach {
                val rucksack = it.toCharArray().toSet()
                intersection = intersection.intersect(rucksack)
                cnt++
                if (cnt == 3) {
                    val badge = intersection.first()
                    totalPriorities +=
                        if (badge.code >= 'a'.code) {
                            badge.code - 'a'.code + 1
                        } else {
                            badge.code - 'A'.code + 27
                        }
                    intersection = ('a'..'z').toSet() + ('A'..'Z').toSet()
                    cnt = 0
                }
            }
            totalPriorities
        }
}
