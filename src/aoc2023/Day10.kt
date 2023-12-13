package aoc2023

import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import utils.readInput
import utils.verify
import java.util.LinkedList
import java.util.Queue

/**
 * --- Day 10: Pipe Maze ---
 */
fun main() {
    val inputFile = "2023/Day10.txt"
    val tiles = charArrayOf('|', '-', 'L', 'J', '7', 'F')
    val pipes =
        mapOf(
            'S' to listOf(NORTH, EAST, SOUTH, WEST),
            '|' to listOf(SOUTH, NORTH),
            '-' to listOf(WEST, EAST),
            'L' to listOf(NORTH, EAST),
            'J' to listOf(NORTH, WEST),
            '7' to listOf(SOUTH, WEST),
            'F' to listOf(SOUTH, EAST),
        )
    val directionToTiles =
        mapOf(
            NORTH to setOf('|', '7', 'F'),
            SOUTH to setOf('|', 'L', 'J'),
            EAST to setOf('-', 'J', '7'),
            WEST to setOf('-', 'L', 'F'),
        )
    val directionToDiff =
        mapOf(
            NORTH to Pair(-1, 0),
            SOUTH to Pair(1, 0),
            EAST to Pair(0, 1),
            WEST to Pair(0, -1),
        )

    fun isConnected(
        direction: Direction,
        maze: Array<CharArray>,
        visited: Array<Array<BooleanArray>>,
        curR: Int,
        curC: Int,
        loopIndex: Int,
        loop: Array<MutableSet<Pair<Int, Int>>>,
        starting: Queue<Triple<Int, Int, Int>>,
    ) {
        val nr = curR + directionToDiff[direction]!!.first
        val nc = curC + directionToDiff[direction]!!.second
        if (nr !in maze.indices || nc !in maze.first().indices) return
        if (directionToTiles[direction]?.contains(maze[nr][nc]) == true && !visited[loopIndex][nr][nc]) {
            loop[loopIndex].add(nr to nc)
            starting.offer(Triple(nr, nc, loopIndex))
            visited[loopIndex][nr][nc] = true
        }
    }

    fun visitTile(
        tile: Char,
        maze: Array<CharArray>,
        visited: Array<Array<BooleanArray>>,
        curR: Int,
        curC: Int,
        loopIndex: Int,
        loop: Array<MutableSet<Pair<Int, Int>>>,
        starting: Queue<Triple<Int, Int, Int>>,
    ) {
        pipes[tile]?.forEach { direction ->
            isConnected(direction, maze, visited, curR, curC, loopIndex, loop, starting)
        }
    }

    fun part1(): Int {
        val lines = inputFile.readInput()
        val maze = lines.map { line -> line.toCharArray() }.toTypedArray()
        val starting: Queue<Triple<Int, Int, Int>> = LinkedList()
        val m = maze.size
        val n = maze.first().size

        val visited = Array(tiles.size) { Array(m) { BooleanArray(n) { false } } }
        val loop = Array(tiles.size) { mutableSetOf<Pair<Int, Int>>() }
        for (r in 0 until m) {
            for (c in 0 until n) {
                if (maze[r][c] == 'S') {
                    for (i in tiles.indices) {
                        starting.offer(Triple(r, c, i))
                        visited[i][r][c] = true
                    }
                }
            }
        }
        while (starting.isNotEmpty()) {
            val (curR, curC, loopIndex) = starting.poll()
            visited[loopIndex][curR][curC] = true
            visitTile(maze[curR][curC], maze, visited, curR, curC, loopIndex, loop, starting)
        }

        return loop.maxOf { it.size } / 2 + 1
    }

    fun part2(): Int {
        val dr = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
        val dc = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
        val lines = inputFile.readInput()
        val maze = mutableMapOf<Pair<Int, Int>, Char>()
        val starting: Queue<Pair<Int, Int>> = LinkedList()
        val visited = mutableSetOf<Pair<Int, Int>>()
        lines.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                maze[r to c] = char
                if (char == 'S') {
                    starting.offer(r to c)
                    visited.add(r to c)
                }
            }
        }

        while (starting.isNotEmpty()) {
            val (curR, curC) = starting.poll()
            visited.add(curR to curC)
            pipes[maze[curR to curC]]?.forEach { direction ->
                val nr = curR + directionToDiff[direction]!!.first
                val nc = curC + directionToDiff[direction]!!.second
                if (visited.contains(nr to nc).not()) {
                    val pipe = maze[nr to nc]
                    if (pipe != null && pipes[pipe]?.contains(direction.reverse()) == true) {
                        starting.offer(nr to nc)
                    }
                }
            }
        }

        val expandedGrid = mutableMapOf<Pair<Int, Int>, Char>()
        maze.forEach { (point, char) ->
            val expandedPoint = point.first * 3 to point.second * 3
            expandedGrid[expandedPoint] = if (char != '.' && point in visited) '#' else '.'

            for (i in 0 until 8) {
                val nr = expandedPoint.first + dr[i]
                val nc = expandedPoint.second + dc[i]
                expandedGrid[nr to nc] = '.'
            }
            if (point in visited) {
                pipes[char]!!.forEach {
                    val moved = expandedPoint.first + directionToDiff[it]!!.first to expandedPoint.second + directionToDiff[it]!!.second
                    expandedGrid[moved] = '#'
                }
            }
        }

        val toFlood = LinkedList<Pair<Int, Int>>()
        toFlood.add(0 to 0)
        while (toFlood.isNotEmpty()) {
            val current = toFlood.poll()
            expandedGrid[current] = '='
            for (i in 0 until 8) {
                val nr = current.first + dr[i]
                val nc = current.second + dc[i]

                if (expandedGrid[nr to nc] == '.' && nr to nc !in toFlood) {
                    toFlood.add(nr to nc)
                }
            }
        }

        return maze.keys.count { expandedGrid[it.first * 3 to it.second * 3] == '.' }
    }

    (part1() to 6701).verify()
    (part2() to 303).verify()
}
