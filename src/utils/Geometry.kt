package utils

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
