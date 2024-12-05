package aoc2024

import utils.solve

object Day05 {
    private const val PAGE_SPLITTER = '|'
    private const val PAGE_COMMA = ','

    private fun List<String>.toSections(): Pair<Map<Int, Set<Int>>, List<String>> {
        val firstSection = mutableMapOf<Int, MutableSet<Int>>()
        val secondSection = mutableListOf<String>()
        for (line in this) {
            when {
                line.contains(PAGE_SPLITTER) -> {
                    val (before, after) = line.split(PAGE_SPLITTER).map { it.toInt() }
                    firstSection[before] = firstSection.getOrDefault(before, mutableSetOf()).apply { add(after) }
                }
                line.contains(PAGE_COMMA) -> secondSection.add(line)
            }
        }
        return firstSection to secondSection
    }

    private fun List<Int>.isRightOrder(firstSection: Map<Int, Set<Int>>): Boolean =
        this.withIndex().all { (index, page) ->
            firstSection.getOrDefault(page, emptySet()).containsAll(this.subList(index + 1, this.size).toSet())
        }

    private fun String.returnMiddleNumberIfRightOrderOrZero(firstSection: Map<Int, Set<Int>>): Int {
        val pages = this.split(PAGE_COMMA).map { it.toInt() }
        val isRightOrder = pages.isRightOrder(firstSection)
        return if (isRightOrder) pages[pages.size / 2] else 0
    }

    fun part1() =
        solve(2024) { lines ->
            val (firstSection, secondSection) = lines.toSections()
            secondSection.sumOf { it.returnMiddleNumberIfRightOrderOrZero(firstSection) }
        }

    private fun List<Int>.makeCorrectOrdering(firstSection: Map<Int, Set<Int>>): List<Int> {
        val candidates = this.toMutableList()
        val result = mutableListOf<Int>()
        while (candidates.isNotEmpty()) {
            val candidate =
                candidates.find {
                    val nextPages = firstSection[it] ?: emptySet()
                    nextPages.containsAll(candidates - it)
                } ?: error("Not found candidate")
            result.add(candidate)
            candidates.remove(candidate)
        }
        return result
    }

    private fun String.returnMiddleNumberIfIncorrectlyOrderOrZero(firstSection: Map<Int, Set<Int>>): Int {
        val pages = this.split(PAGE_COMMA).map { it.toInt() }
        val isRightOrder = pages.isRightOrder(firstSection)
        return if (isRightOrder.not()) pages.makeCorrectOrdering(firstSection).let { it[it.size / 2] } else 0
    }

    fun part2() =
        solve(2024) { lines ->
            val (firstSection, secondSection) = lines.toSections()
            secondSection.sumOf { it.returnMiddleNumberIfIncorrectlyOrderOrZero(firstSection) }
        }
}
