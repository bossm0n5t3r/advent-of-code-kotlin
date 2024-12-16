package aoc2024

import utils.solve
import kotlin.math.abs
import kotlin.math.roundToLong

/**
 * --- Day 13: Claw Contraption ---
 */
object Day13 {
    private val NUMBER_REGEX = Regex("\\d+")

    private fun List<String>.toMachines(): List<List<String>> {
        val machines = mutableListOf<List<String>>()
        var machine = mutableListOf<String>()
        for (line in this) {
            if (line.isBlank()) {
                machines.add(machine)
                machine = mutableListOf()
            } else {
                machine.add(line)
            }
        }
        machines.add(machine)
        return machines
    }

    private fun List<String>.getFewestToken(plus: Long = 0L): Long {
        val (buttonAX, buttonAY) = this[0].parseNumbers()
        val (buttonBX, buttonBY) = this[1].parseNumbers()
        val buttonMatrix =
            arrayOf(
                intArrayOf(buttonAX, buttonBX),
                intArrayOf(buttonAY, buttonBY),
            )
        val resultMatrix = buttonMatrix.inverse().product(this[2].parseNumbers().map { it.toDouble() + plus })
        if (resultMatrix.any { it < 0 }) return 0L
        if (resultMatrix.any { abs(it - it.roundToLong()) > 10e-3 }) return 0L
        return resultMatrix.first().roundToLong() * 3 + resultMatrix.last().roundToLong()
    }

    private fun Array<IntArray>.inverse(): Array<DoubleArray> {
        val det = this[0][0] * this[1][1] - this[0][1] * this[1][0]
        return arrayOf(
            doubleArrayOf(this[1][1].toDouble() / det, -this[0][1].toDouble() / det),
            doubleArrayOf(-this[1][0].toDouble() / det, this[0][0].toDouble() / det),
        )
    }

    private fun Array<DoubleArray>.product(vector: List<Double>): DoubleArray =
        doubleArrayOf(
            this[0][0] * vector[0] + this[0][1] * vector[1],
            this[1][0] * vector[0] + this[1][1] * vector[1],
        )

    private fun String.parseNumbers(): List<Int> = NUMBER_REGEX.findAll(this).map { it.value.toInt() }.toList()

    fun part1() =
        solve(2024) { lines ->
            lines.toMachines().sumOf { it.getFewestToken() }
        }

    fun part2() =
        solve(2024) { lines ->
            lines.toMachines().sumOf { it.getFewestToken(10_000_000_000_000) }
        }
}
