package aoc2023

import utils.readInput
import utils.verify

/**
 * --- Day 8: Haunted Wasteland ---
 */
fun main() {
    val inputFile = "2023/Day08.txt"

    data class Node(val cur: String, val next: List<String>) {
        init {
            require(next.size == 2)
        }
    }

    fun String.toInstructions(): List<Int> =
        this
            .replace('L', '0')
            .replace('R', '1')
            .toCharArray().map { it.digitToInt() }

    fun part1(): Int {
        val lines = inputFile.readInput()
        val instructions = lines.first().toInstructions()

        val strToNode =
            lines
                .drop(2)
                .map { line ->
                    line
                        .replace('(', ' ')
                        .replace(')', ' ')
                        .split("=", ",")
                        .map { it.trim() }
                        .let { (cur, left, right) ->
                            Node(cur, listOf(left, right))
                        }
                }
                .associateBy { it.cur }

        var steps = 0
        var curPos = "AAA"
        val end = "ZZZ"
        while (curPos != end) {
            steps++
            for (instruction in instructions) {
                val curNode = strToNode[curPos] ?: error("Not found Node")
                curPos = curNode.next[instruction]
            }
        }

        return steps * instructions.size
    }

    fun part2(): Long {
        val lines = inputFile.readInput()
        val instructions = lines.first().toInstructions()

        fun gcm(
            a: Long,
            b: Long,
        ): Long {
            if (b == 0L) return a
            return gcm(b, a % b)
        }

        fun lcm(
            a: Long,
            b: Long,
        ): Long {
            return a * b / gcm(a, b)
        }

        val initialNodes = mutableListOf<String>()
        val strToNode =
            lines
                .drop(2)
                .map { line ->
                    line
                        .replace('(', ' ')
                        .replace(')', ' ')
                        .split("=", ",")
                        .map { it.trim() }
                        .let { (cur, left, right) ->
                            Node(cur, listOf(left, right)).also {
                                if (cur.endsWith("A")) {
                                    initialNodes.add(cur)
                                }
                            }
                        }
                }
                .associateBy { it.cur }

        return initialNodes.map {
            var curPos = it
            var steps = 0L
            while (curPos.endsWith("Z").not()) {
                steps++
                for (instruction in instructions) {
                    val curNode = strToNode[curPos] ?: error("Not found Node")
                    curPos = curNode.next[instruction]
                }
            }
            steps
        }.let {
            it.reduce { acc, bigInteger -> lcm(acc, bigInteger) }
        } * instructions.size
    }

    (part1() to 11309).verify()
    (part2() to 13740108158591L).verify()
}
