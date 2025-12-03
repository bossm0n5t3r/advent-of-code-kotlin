package utils

interface Problem<T> {
    fun part1(): T

    fun part2(): T
}

fun <T> Problem<T>.runProblem(
    part1Answer: T,
    part2Answer: T,
) {
    println("=== ${this::class.simpleName} ===")
    println()

    val part1 =
        runCatching { part1() }
            .onFailure {
                println("Failed part1(): ${it.message}")
                it.printStackTrace()
            }.getOrNull()
    val part2 =
        runCatching { part2() }
            .onFailure {
                println("Failed part2(): ${it.message}")
                it.printStackTrace()
            }.getOrNull()

    println()
    println("part1: $part1, expected: $part1Answer, result: ${part1 == part1Answer}")
    println("part2: $part2, expected: $part2Answer, result: ${part2 == part2Answer}")
}
