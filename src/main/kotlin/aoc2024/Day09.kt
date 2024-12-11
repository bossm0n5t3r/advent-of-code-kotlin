package aoc2024

import utils.solve

/**
 * --- Day 9: Disk Fragmenter ---
 */
object Day09 {
    private fun String.toDisk(): Pair<MutableList<MutableList<Int>>, MutableList<Int>> {
        val result = MutableList(this.sumOf { it - '0' }) { -1 }
        val emptyIndexList = mutableListOf<MutableList<Int>>()
        var index = 0
        var idNumber = 0
        var isFreeSpace = false
        for (c in this) {
            val count = c - '0'
            if (isFreeSpace) {
                emptyIndexList.add(MutableList(count) { index++ })
                isFreeSpace = false
                continue
            }
            repeat(count) {
                result[index++] = idNumber
            }
            idNumber++
            isFreeSpace = true
        }
        return emptyIndexList to result
    }

    private fun List<Int>.checkSum(): Long =
        this.foldIndexed(0L) { index, acc, c ->
            if (c == -1) {
                acc
            } else {
                acc + c * index
            }
        }

    fun part1() =
        solve(2024) { lines ->
            val (_, numbers) = lines.joinToString("").toDisk()
            var start = 0
            var end = numbers.lastIndex
            while (start < end) {
                while (start < end && numbers[start] != -1) {
                    start++
                }
                while (start < end && numbers[end] == -1) {
                    end--
                }
                if (start < end) {
                    numbers[start] = numbers[end]
                    numbers[end] = -1
                }
                start++
                end--
            }
            numbers.checkSum()
        }

    fun part2() =
        solve(2024) { lines ->
            val (emptyIndexList, numbers) = lines.joinToString("").toDisk()
            var lastIdNumber = numbers.last()
            var end = numbers.lastIndex
            while (lastIdNumber > -1) {
                while (end > 0 && numbers[end] != lastIdNumber) {
                    end--
                }
                var endCount = 1
                while (end - endCount >= 0 && numbers[end - endCount] == lastIdNumber) {
                    endCount++
                }
                val targetEmptyIndexListIndex = emptyIndexList.indexOfFirst { it.size >= endCount && it.first() <= end }
                if (targetEmptyIndexListIndex == -1) {
                    lastIdNumber--
                    end -= endCount
                    continue
                }
                val targetEmptyIndexList = emptyIndexList[targetEmptyIndexListIndex]
                repeat(endCount) {
                    numbers[end - it] = -1
                    numbers[targetEmptyIndexList.removeFirst()] = lastIdNumber
                }
                emptyIndexList[targetEmptyIndexListIndex] = targetEmptyIndexList
                end -= endCount
                lastIdNumber--
            }
            numbers.checkSum()
        }
}
