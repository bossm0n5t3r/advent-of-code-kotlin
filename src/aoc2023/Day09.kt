package aoc2023

import utils.solve
import utils.verify
import java.util.Stack

/**
 * --- Day 9: Mirage Maintenance ---
 */
fun main() {
    fun part1(): Int =
        solve { lines ->
            lines.sumOf { line ->
                val stack = Stack<List<Int>>()
                var nums = line.split(" ").map { it.toInt() }
                while (nums.all { it == 0 }.not()) {
                    stack.push(nums)
                    nums = nums.windowed(2) { it.last() - it.first() }
                }
                val cur = stack.pop()
                var last = cur.last()
                while (stack.isNotEmpty()) {
                    val next = stack.pop()
                    last += next.last()
                }
                last
            }
        }

    fun part2(): Int =
        solve { lines ->
            lines.sumOf { line ->
                val stack = Stack<List<Int>>()
                var nums = line.split(" ").map { it.toInt() }
                while (nums.all { it == 0 }.not()) {
                    stack.push(nums)
                    nums = nums.windowed(2) { it.last() - it.first() }
                }
                val cur = stack.pop()
                var last = cur.first()
                while (stack.isNotEmpty()) {
                    val next = stack.pop()
                    last = next.first() - last
                }
                last
            }
        }

    (part1() to 1877825184).verify()
    (part2() to 1108).verify()
}
