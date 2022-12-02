package aoc2022

import utils.PuzzleInput
import utils.readInputForEachLine

/**
 * --- Day 2: Rock Paper Scissors ---
 *
 * The score for a single round is the score for the shape you selected
 * (1 for Rock, 2 for Paper, and 3 for Scissors)
 * plus the score for the outcome of the round
 * (0 if you lost, 3 if the round was a draw, and 6 if you won).
 */
fun main() {
    fun part1(puzzleInput: PuzzleInput): Long {
        val rockPaperScissors = mapOf(
            "A" to 1,
            "B" to 2,
            "C" to 3,
            "X" to 1,
            "Y" to 2,
            "Z" to 3,
        )

        val scores = mapOf(
            0 to 3,
            1 to 6,
            2 to 0,
        )

        var totalScores = 0L
        puzzleInput.readInputForEachLine {
            val (opponentHand, myHand) = it.split(" ").map { hand ->
                rockPaperScissors[hand] ?: error("NOT FOUND HANDS : $hand")
            }
            val score = scores[(myHand - opponentHand + 3) % 3]
                ?: error("NOT FOUND SCORES - myHand: $myHand, opponentHand: $opponentHand")
            totalScores += (myHand + score)
        }
        return totalScores
    }

    fun part2(puzzleInput: PuzzleInput): Long {
        val rockPaperScissors = mapOf(
            "A" to 1,
            "B" to 2,
            "C" to 3,
        )
        val scores = mapOf(
            "X" to 0,
            "Y" to 3,
            "Z" to 6,
        )

        var totalScores = 0L
        puzzleInput.readInputForEachLine {
            val (opponentHandKey, scoreKey) = it.split(" ")
            val opponentHand = rockPaperScissors[opponentHandKey] ?: error("NOT FOUND HANDS : $opponentHandKey")
            val score = scores[scoreKey] ?: error("NOT FOUND SCORES - scoreKey: $scoreKey")

            val refinedScore = (score / 3 + 2) % 3
            val refinedOpponentHand = (opponentHand + 1) % 3
            val myHand = (refinedScore + refinedOpponentHand + 1) % 3 + 1
            totalScores += (score + myHand)
        }
        return totalScores
    }

    val puzzleInput = PuzzleInput(2022, "Day2.txt")
    println(part1(puzzleInput))
    println(part2(puzzleInput))
}
