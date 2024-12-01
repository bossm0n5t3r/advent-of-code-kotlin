package utils

import kotlin.math.abs

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    ;

    fun reverse() =
        when (this) {
            NORTH -> SOUTH
            SOUTH -> NORTH
            WEST -> EAST
            EAST -> WEST
        }
}

enum class Turn {
    RIGHT,
    LEFT,
}

data class Point(
    val x: Int,
    val y: Int,
) {
    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    fun manhattan(other: Point) = abs(x - other.x) + abs(y - other.y)
}
