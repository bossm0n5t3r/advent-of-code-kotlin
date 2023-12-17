package aoc2023

import utils.Point
import utils.solve
import utils.verify

/**
 * --- Day 11: Cosmic Expansion ---
 */
fun main() {
    fun getNoGalaxiesRowsAndColsAndGalaxies(image: List<List<String>>): Triple<Set<Int>, Set<Int>, List<Point>> {
        val noGalaxiesRows = mutableSetOf<Int>()
        val noGalaxiesCols = image.first().indices.toMutableSet()
        val galaxies = mutableSetOf<Point>()

        for ((r, row) in image.withIndex()) {
            var noGalaxiesInRow = true
            for ((c, char) in row.withIndex()) {
                if (char == "#") {
                    galaxies.add(Point(r, c))
                    noGalaxiesInRow = false
                    if (noGalaxiesCols.contains(c)) {
                        noGalaxiesCols.remove(c)
                    }
                }
            }
            if (noGalaxiesInRow) {
                noGalaxiesRows.add(r)
            }
        }
        return Triple(noGalaxiesRows, noGalaxiesCols, galaxies.toList())
    }

    fun part1() =
        solve { lines ->
            val image =
                lines
                    .map { it.split("").toMutableList() }
                    .toMutableList()

            val (
                noGalaxiesRows,
                noGalaxiesCols,
                galaxiesList,
            ) = getNoGalaxiesRowsAndColsAndGalaxies(image)

            var result = 0
            while (galaxiesList.isNotEmpty()) {
                val cur = galaxiesList.removeFirst()
                for (other in galaxiesList) {
                    val dr = (minOf(cur.x, other.x)..<maxOf(cur.x, other.x)).toSet()
                    val dc = (minOf(cur.y, other.y)..<maxOf(cur.y, other.y)).toSet()
                    result += dr.size + dc.size + noGalaxiesRows.intersect(dr).size + noGalaxiesCols.intersect(dc).size
                }
            }

            result
        }

    fun part2() =
        solve { lines ->
            val image =
                lines
                    .map { it.split("").toMutableList() }
                    .toMutableList()

            val (
                noGalaxiesRows,
                noGalaxiesCols,
                galaxiesList,
            ) = getNoGalaxiesRowsAndColsAndGalaxies(image)

            var result = 0L
            val times = 1_000_000 - 1
            while (galaxiesList.isNotEmpty()) {
                val cur = galaxiesList.removeFirst()
                for (other in galaxiesList) {
                    val dr = (minOf(cur.x, other.x)..<maxOf(cur.x, other.x)).toSet()
                    val dc = (minOf(cur.y, other.y)..<maxOf(cur.y, other.y)).toSet()
                    result += dr.size + dc.size
                    result += noGalaxiesRows.intersect(dr).size * times + noGalaxiesCols.intersect(dc).size * times
                }
            }

            result
        }

    (part1() to 9522407).verify()
    (part2() to 544723432977L).verify()
}
