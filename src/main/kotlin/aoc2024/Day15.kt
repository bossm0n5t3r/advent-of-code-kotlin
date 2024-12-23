package aoc2024

import utils.solve

/**
 * --- Day 15: Warehouse Woes ---
 */
object Day15 {
    private const val FLOOR = '.'
    private const val WALL = '#'
    private const val SINGLE_BOX = 'O'
    private const val ROBOT = '@'
    private const val LEFT_BOX = '['
    private const val RIGHT_BOX = ']'
    private const val UP = '^'
    private const val DOWN = 'v'
    private const val LEFT = '<'
    private const val RIGHT = '>'

    private fun String.twice() =
        this
            .replace("$WALL", "$WALL$WALL")
            .replace("$SINGLE_BOX", "$LEFT_BOX$RIGHT_BOX")
            .replace("$FLOOR", "$FLOOR$FLOOR")
            .replace("$ROBOT", "$ROBOT$FLOOR")

    private fun List<String>.toMapAndCommand(twice: Boolean = false): Pair<List<String>, List<String>> {
        val map = mutableListOf<String>()
        val commands = mutableListOf<String>()
        var isMap = true
        forEach { line ->
            when {
                line.isEmpty() -> isMap = false
                isMap -> map.add(if (twice) line.twice() else line)
                else -> commands.add(line)
            }
        }
        return map to commands
    }

    private fun List<String>.toGrid(): Map<Pair<Int, Int>, Char> =
        mapIndexed { r, row ->
            row.mapIndexed { c, x -> (r to c) to x }
        }.flatten().toMap()

    private fun Pair<Int, Int>.move(direction: Char): Pair<Int, Int> =
        when (direction) {
            UP -> first - 1 to second
            DOWN -> first + 1 to second
            LEFT -> first to second - 1
            RIGHT -> first to second + 1
            else -> error("Invalid direction: $direction")
        }

    private fun walk(
        map: Map<Pair<Int, Int>, Char>,
        direction: Char,
    ): Map<Pair<Int, Int>, Char> {
        val robot = map.entries.find { it.value == ROBOT }?.key ?: error("Robot not found")

        val movingPieces = mutableSetOf<Pair<Int, Int>>()

        fun canPush(from: Pair<Int, Int>): Boolean =
            when (val element = map.getValue(from)) {
                ROBOT, SINGLE_BOX -> {
                    movingPieces += from
                    canPush(from.move(direction))
                }
                FLOOR -> true
                WALL -> false
                LEFT_BOX, RIGHT_BOX -> {
                    val pushSelf = canPush(from.move(direction))
                    movingPieces += from
                    if (direction == UP || direction == DOWN) {
                        val boxDirection = if (element == LEFT_BOX) RIGHT else LEFT
                        val otherHalf = from.move(boxDirection)
                        movingPieces += otherHalf
                        pushSelf && canPush(otherHalf.move(direction))
                    } else {
                        pushSelf
                    }
                }
                else -> error("Invalid element: $element")
            }

        if (!canPush(robot)) return map

        return map.toMutableMap().apply {
            putAll(movingPieces.associateWith { FLOOR })
            movingPieces.forEach {
                this[it.move(direction)] = map.getValue(it)
            }
        }
    }

    private fun Pair<Int, Int>.getGPSCoordinates() = (first * 100 + second).toLong()

    private fun run(
        lines: List<String>,
        twice: Boolean,
    ): Long {
        val (map, commands) = lines.toMapAndCommand(twice)
        var grid = map.toGrid()
        for (command in commands.joinToString("")) {
            grid = walk(grid, command)
        }
        return grid.filter { it.value == LEFT_BOX || it.value == SINGLE_BOX }.keys.sumOf { it.getGPSCoordinates() }
    }

    fun part1() =
        solve(2024) { lines ->
            run(lines, false)
        }

    fun part2() =
        solve(2024) { lines ->
            run(lines, true)
        }
}
