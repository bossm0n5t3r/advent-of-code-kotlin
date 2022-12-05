package aoc2022

import utils.PuzzleInput
import utils.readInputForEachLine
import java.util.Stack

/**
 * --- Day 5: Supply Stacks ---
 */
fun main() {
    fun parseCraftsFromString(str: String, stacks: MutableMap<Int, Stack<Char>>) = run {
        str.chunked(4)
            .map { str -> str.trim() }
            .forEachIndexed { index, str ->
                if (str.isNotBlank() && str.length > 1) {
                    val stackIdx = index + 1
                    val crate = str[1]
                    if (!stacks.containsKey(stackIdx)) {
                        stacks[stackIdx] = Stack()
                    }
                    stacks[stackIdx]?.add(0, crate)
                }
            }
    }

    fun part1(puzzleInput: PuzzleInput): String {
        val stacks = mutableMapOf(0 to Stack<Char>())

        var parseStack = true
        puzzleInput.readInputForEachLine {
            if (parseStack) {
                if (it.isNotBlank()) {
                    parseCraftsFromString(it, stacks)
                } else {
                    parseStack = false
                }
            } else {
                val (craftsToMove, from, to) = it.split("move", "from", "to")
                    .drop(1)
                    .map { s -> s.trim().toInt() }

                repeat(craftsToMove) {
                    stacks[from]?.pop()?.run {
                        stacks[to]?.add(this)
                    }
                }
            }
        }

        return stacks.keys.sorted().drop(1).mapNotNull { stacks[it]?.peek() }.joinToString("")
    }

    fun part2(puzzleInput: PuzzleInput): String {
        val stacks = mutableMapOf(0 to Stack<Char>())

        var parseStack = true
        puzzleInput.readInputForEachLine {
            if (parseStack) {
                if (it.isNotBlank()) {
                    parseCraftsFromString(it, stacks)
                } else {
                    parseStack = false
                }
            } else {
                val (craftsToMove, from, to) = it.split("move", "from", "to")
                    .drop(1)
                    .map { s -> s.trim().toInt() }

                val tmpStack = Stack<Char>()

                repeat(craftsToMove) {
                    stacks[from]?.pop()?.run {
                        tmpStack.add(this)
                    }
                }

                repeat(craftsToMove) {
                    tmpStack.pop().run {
                        stacks[to]?.add(this)
                    }
                }
            }
        }

        return stacks.keys.sorted().drop(1).mapNotNull { stacks[it]?.peek() }.joinToString("")
    }

    val puzzleInput = PuzzleInput(2022, "Day5.txt")
    part1(puzzleInput).run {
        println(this)
        require(this == "VWLCWGSDQ")
    }
    part2(puzzleInput).run {
        println(this)
        require(this == "TCGLQSLPW")
    }
}
