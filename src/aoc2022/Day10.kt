package aoc2022

import utils.asInputStream
import utils.verify

/**
 * --- Day 10: Cathode-Ray Tube ---
 */
fun main() {
    val lines = "2022/Day10.txt".asInputStream().bufferedReader().readLines()

    open class CPU {
        private var cycles = 0
        private var registerX = 1
        private var spritePosition = registerX until registerX + 3
        var totalSignalStrength = 0
            protected set
        val crt = mutableListOf<String>()
        var tmpCrtLine = ""

        fun command(line: String) =
            when {
                line.startsWith("addx") -> {
                    val (_, value) = line.split(" ")
                    val valueV = value.toInt()
                    updateCpuCycles()
                    updateCpuCycles()
                    registerX += valueV
                    updateSpritePosition()
                }

                line.startsWith("noop") -> {
                    updateCpuCycles()
                }

                else -> error("NOT FOUND cmd: $line")
            }

        private fun updateCpuCycles() {
            cycles++

            tmpCrtLine +=
                if (cycles % 40 in spritePosition) {
                    "#"
                } else {
                    "."
                }

            if (tmpCrtLine.length == 40) {
                crt.add(tmpCrtLine)
                tmpCrtLine = ""
            }

            if (cycles % 40 == 20) {
                totalSignalStrength += cycles * registerX
            }
        }

        private fun updateSpritePosition() {
            spritePosition = registerX until registerX + 3
        }
    }

    fun part1(): Int {
        val cpu = CPU()
        lines.forEach { line -> cpu.command(line) }
        return cpu.totalSignalStrength
    }

    fun part2() {
        val cpu = CPU()
        lines.forEach { line -> cpu.command(line) }
        cpu.crt.forEach { println(it) }
    }

    (part1() to 13180).verify()
    part2() // EZFCHJAB
}
