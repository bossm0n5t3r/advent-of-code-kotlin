package aoc2023

import utils.readInput
import utils.verify

/**
 * --- Day 2: Cube Conundrum ---
 */
fun main() {
    val inputFile = "2023/Day02.txt"

    data class Cubes(
        val red: Int = 0,
        val green: Int = 0,
        val blue: Int = 0,
    ) {
        fun isPossible(
            redCubes: Int,
            greenCubes: Int,
            blueCubes: Int,
        ): Boolean {
            return red <= redCubes && green <= greenCubes && blue <= blueCubes
        }

        fun update(otherCubes: Cubes): Cubes {
            return Cubes(
                red = maxOf(this.red, otherCubes.red),
                green = maxOf(this.green, otherCubes.green),
                blue = maxOf(this.blue, otherCubes.blue),
            )
        }

        fun power() = red * green * blue
    }

    fun List<String>.parseCube(cubeColor: String): Int {
        return this.firstOrNull { it.contains(cubeColor) }
            ?.replace(cubeColor, "")
            ?.trim()
            ?.toInt()
            ?: 0
    }

    fun String.toCubes(): Cubes {
        val splitSubsetOfCubes = this.split(",")
        return Cubes(
            red = splitSubsetOfCubes.parseCube("red"),
            green = splitSubsetOfCubes.parseCube("green"),
            blue = splitSubsetOfCubes.parseCube("blue"),
        )
    }

    fun part1(
        redCubes: Int,
        greenCubes: Int,
        blueCubes: Int,
    ): Int {
        return inputFile.readInput().sumOf { line ->
            val id = line.substringBefore(":").replace("Game", "").trim().toInt()
            val subSets = line.substringAfter("Game $id: ")
            for (subSetOfCubes in subSets.split(";")) {
                val cubes = subSetOfCubes.toCubes()
                if (cubes.isPossible(redCubes, greenCubes, blueCubes).not()) {
                    return@sumOf 0
                }
            }
            id
        }
    }

    fun part2(): Int {
        return inputFile.readInput().sumOf { line ->
            val id = line.substringBefore(":").replace("Game", "").trim().toInt()
            val subSets = line.substringAfter("Game $id: ")
            var fewestCubes = Cubes()
            for (subSetOfCubes in subSets.split(";")) {
                val cubes = subSetOfCubes.toCubes()
                fewestCubes = fewestCubes.update(cubes)
            }
            fewestCubes.power()
        }
    }

    (part1(12, 13, 14) to 2563).verify()
    (part2() to 70768).verify()
}
