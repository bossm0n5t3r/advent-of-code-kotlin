package aoc2022

import utils.readInputForEachLine
import utils.verify

/**
 * --- Day 1: Calorie Counting ---
 */
fun main() {
    fun part1(): Long {
        var maxTotalCalories = 0L
        var totalCalories = 0L
        "2022/Day01.txt".readInputForEachLine {
            if (it.isBlank()) {
                if (totalCalories > maxTotalCalories) {
                    maxTotalCalories = totalCalories
                }
                totalCalories = 0L
            } else {
                totalCalories += it.toLong()
            }
        }
        return maxTotalCalories
    }

    fun part2(): Long {
        data class Top3Elves(
            var top1: Long = 0L,
            var top2: Long = 0L,
            var top3: Long = 0L,
        ) {
            fun update(value: Long) =
                when {
                    value <= top3 -> {}
                    value > top1 -> {
                        top3 = top2
                        top2 = top1
                        top1 = value
                    }

                    value in (top2 + 1) until top1 -> {
                        top3 = top2
                        top2 = value
                    }

                    value in (top3 + 1) until top2 -> {
                        top3 = value
                    }

                    else -> {}
                }

            fun totalCalories() = top1 + top2 + top3
        }

        val top3Elves = Top3Elves()
        var totalCalories = 0L
        "2022/Day01.txt".readInputForEachLine {
            if (it.isBlank()) {
                top3Elves.update(totalCalories)
                totalCalories = 0L
            } else {
                totalCalories += it.toLong()
            }
        }

        return top3Elves.totalCalories()
    }

    (part1() to 72017L).verify()
    (part2() to 212520L).verify()
}
