package aoc2022

import utils.asInputStream
import utils.verify
import java.math.BigInteger
import java.util.LinkedList

/**
 * --- Day 11: Monkey in the Middle ---
 */
fun main() {
    val lines = "2022/Day11.txt".asInputStream().bufferedReader().readLines()

    data class Monkey(
        val idx: Int,
        val items: LinkedList<BigInteger>,
        val operation: (BigInteger) -> BigInteger,
        val divisor: Long,
        val ifTrue: Int,
        val ifFalse: Int,
    ) {
        var inspectTimes = 0L

        private fun catch(worryLevel: BigInteger) = items.add(worryLevel)

        fun inspectItems(
            monkeys: List<Monkey>,
            modulo: Long,
            dividedByThree: Boolean = true,
        ) {
            val size = items.size
            inspectTimes += size
            repeat(size) {
                val worryLevel = items.pop()
                val newWorryLevel =
                    if (dividedByThree) {
                        operation(worryLevel) / 3.toBigInteger()
                    } else {
                        operation(worryLevel).mod(modulo.toBigInteger())
                    }

                if (newWorryLevel.mod(divisor.toBigInteger()) == BigInteger.ZERO) {
                    monkeys[ifTrue].catch(newWorryLevel)
                } else {
                    monkeys[ifFalse].catch(newWorryLevel)
                }
            }
        }
    }

    fun parseOperationFrom(str: String): (BigInteger) -> BigInteger {
        val operationParts = str.substringAfter("Operation:").trim().split(" ")
        return { old: BigInteger ->
            val last =
                if (operationParts.last() != "old") {
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

    fun List<String>.toMonkey(): Monkey {
        val idx = this.first().substringAfter("Monkey ").dropLast(1).trim().toInt()
        val items =
            this[1]
                .substringAfter("Starting items:")
                .trim()
                .split(", ")
                .map { it.trim().toLong().toBigInteger() }
        val operation = parseOperationFrom(this[2])
        val divisor = this[3].substringAfter("Test:").trim().split(" ").last().toLong()
        val ifTrue = this[4].substringAfter("If true:").trim().split(" ").last().toInt()
        val ifFalse = this[5].substringAfter("If false:").trim().split(" ").last().toInt()
        return Monkey(idx, LinkedList(items), operation, divisor, ifTrue, ifFalse)
    }

    fun inspectItems(
        monkeys: List<Monkey>,
        modulo: Long,
        round: Int = 20,
        dividedByThree: Boolean = true,
    ) {
        repeat(round) {
            monkeys.forEach { monkey -> monkey.inspectItems(monkeys, modulo, dividedByThree) }
        }
    }

    fun part1(): Long {
        val monkeys = lines.chunked(7).map { it.toMonkey() }
        val modulo =
            monkeys
                .map { it.divisor }
                .reduce { acc, i -> acc * i }
        inspectItems(monkeys, modulo)
        return monkeys.map { it.inspectTimes }.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    fun part2(): Long {
        val monkeys = lines.chunked(7).map { it.toMonkey() }
        val modulo =
            monkeys
                .map { it.divisor }
                .reduce { acc, i -> acc * i }
        inspectItems(monkeys, modulo, 10000, false)
        return monkeys.map { it.inspectTimes }.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    (part1() to 112221L).verify()
    (part2() to 25272176808L).verify()
}
