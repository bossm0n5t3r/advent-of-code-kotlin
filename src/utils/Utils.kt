package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun String.asInputStream() = File("src/resources/aoc$this").inputStream()

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
    fileName: String,
    function: (lines: List<String>) -> T,
): T = function(fileName.readInput())

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
