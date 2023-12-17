package utils

fun gcm(
    a: Long,
    b: Long,
): Long {
    if (b == 0L) return a
    return gcm(b, a % b)
}

fun lcm(
    a: Long,
    b: Long,
): Long {
    return a * b / gcm(a, b)
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

inline fun <reified T> Array<Array<T>>.transpose(): Array<Array<T>> {
    return Array(this[0].size) { i -> Array(this.size) { j -> this[j][i] } }
}
