package aoc2025

import utils.Problem
import utils.runProblem
import utils.solve

object Day06 : Problem<Long> {
    private enum class Op {
        PLUS,
        MUL,
        ;

        fun apply(
            a: Long,
            b: Long,
        ): Long =
            when (this) {
                PLUS -> a + b
                MUL -> a * b
            }
    }

    private fun Char.toOp(): Op =
        when (this) {
            '+' -> Op.PLUS
            '*' -> Op.MUL
            else -> error("Invalid operator: $this")
        }

    private data class Acc(
        var value: Long = 0,
        var has: Boolean = false,
    ) {
        fun pushDigit(ch: Char) {
            value = value * 10 + (ch - '0')
            has = true
        }

        fun clear() {
            value = 0
            has = false
        }
    }

    private fun Array<Acc>.allReady(): Boolean = all { it.has }

    private fun readDigitsAtColumn(
        i: Int,
        numbers: List<String>,
        accs: Array<Acc>,
    ) {
        for (j in numbers.indices) {
            val ch = numbers[j].getOrNull(i) ?: ' '
            if (ch != ' ') accs[j].pushDigit(ch)
        }
    }

    private fun flushGroup(
        accs: Array<Acc>,
        op: Op,
    ): Long {
        require(accs.isNotEmpty())
        require(accs.allReady()) { "Tried to flush but not all accumulators are ready." }

        var reduced = accs[0].value
        for (k in 1 until accs.size) {
            reduced = op.apply(reduced, accs[k].value)
        }
        accs.forEach { it.clear() }
        return reduced
    }

    private fun readVerticalNumberAtColumn(
        i: Int,
        numbers: List<String>,
    ): Long? {
        var value = 0L
        var hasDigit = false

        for (j in numbers.indices) {
            val ch = numbers[j].getOrNull(i) ?: ' '
            if (ch != ' ') {
                value = value * 10 + (ch - '0')
                hasDigit = true
            }
        }
        return if (hasDigit) value else null
    }

    private fun ArrayDeque<Long>.consumeReduce(op: Op): Long {
        require(isNotEmpty())
        var acc = removeFirst()
        while (isNotEmpty()) {
            acc = op.apply(acc, removeFirst())
        }
        return acc
    }

    override fun part1(): Long =
        solve(2025) { allLines ->
            val operations = allLines.last()
            val numbers = allLines.dropLast(1)

            require(operations.isNotEmpty() && operations.first() != ' ') {
                "Expected operations.first() to be an operator (non-space), but was: '${operations.firstOrNull()}'"
            }

            val accs = Array(numbers.size) { Acc() }

            var result = 0L
            var prevOp = operations.first().toOp()

            for (i in operations.indices) {
                val opCh = operations[i]

                if (opCh != ' ' && accs.allReady()) {
                    result += flushGroup(accs, prevOp)
                    prevOp = opCh.toOp()
                }

                readDigitsAtColumn(i, numbers, accs)
            }

            result + flushGroup(accs, prevOp)
        }

    override fun part2(): Long =
        solve(2025) { allLines ->
            val operations = allLines.last()
            val numbers = allLines.dropLast(1)

            var result = 0L
            val candidates = ArrayDeque<Long>()

            for (i in operations.indices.reversed()) {
                val number = readVerticalNumberAtColumn(i, numbers) ?: continue
                candidates.addLast(number)

                val opCh = operations[i]
                if (opCh != ' ') {
                    result += candidates.consumeReduce(opCh.toOp())
                }
            }

            if (candidates.isNotEmpty()) {
                val fallbackOp =
                    operations.firstOrNull { it != ' ' }?.toOp()
                        ?: error("No operator found to reduce the remaining candidates.")

                result += candidates.consumeReduce(fallbackOp)
            }

            result
        }
}

fun main() {
    Day06.runProblem(7098065460541L, 13807151830618L)
}
