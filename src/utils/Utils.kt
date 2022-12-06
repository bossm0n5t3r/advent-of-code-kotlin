package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads and works for each line from the given input txt file.
 */
fun String.readInputForEachLine(work: (String) -> Unit) {
    File("src/resources/aoc$this").inputStream().bufferedReader().forEachLine {
        work(it)
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Print first and Verify first == second
 */
fun <T> Pair<T, T>.verify() {
    println(first)
    require(first == second)
}
