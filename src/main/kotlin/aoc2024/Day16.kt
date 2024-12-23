package aoc2024

import utils.solve
import java.util.PriorityQueue

/**
 * --- Day 16: Reindeer Maze ---
 */
object Day16 {
    enum class Direction(
        val diff: Pair<Int, Int>,
    ) {
        UP(-1 to 0),
        DOWN(1 to 0),
        LEFT(0 to -1),
        RIGHT(0 to 1),
        ;

        fun rotateLeft(): Direction =
            when (this) {
                UP -> LEFT
                LEFT -> DOWN
                DOWN -> RIGHT
                RIGHT -> UP
            }

        fun rotateRight(): Direction =
            when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }
    }

    private fun List<String>.toGrid(): Map<Pair<Int, Int>, Char> =
        mapIndexed { r, row ->
            row.mapIndexed { c, x -> (r to c) to x }
        }.flatten().toMap()

    private fun Map<Pair<Int, Int>, Char>.find(target: Char): Pair<Int, Int> =
        entries
            .find {
                it.value == target
            }?.key ?: error("Start not found")

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = first + other.first to second + other.second

    private data class Tile(
        val position: Pair<Int, Int>,
        val direction: Direction,
        val cost: Int,
        val steps: MutableSet<Pair<Int, Int>> = mutableSetOf(),
    ) {
        val positionWithDirection = position to direction
    }

    fun part1() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val start = grid.find('S')
            val end = grid.find('E')
            val priorityQueue = PriorityQueue<Tile>(compareBy { it.cost })
            priorityQueue.add(Tile(start, Direction.RIGHT, 0))
            val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
            while (priorityQueue.isNotEmpty()) {
                val (cur, curDirection, step) = priorityQueue.poll()
                if (cur == end) {
                    return@solve step
                }
                visited += (cur to curDirection)
                priorityQueue +=
                    listOf(
                        Tile(cur, curDirection.rotateLeft(), step + 1000),
                        Tile(cur, curDirection.rotateRight(), step + 1000),
                        Tile(cur + curDirection.diff, curDirection, step + 1),
                    ).filter { it.positionWithDirection !in visited && grid[it.position] != '#' }
            }
            error("No path found")
        }

    fun part2() =
        solve(2024) { lines ->
            val grid = lines.toGrid()
            val start = grid.find('S')
            val end = grid.find('E')
            val priorityQueue = PriorityQueue<Tile>(compareBy { it.cost })
            priorityQueue.add(Tile(start, Direction.RIGHT, 0, mutableSetOf(start)))
            val visited = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
            var resultCost = Int.MAX_VALUE
            val result = mutableSetOf<Pair<Int, Int>>()
            while (priorityQueue.isNotEmpty()) {
                val (cur, curDirection, cost, steps) = priorityQueue.poll()
                if (cur == end) {
                    if (resultCost == Int.MAX_VALUE) {
                        resultCost = cost
                    }
                    if (cost == resultCost) {
                        result.addAll(steps)
                    }
                    continue
                }
                visited += (cur to curDirection)
                priorityQueue +=
                    listOf(
                        Tile(cur, curDirection.rotateLeft(), cost + 1000, steps.toMutableSet().apply { add(cur) }),
                        Tile(cur, curDirection.rotateRight(), cost + 1000, steps.toMutableSet().apply { add(cur) }),
                        Tile(cur + curDirection.diff, curDirection, cost + 1, steps.toMutableSet().apply { add(cur) }),
                    ).filter { it.positionWithDirection !in visited && grid[it.position] != '#' }
            }
            result.size + 1
        }
}
