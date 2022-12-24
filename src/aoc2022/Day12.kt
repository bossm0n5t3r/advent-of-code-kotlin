package aoc2022

import utils.asInputStream
import utils.verify
import java.util.LinkedList
import java.util.Queue

/**
 * --- Day 12: Hill Climbing Algorithm ---
 */
fun main() {
    val lines = "2022/Day12.txt".asInputStream().bufferedReader().readLines()

    data class Location(val r: Int, val c: Int) {
        constructor(location: Pair<Int, Int>) : this(location.first, location.second)
    }

    fun getHeightMapAndSAndE(
        lines: List<String>,
        startPositions: List<Char>,
    ): Triple<Array<CharArray>, List<Location>, List<Location>> {
        val startLocations = mutableListOf<Location>()
        val endLocations = mutableListOf<Location>()
        val heightmap = lines.mapIndexed { r, s ->
            s
                .toCharArray()
                .also {
                    it.forEachIndexed { c, element ->
                        when (element) {
                            'E' -> endLocations.add(Location(r to c))
                            else -> {
                                if (startPositions.contains(element)) {
                                    startLocations.add(Location(r to c))
                                }
                            }
                        }
                    }
                }
        }.toTypedArray()
        return Triple(heightmap, startLocations, endLocations)
    }

    val dr = listOf(0, 0, 1, -1)
    val dc = listOf(1, -1, 0, 0)

    fun part1(): Int {
        val (
            heightmap,
            startLocations,
            endLocations,
        ) = getHeightMapAndSAndE(lines, listOf('S'))
        val (m, n) = heightmap.size to heightmap.first().size
        val visited = Array(m) { IntArray(n) { 0 } }

        val queue: Queue<Location> = LinkedList()
        queue.addAll(startLocations)
        visited[startLocations.first().r][startLocations.first().c] = 1

        while (queue.isNotEmpty()) {
            val (r, c) = queue.poll()
            if (Location(r, c) == endLocations.first()) return visited[r][c] - 1

            val curVisit = visited[r][c]
            val curHeight = heightmap[r][c]

            (0 until 4).forEach { i ->
                val nr = r + dr[i]
                val nc = c + dc[i]
                val nextLocation = Location(nr, nc)

                if (nr in 0 until m && nc in 0 until n && visited[nr][nc] == 0) {
                    val nextHeight = heightmap[nextLocation.r][nextLocation.c]
                    if (curHeight == 'S') {
                        if (nextHeight == 'a') {
                            visited[nr][nc] = curVisit + 1
                            queue.add(nextLocation)
                        }
                    } else if (nextHeight == 'E') {
                        if (curHeight == 'z') {
                            visited[nr][nc] = curVisit + 1
                            queue.add(nextLocation)
                        }
                    } else if (heightmap[nr][nc].code <= heightmap[r][c].code + 1) {
                        visited[nr][nc] = curVisit + 1
                        queue.add(nextLocation)
                    }
                }
            }
        }
        return -1
    }

    fun part2(): Int {
        val (
            heightmap,
            startLocations,
            endLocations,
        ) = getHeightMapAndSAndE(lines, listOf('S', 'a'))
        val (m, n) = heightmap.size to heightmap.first().size
        val visited = Array(m) { IntArray(n) { 0 } }

        val queue: Queue<Location> = LinkedList()

        startLocations.forEach {
            queue.add(it)
            visited[it.r][it.c] = 1
        }

        var fewestSteps = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val (r, c) = queue.poll()
            if (Location(r, c) == endLocations.first()) {
                if (fewestSteps > visited[r][c] - 1) {
                    fewestSteps = visited[r][c] - 1
                }
                continue
            }

            val curVisit = visited[r][c]
            val curHeight = heightmap[r][c]

            (0 until 4).forEach { i ->
                val nr = r + dr[i]
                val nc = c + dc[i]
                val nextLocation = Location(nr, nc)

                if (nr in 0 until m && nc in 0 until n && visited[nr][nc] == 0) {
                    val nextHeight = heightmap[nextLocation.r][nextLocation.c]
                    if (curHeight == 'S') {
                        if (nextHeight == 'a') {
                            visited[nr][nc] = curVisit + 1
                            queue.add(nextLocation)
                        }
                    } else if (curHeight == 'a') {
                        if (nextHeight == 'b') {
                            visited[nr][nc] = curVisit + 1
                            queue.add(nextLocation)
                        }
                    } else if (nextHeight == 'E') {
                        if (curHeight == 'z') {
                            visited[nr][nc] = curVisit + 1
                            queue.add(nextLocation)
                        }
                    } else if (heightmap[nr][nc].code <= heightmap[r][c].code + 1) {
                        visited[nr][nc] = curVisit + 1
                        queue.add(nextLocation)
                    }
                }
            }
        }

        return fewestSteps
    }

    (part1() to 456).verify()
    (part2() to 454).verify()
}
