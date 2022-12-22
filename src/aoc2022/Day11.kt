package aoc2022

import utils.asInputStream
import utils.verify
import java.math.BigInteger

/**
 * --- Day 11: Monkey in the Middle ---
 */
fun main() {
    val lines = "2022/Day11.txt".asInputStream().bufferedReader().readLines()

    data class Item(
        var worryLevel: BigInteger,
        var nextMonkeyIdx: Int,
    )

    data class Monkey(
        val idx: Int,
        val operation: (BigInteger) -> BigInteger,
        val divisor: Long,
        val ifTrue: Int,
        val ifFalse: Int,
    )

    fun parseOperationFrom(str: String): (BigInteger) -> BigInteger {
        val operationParts = str.substringAfter("Operation:").trim().split(" ")
        return { old: BigInteger ->
            val last = if (operationParts.last() != "old") {
                BigInteger.valueOf(operationParts.last().toLong())
            } else {
                old
            }

            when (operationParts[3]) {
                "+" -> old.plus(last)
                "-" -> old.minus(last)
                "*" -> old.multiply(last)
                "/" -> old.divide(last)
                else -> error("NOT FOUND OPERATION: $str")
            }
        }
    }

    fun List<String>.toMonkeyAndItems(): Pair<Monkey, List<Item>> {
        val idx = this.first().substringAfter("Monkey ").dropLast(1).trim().toInt()
        val items = this[1].substringAfter("Starting items:").trim().split(", ").map {
            Item(BigInteger.valueOf(it.trim().toLong()), idx)
        }
        val operation = parseOperationFrom(this[2])
        val divisor = this[3].substringAfter("Test:").trim().split(" ").last().toLong()
        val ifTrue = this[4].substringAfter("If true:").trim().split(" ").last().toInt()
        val ifFalse = this[5].substringAfter("If false:").trim().split(" ").last().toInt()
        return Monkey(idx, operation, divisor, ifTrue, ifFalse) to items
    }

    fun inspectItems(
        items: List<Item>,
        monkeys: List<Monkey>,
        inspectTimes: LongArray,
        modulo: Long,
        round: Int = 20,
        dividedByThree: Boolean = true,
    ) {
        repeat(round) {
            monkeys.forEach { monkey ->
                items
                    .filter { item -> item.nextMonkeyIdx == monkey.idx }
                    .also { filteredItems -> inspectTimes[monkey.idx] += filteredItems.size.toLong() }
                    .forEach { item ->
                        val tmp = if (dividedByThree) {
                            monkey.operation(item.worryLevel) / 3.toBigInteger()
                        } else {
                            monkey.operation(item.worryLevel).mod(modulo.toBigInteger())
                        }
                        item.worryLevel = tmp
                        item.nextMonkeyIdx = if (tmp.mod(monkey.divisor.toBigInteger()) == BigInteger.ZERO) {
                            monkey.ifTrue
                        } else {
                            monkey.ifFalse
                        }
                    }
            }
        }
    }

    fun part1(): Long {
        val monkeys = mutableListOf<Monkey>()
        val items = mutableListOf<Item>()
        lines.chunked(7).forEach {
            val (monkey, inputItems) = it.toMonkeyAndItems()
            monkeys.add(monkey)
            items.addAll(inputItems)
        }
        val modulo = monkeys
            .map { it.divisor }
            .reduce { acc, i -> acc * i }
        val inspectTimes = LongArray(monkeys.size)
        inspectItems(items, monkeys, inspectTimes, modulo)
        return inspectTimes.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    fun part2(): Long {
        val monkeys = mutableListOf<Monkey>()
        val items = mutableListOf<Item>()
        lines.chunked(7).forEach {
            val (monkey, inputItems) = it.toMonkeyAndItems()
            monkeys.add(monkey)
            items.addAll(inputItems)
        }
        val modulo = monkeys
            .map { it.divisor }
            .reduce { acc, i -> acc * i }
        val inspectTimes = LongArray(monkeys.size)
        inspectItems(items, monkeys, inspectTimes, modulo, 10000, false)
        return inspectTimes.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    (part1() to 112221L).verify()
    (part2() to 25272176808L).verify()
}
