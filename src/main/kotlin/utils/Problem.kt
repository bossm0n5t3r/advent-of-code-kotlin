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

    val part1 = part1()
    val part2 = part2()

    println("part1: $part1, expected: $part1Answer, result: ${part1 == part1Answer}")
    println("part2: $part2, expected: $part2Answer, result: ${part2 == part2Answer}")
}
