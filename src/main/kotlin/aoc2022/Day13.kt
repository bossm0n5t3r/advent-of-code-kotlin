package aoc2022

import utils.asInputStream
import utils.verify

/**
 * --- Day 13: Distress Signal ---
 */
fun main() {
    val lines = "2022/Day13.txt".asInputStream().bufferedReader().readLines()

    fun parseList(
        packet: String,
        nextIdxStart: Int = 1,
    ): Pair<List<Any>, Int> {
        val result = mutableListOf<Any>()
        var curr = ""
        var nextIdx = nextIdxStart
        var c: Char
        while (true) {
            c = packet[nextIdx++]
            when (c) {
                ']' -> {
                    if (curr != "") {
                        result.add(curr.toInt())
                    }
                    return Pair(result, nextIdx)
                }

                ',' -> {
                    if (curr != "") {
                        result.add(curr.toInt())
                    }
                    curr = ""
                }

                '[' -> {
                    val (ls, idz) = parseList(packet, nextIdx)
                    nextIdx = idz
                    result.add(ls)
                }

                else -> curr += c
            }
        }
    }

    fun parsePacket(packet: String) = parseList(packet).first

    fun compare(
        left: Any,
        right: Any,
    ): Int {
        return when {
            left is Int && right is Int -> {
                when {
                    left < right -> 1
                    left > right -> -1
                    else -> 0
                }
            }

            left is List<*> && right is List<*> -> {
                var i = 0
                while (i < left.size && i < right.size) {
                    val cmp = compare(left[i]!!, right[i]!!)
                    if (cmp != 0) {
                        return cmp
                    }
                    ++i
                }
                when {
                    left.size < right.size -> 1
                    left.size > right.size -> -1
                    else -> 0
                }
            }

            else -> {
                val newLeft: List<Any>
                var newRight: List<Any>
                if (left is Int) {
                    newLeft = listOf(left)
                    newRight = right as List<Any>
                } else {
                    newLeft = left as List<Any>
                    newRight = listOf(right)
                }
                compare(newLeft, newRight)
            }
        }
    }

    fun part1(): Int =
        lines
            .chunked(3)
            .mapIndexed { index, strings ->
                val packetFirst = parsePacket(strings[0])
                val packetSecond = parsePacket(strings[1])

                if (compare(packetFirst, packetSecond) == 1) {
                    index + 1
                } else {
                    0
                }
            }.sum()

    fun part2(): Int {
        val packets = mutableListOf<List<Any>>()

        lines.chunked(3).forEach {
            packets.addAll(it.dropLast(1).map { s: String -> parsePacket(s) })
        }

        val div1 = parsePacket("[[2]]")
        val div2 = parsePacket("[[6]]")
        packets.add(div1)
        packets.add(div2)

        val sortedPackets =
            packets
                .sortedWith { ls1: List<Any>, ls2: List<Any> ->
                    if (ls1 == ls2) 0 else compare(ls2, ls1)
                }.toMutableList()

        return (sortedPackets.indexOf(div1) + 2) * (sortedPackets.indexOf(div2) + 2)
    }

    (part1() to 5808).verify()
    (part2() to 22713).verify()
}
