package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Year

private const val RESOURCE_PATH = "src/main/resources"

fun String.asInputStream() = File("$RESOURCE_PATH/aoc$this").inputStream()

/**
 * Reads and works for each line from the given input txt file.
 */
fun String.readInputForEachLine(work: (String) -> Unit) =
    this.asInputStream().bufferedReader().forEachLine {
        work(it)
    }

fun <T> String.mapInputForEachLine(work: (String) -> T): List<T> =
    this.asInputStream().bufferedReader().readLines().map {
        work(it)
    }

fun String.readInput() = this.asInputStream().bufferedReader().readLines()

fun <T> solve(
    year: Int,
    function: (lines: List<String>) -> T,
): T = function(getInputFile(Year.of(year)).readLines())

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

/**
 * Print first and Verify first == second
 */
fun <T> Pair<T, T>.verify() {
    println(first)
    require(first == second) {
        error("first != second - first: $first, second: $second")
    }
}

fun getInputFile(year: Year = Year.now()): File {
    val name = Throwable().stackTrace.first { it.className.contains("Day") }.fileName
    requireNotNull(name) { "Not found file name" }
    val day = name.substringBefore(".kt").removePrefix("Day").padStart(2, '0')
    val file = File("$RESOURCE_PATH/aoc$year/Day$day.txt")
    return if (file.readText().isBlank()) {
        File("src/main/kotlin/day$day/input1.txt")
    } else {
        file
    }
}
