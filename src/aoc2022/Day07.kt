package aoc2022

import utils.readInputForEachLine
import utils.verify

/**
 * --- Day 7: No Space Left On Device ---
 */
fun main() {
    data class File(
        val name: String,
        val size: Long,
    )

    fun getDirsWithFiles(): Map<String, Set<File>> {
        val dirs = mutableMapOf<String, MutableSet<File>>()
        var curDir = "/"
        dirs[curDir] = mutableSetOf()
        "2022/Day07.txt".readInputForEachLine { line ->
            val splitLine = line.split(" ")
            if (splitLine.first() == "$") {
                val cmd = splitLine[1]
                if (cmd == "cd") {
                    val targetDir = splitLine[2]
                    if (targetDir == "..") {
                        curDir = curDir.split("/").dropLast(1).joinToString("/")
                    } else if (targetDir != "/") {
                        curDir =
                            if (curDir != "/") {
                                "$curDir/$targetDir"
                            } else {
                                "$curDir$targetDir"
                            }
                        dirs[curDir] = mutableSetOf()
                    }
                }
            } else if (splitLine.first() == "dir") {
                val targetDir = splitLine[1]
                val newDir =
                    if (curDir != "/") {
                        "$curDir/$targetDir"
                    } else {
                        "$curDir$targetDir"
                    }
                dirs[newDir] = mutableSetOf()
            } else {
                val newFile = File(splitLine.last(), splitLine[0].toLong())
                var tmpDir = curDir
                while (tmpDir.isNotBlank()) {
                    dirs[tmpDir]?.add(newFile)
                    tmpDir = tmpDir.split("/").dropLast(1).joinToString("/")
                    if (tmpDir.isBlank()) {
                        dirs["/"]?.add(newFile)
                    }
                }
            }
        }
        return dirs
    }

    fun part1(): Long {
        val dirs = getDirsWithFiles()
        return dirs.values.sumOf {
            val totalFileSize = it.sumOf { file -> file.size }
            if (totalFileSize > 100000) {
                0
            } else {
                totalFileSize
            }
        }
    }

    fun part2(): Long {
        val dirs = getDirsWithFiles()
        val dirToTotalSize =
            dirs.mapValues { (_, value) ->
                value.sumOf { it.size }
            }
        val totalAmountOfUsedSpace = dirToTotalSize["/"] ?: error("NOT FOUND /")
        val unusedSpace = 70000000L - totalAmountOfUsedSpace
        val needMoreSpace = 30000000L - unusedSpace
        return dirToTotalSize.values.mapNotNull {
            if (it > needMoreSpace) {
                it
            } else {
                null
            }
        }.minOrNull() ?: error("NOT FOUND ANSWER")
    }

    (part1() to 1555642L).verify()
    (part2() to 5974547L).verify()
}
