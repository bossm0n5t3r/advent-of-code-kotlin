package aoc2023

import utils.readInput
import utils.verify

/**
 * --- Day 3: Gear Ratios ---
 */
fun main() {
    val inputFile = "2023/Day03.txt"

    val dr = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
    val dc = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)

    fun part1(): Int {
        val engineSchematic =
            inputFile
                .readInput()
                .map { it.toCharArray() }
                .toTypedArray()
        val r = engineSchematic.size
        val c = engineSchematic.first().size
        val symbols = mutableListOf<Pair<Int, Int>>()
        for (nr in 0 until r) {
            for (nc in 0 until c) {
                val char = engineSchematic[nr][nc]
                if (char.isDigit().not() && char != '.') {
                    symbols.add(nr to nc)
                }
            }
        }
        val visited = Array(r) { BooleanArray(c) { false } }
        val partNumberList = mutableListOf<Int>()
        for (symbol in symbols) {
            val (currentR, currentC) = symbol
            visited[currentR][currentC] = true
            for (i in 0 until 8) {
                val nr = currentR + dr[i]
                val nc = currentC + dc[i]
                if (visited[nr][nc].not() && engineSchematic[nr][nc].isDigit()) {
                    visited[nr][nc] = true
                    var tmp = engineSchematic[nr][nc].toString()
                    var tmpCLeft = nc - 1
                    while (tmpCLeft >= 0 && visited[nr][tmpCLeft].not() && engineSchematic[nr][tmpCLeft].isDigit()) {
                        tmp = "${engineSchematic[nr][tmpCLeft]}$tmp"
                        visited[nr][tmpCLeft] = true
                        tmpCLeft--
                    }
                    var tmpCRight = nc + 1
                    while (tmpCRight < c && visited[nr][tmpCRight].not() && engineSchematic[nr][tmpCRight].isDigit()) {
                        tmp = "$tmp${engineSchematic[nr][tmpCRight]}"
                        visited[nr][tmpCRight] = true
                        tmpCRight++
                    }
                    partNumberList.add(tmp.toInt())
                }
            }
        }
        return partNumberList.sum()
    }

    fun part2(): Int {
        val engineSchematic =
            inputFile
                .readInput()
                .map { it.toCharArray() }
                .toTypedArray()
        val r = engineSchematic.size
        val c = engineSchematic.first().size
        val gears = mutableListOf<Pair<Int, Int>>()
        for (nr in 0 until r) {
            for (nc in 0 until c) {
                if (engineSchematic[nr][nc] == '*') {
                    gears.add(nr to nc)
                }
            }
        }

        val visited = Array(r) { BooleanArray(c) { false } }
        val gearRatiosList = mutableListOf<Int>()
        for (symbol in gears) {
            val (currentR, currentC) = symbol
            visited[currentR][currentC] = true
            val partNumbers = mutableListOf<Int>()
            for (i in 0 until 8) {
                val nr = currentR + dr[i]
                val nc = currentC + dc[i]
                if (visited[nr][nc].not() && engineSchematic[nr][nc].isDigit()) {
                    visited[nr][nc] = true
                    var tmp = engineSchematic[nr][nc].toString()
                    var tmpCLeft = nc - 1
                    while (tmpCLeft >= 0 && visited[nr][tmpCLeft].not() && engineSchematic[nr][tmpCLeft].isDigit()) {
                        tmp = "${engineSchematic[nr][tmpCLeft]}$tmp"
                        visited[nr][tmpCLeft] = true
                        tmpCLeft--
                    }
                    var tmpCRight = nc + 1
                    while (tmpCRight < c && visited[nr][tmpCRight].not() && engineSchematic[nr][tmpCRight].isDigit()) {
                        tmp = "$tmp${engineSchematic[nr][tmpCRight]}"
                        visited[nr][tmpCRight] = true
                        tmpCRight++
                    }
                    partNumbers.add(tmp.toInt())
                }
            }
            if (partNumbers.size == 2) {
                gearRatiosList.add(partNumbers.first() * partNumbers.last())
            }
        }
        return gearRatiosList.sum()
    }

    (part1() to 539590).verify()
    (part2() to 80703636).verify()
}
