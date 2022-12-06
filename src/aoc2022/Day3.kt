package aoc2022

import utils.readInputForEachLine
import utils.verify

/**
 * --- Day 3: Rucksack Reorganization ---
 */
fun main() {
    fun part1(): Long {
        var totalPriorities = 0L
        "2022/Day3.txt".readInputForEachLine {
            val length = it.length
            val firstCompartment = it.subSequence(0, length / 2).toSet()
            val secondCompartment = it.subSequence(length / 2, length).toSet()

            val intersection = firstCompartment.intersect(secondCompartment).first()
            totalPriorities += if (intersection.code >= 'a'.code) {
                intersection.code - 'a'.code + 1
            } else {
                intersection.code - 'A'.code + 27
            }
        }
        return totalPriorities
    }

    fun part2(): Long {
        var totalPriorities = 0L
        var cnt = 0
        var intersection = ('a'..'z').toSet() + ('A'..'Z').toSet()
        "2022/Day3.txt".readInputForEachLine {
            val rucksack = it.toCharArray().toSet()
            intersection = intersection.intersect(rucksack)
            cnt++
            if (cnt == 3) {
                val badge = intersection.first()
                totalPriorities += if (badge.code >= 'a'.code) {
                    badge.code - 'a'.code + 1
                } else {
                    badge.code - 'A'.code + 27
                }
                intersection = ('a'..'z').toSet() + ('A'..'Z').toSet()
                cnt = 0
            }
        }
        return totalPriorities
    }

    (part1() to 7826L).verify()
    (part2() to 2577L).verify()
}
