package aoc2024

import utils.solve

object Day03 {
    private const val MUL = "mul"
    private const val MUL_PATTERN = "$MUL\\([0-9]{1,3},[0-9]{1,3}\\)"
    private const val DO = "do()"
    private const val DONT = "don't()"

    private val MUL_REGEX = Regex(MUL_PATTERN)
    private val DO_OR_DONT_OR_MUL_REGEX = Regex("do\\(\\)|don't\\(\\)|$MUL_PATTERN")

    fun part1() =
        solve(2024) { lines ->
            MUL_REGEX
                .findAll(lines.joinToString(""))
                .asIterable()
                .map { it.value }
                .toList()
                .sumOf { matched -> matched.calculate() }
        }

    private fun String.calculate(): Long =
        this
            .substringAfter("$MUL(")
            .substringBefore(")")
            .split(",")
            .fold(1) { acc, value -> acc * value.toLong() }

    fun part2() =
        solve(2024) { lines ->
            DO_OR_DONT_OR_MUL_REGEX
                .findAll(lines.joinToString(""))
                .map { it.value }
                .toList()
                .let { matchResultList ->
                    var result = 0L
                    var enabled = true
                    for (matchResult in matchResultList) {
                        when {
                            matchResult.startsWith(MUL) -> {
                                if (enabled) {
                                    result += matchResult.calculate()
                                }
                            }

                            matchResult == DO -> enabled = true
                            matchResult == DONT -> enabled = false
                        }
                    }
                    result
                }
        }
}
