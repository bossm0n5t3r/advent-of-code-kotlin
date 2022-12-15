package aoc2022

import utils.readInputForEachLine
import utils.verify

/**
 * --- Day 9: Rope Bridge ---
 */
fun main() {
    data class Rope(private val size: Int = 2) {
        private val knots = Array(size) { 0 to 0 }
        val lastKnotVisited = mutableSetOf(knots.last())

        fun move(dir: Char, dist: Int) {
            repeat(dist) {
                val (curR, curC) = knots.first()
                knots[0] = when (dir) {
                    'U' -> {
                        curR - 1 to curC
                    }

                    'D' -> {
                        curR + 1 to curC
                    }

                    'L' -> {
                        curR to curC - 1
                    }

                    'R' -> {
                        curR to curC + 1
                    }

                    else -> error("NOT FOUND dir: $dir")
                }

                (0 until knots.lastIndex).forEach { i ->
                    followHeadKnot(headIdx = i, tailIdx = i + 1)
                }
            }
        }

        private fun followHeadKnot(
            headIdx: Int,
            tailIdx: Int,
        ) {
            if (!touchingHeadKnot(headIdx, tailIdx)) {
                knots[tailIdx] = moveTailKnot(headIdx, tailIdx)
            }

            if (tailIdx == knots.lastIndex) {
                lastKnotVisited.add(knots.last())
            }
        }

        private fun touchingHeadKnot(
            headIdx: Int,
            tailIdx: Int,
        ): Boolean {
            val (head, tail) = knots[headIdx] to knots[tailIdx]
            val (hr, hc) = head
            val (tr, tc) = tail
            return tr in (hr - 1..hr + 1) && tc in (hc - 1..hc + 1)
        }

        private fun moveTailKnot(
            headIdx: Int,
            tailIdx: Int,
        ): Pair<Int, Int> {
            val (hr, hc) = knots[headIdx]
            val (tr, tc) = knots[tailIdx]

            return when {
                /**
                 * If the head is ever two steps directly up, down, left, or right from the tail,
                 * the tail must also move one step in that direction so it remains close enough:
                 */
                hr == tr -> {
                    if (hc > tc) {
                        tr to tc + 1
                    } else {
                        tr to tc - 1
                    }
                }

                hc == tc -> {
                    if (hr > tr) {
                        tr + 1 to tc
                    } else {
                        tr - 1 to tc
                    }
                }

                /**
                 * Otherwise, if the head and tail aren't touching and aren't in the same row or column,
                 * the tail always moves one step diagonally to keep up:
                 */
                else -> {
                    if (hr > tr && hc > tc) {
                        tr + 1 to tc + 1
                    } else if (hr > tr) {
                        tr + 1 to tc - 1
                    } else if (hc > tc) {
                        tr - 1 to tc + 1
                    } else {
                        tr - 1 to tc - 1
                    }
                }
            }
        }
    }

    fun part1(): Int {
        val rope = Rope()
        "2022/Day09.txt".readInputForEachLine {
            val (dir, dist) = it.split(" ")
            rope.move(dir = dir.first(), dist = dist.toInt())
        }
        return rope.lastKnotVisited.size
    }

    fun part2(): Int {
        val rope = Rope(size = 10)
        "2022/Day09.txt".readInputForEachLine {
            val (dir, dist) = it.split(" ")
            rope.move(dir = dir.first(), dist = dist.toInt())
        }
        return rope.lastKnotVisited.size
    }

    (part1() to 6209).verify()
    (part2() to 2460).verify()
}
