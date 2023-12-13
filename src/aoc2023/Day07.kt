package aoc2023

import utils.solve
import utils.verify

/**
 * --- Day 7: Camel Cards ---
 */
fun main() {
    data class Card(val hand: String, val bid: Int, val stringToTypeAndLabels: (hand: String) -> Pair<Int, List<Int>>)

    val cardComparator =
        Comparator<Card> { o1: Card, o2: Card ->
            val (o1Type, o1Labels) = o1.stringToTypeAndLabels(o1.hand)
            val (o2Type, o2Labels) = o2.stringToTypeAndLabels(o2.hand)
            if (o1Type != o2Type) {
                o1Type - o2Type
            } else {
                o1Labels.zip(o2Labels).forEach { (o1Label, o2Label) ->
                    if (o1Label != o2Label) {
                        return@Comparator (o1Label - o2Label)
                    }
                }
                0
            }
        }

    fun keysLabelsAndMaxCardSizeToTypeAndLabels(
        keys: Set<Char>,
        labels: List<Int>,
        maxCardSize: Int,
    ): Pair<Int, List<Int>> {
        return if (keys.size == 1) {
            // - 6: Five of a kind, where all five cards have the same label: AAAAA
            6 to labels
        } else if (keys.size == 2) {
            // - 5: Four of a kind, where four cards have the same label and one card has a different label: AA8AA
            // - 4: Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
            if (maxCardSize == 4) {
                5 to labels
            } else {
                4 to labels
            }
        } else if (keys.size == 3) {
            // - 3: Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
            // - 2: Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
            if (maxCardSize == 3) {
                3 to labels
            } else {
                2 to labels
            }
        } else if (keys.size == 4) {
            1 to labels
        } else {
            0 to labels
        }
    }

    fun part1(): Int {
        val cardRank =
            mapOf(
                '2' to 0,
                '3' to 1,
                '4' to 2,
                '5' to 3,
                '6' to 4,
                '7' to 5,
                '8' to 6,
                '9' to 7,
                'T' to 8,
                'J' to 9,
                'Q' to 10,
                'K' to 11,
                'A' to 12,
            )

        fun stringToTypeAndLabels(hand: String): Pair<Int, List<Int>> {
            val charToCharList = hand.groupBy { it }
            val keys = charToCharList.keys
            val maxCardSize = keys.maxOf { charToCharList[it]?.size ?: 0 }
            val labels = hand.mapNotNull { cardRank[it] }
            return keysLabelsAndMaxCardSizeToTypeAndLabels(keys, labels, maxCardSize)
        }

        return solve { lines ->
            lines.map { line ->
                line.split(" ").let {
                    Card(it.first(), it.last().toInt(), ::stringToTypeAndLabels)
                }
            }
        }
            .sortedWith(cardComparator)
            .mapIndexed { index, card ->
                card.bid * (index + 1)
            }
            .sum()
    }

    fun part2(): Int {
        val cardRank =
            mapOf(
                'J' to 0,
                '2' to 1,
                '3' to 2,
                '4' to 3,
                '5' to 4,
                '6' to 5,
                '7' to 6,
                '8' to 7,
                '9' to 8,
                'T' to 9,
                'Q' to 10,
                'K' to 11,
                'A' to 12,
            )

        fun getCharToCharListWithoutJ(hand: String): Map<Char, List<Char>> {
            val charToCharList = hand.groupBy { it }.toMutableMap()
            val keys = charToCharList.keys

            if (keys.contains('J') && keys.size > 1) {
                val maxKeyWithoutJ =
                    (keys - 'J')
                        .toList()
                        .map {
                            it to (charToCharList[it]?.size ?: 0)
                        }
                        .sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { cardRank[it.first] })
                        .first()
                        .first
                charToCharList[maxKeyWithoutJ] =
                    charToCharList.getOrDefault(maxKeyWithoutJ, emptyList()) +
                    charToCharList.getOrDefault('J', emptyList())

                charToCharList.remove('J')
            }
            return charToCharList
        }

        fun stringToTypeAndLabels(hand: String): Pair<Int, List<Int>> {
            val charToCharList = getCharToCharListWithoutJ(hand)
            val keys = charToCharList.keys
            val maxCardSize = keys.maxOf { charToCharList[it]?.size ?: 0 }
            val labels = hand.mapNotNull { cardRank[it] }
            return keysLabelsAndMaxCardSizeToTypeAndLabels(keys, labels, maxCardSize)
        }

        return solve { lines ->
            lines.map { line ->
                line.split(" ").let {
                    Card(it.first(), it.last().toInt(), ::stringToTypeAndLabels)
                }
            }
        }
            .sortedWith(cardComparator)
            .mapIndexed { index, card ->
                card.bid * (index + 1)
            }
            .sum()
    }

    (part1() to 250058342).verify()
    (part2() to 250506580).verify()
}
