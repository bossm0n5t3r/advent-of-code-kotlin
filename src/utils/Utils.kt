package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads and works for each line from the given input txt file.
 */
fun PuzzleInput.readInputForEachLine(work: (String) -> Unit) {
    File("src/resources/aoc$year", filename).inputStream().bufferedReader().forEachLine {
        work(it)
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
