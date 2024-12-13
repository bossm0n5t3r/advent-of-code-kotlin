package aoc2024

import utils.solve

/**
 * --- Day 12: Garden Groups ---
 */
object Day12 {
    private enum class Dir { U, R, D, L }

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second

    private fun Pair<Int, Int>.neighbours() =
        listOf(
            this + (-1 to 0) to Dir.U,
            this + (0 to 1) to Dir.R,
            this + (1 to 0) to Dir.D,
            this + (0 to -1) to Dir.L,
        )

    private fun List<Pair<Int, Int>>.continuous() =
        buildList<List<Pair<Int, Int>>> {
            val toGo = this@continuous.toMutableSet()
            while (toGo.isNotEmpty()) {
                add(
                    buildList {
                        val queue = mutableListOf(toGo.first().also { toGo.remove(it) })
                        while (queue.isNotEmpty()) {
                            val element = queue.removeFirst()
                            add(element)
                            element.neighbours().filter { (pos, _) -> pos in toGo }.forEach { (pos, _) ->
                                toGo.remove(pos)
                                queue.add(pos)
                            }
                        }
                    },
                )
            }
        }

    private fun List<String>.asRegions(): Collection<List<Pair<Int, Int>>> =
        flatMapIndexed { row, line -> line.indices.map { col -> row to col } }
            .groupBy { (r, c) -> this[r][c] }
            .values
            .flatMap { it.continuous() }

    private fun List<Pair<Int, Int>>.perimeter(): Set<Pair<Pair<Int, Int>, Dir>> =
        flatMap { it.neighbours().filterNot { (pos, _) -> pos in this } }.toSet()

    private fun Dir.turnRight(): Pair<Int, Int> =
        when (this) {
            Dir.U -> 0 to -1
            Dir.R -> 1 to 0
            Dir.D -> 0 to 1
            Dir.L -> -1 to 0
        }

    private fun Set<Pair<Pair<Int, Int>, Dir>>.discounted(): Set<Pair<Pair<Int, Int>, Dir>> =
        filterNot { (pos, dir) -> (pos + dir.turnRight() to dir) in this }.toSet()

    fun part1() =
        solve(2024) { lines ->
            lines.asRegions().sumOf { it.size * it.perimeter().size }
        }

    fun part2() =
        solve(2024) { lines ->
            lines.asRegions().sumOf { it.size * it.perimeter().discounted().size }
        }
}
