package aoc2023

import utils.solve
import utils.verify

/**
 * --- Day 5: If You Give A Seed A Fertilizer ---
 */
fun main() {
    val inputFile = "2023/Day05.txt"

    data class Mapping(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
        fun toDestinationOrNull(source: Long): Long? {
            if (source >= sourceRangeStart && source < sourceRangeStart + rangeLength) {
                return source - sourceRangeStart + destinationRangeStart
            }
            return null
        }

        fun toSourceOrNull(destination: Long): Long? {
            if (destination >= destinationRangeStart && destination < destinationRangeStart + rangeLength) {
                return destination - destinationRangeStart + sourceRangeStart
            }
            return null
        }
    }

    fun String.toMapping(): Mapping {
        val split = this.split(" ")
        return Mapping(
            split[0].toLong(),
            split[1].toLong(),
            split[2].toLong(),
        )
    }

    data class AllMappings(
        val seedToSoils: MutableList<Mapping> = mutableListOf(),
        val soilToFertilizers: MutableList<Mapping> = mutableListOf(),
        val fertilizerToWaters: MutableList<Mapping> = mutableListOf(),
        val waterToLights: MutableList<Mapping> = mutableListOf(),
        val lightToTemperatures: MutableList<Mapping> = mutableListOf(),
        val temperatureToHumidities: MutableList<Mapping> = mutableListOf(),
        val humidityToLocations: MutableList<Mapping> = mutableListOf(),
    )

    fun parseAllMappings(lines: List<String>): AllMappings {
        val allMappings = AllMappings()
        var index = 0
        while (index < lines.size) {
            val line = lines[index]
            val target =
                when (line) {
                    "seed-to-soil map:" -> allMappings.seedToSoils
                    "soil-to-fertilizer map:" -> allMappings.soilToFertilizers
                    "fertilizer-to-water map:" -> allMappings.fertilizerToWaters
                    "water-to-light map:" -> allMappings.waterToLights
                    "light-to-temperature map:" -> allMappings.lightToTemperatures
                    "temperature-to-humidity map:" -> allMappings.temperatureToHumidities
                    "humidity-to-location map:" -> allMappings.humidityToLocations
                    else -> {
                        index++
                        continue
                    }
                }
            while (index + 1 < lines.size && lines[index + 1].isNotBlank()) {
                target.add(lines[index + 1].toMapping())
                index++
            }
            index++
        }
        return allMappings
    }

    fun part1() =
        solve(inputFile) { lines ->
            val seeds =
                lines
                    .first()
                    .substringAfter("seeds: ")
                    .split(" ")
                    .map { it.toLong() }

            val allMappings = parseAllMappings(lines)

            fun List<Mapping>.getOrDefaultToDestination(source: Long): Long {
                return this.firstNotNullOfOrNull { it.toDestinationOrNull(source) } ?: source
            }

            seeds.minOf { seed ->
                allMappings.seedToSoils.getOrDefaultToDestination(seed)
                    .let { allMappings.soilToFertilizers.getOrDefaultToDestination(it) }
                    .let { allMappings.fertilizerToWaters.getOrDefaultToDestination(it) }
                    .let { allMappings.waterToLights.getOrDefaultToDestination(it) }
                    .let { allMappings.lightToTemperatures.getOrDefaultToDestination(it) }
                    .let { allMappings.temperatureToHumidities.getOrDefaultToDestination(it) }
                    .let { allMappings.humidityToLocations.getOrDefaultToDestination(it) }
            }
        }

    fun part2() =
        solve(inputFile) { lines ->
            data class Seed(val start: Long, val range: Long) {
                fun contains(target: Long) = target >= start && target < start + range
            }

            val seeds =
                lines
                    .first()
                    .substringAfter("seeds: ")
                    .split(" ")
                    .map { it.toLong() }
                    .chunked(2)
                    .map {
                        val (start, range) = it
                        Seed(start, range)
                    }

            val allMappings = parseAllMappings(lines)

            fun List<Mapping>.getOrDefaultToSource(source: Long): Long {
                return this.firstNotNullOfOrNull { it.toSourceOrNull(source) } ?: source
            }

            fun List<Seed>.contains(target: Long): Boolean {
                return this.any { it.contains(target) }
            }

            var location = 0L
            while (location < Long.MAX_VALUE) {
                val existSeed =
                    allMappings.humidityToLocations.getOrDefaultToSource(location)
                        .let { allMappings.temperatureToHumidities.getOrDefaultToSource(it) }
                        .let { allMappings.lightToTemperatures.getOrDefaultToSource(it) }
                        .let { allMappings.waterToLights.getOrDefaultToSource(it) }
                        .let { allMappings.fertilizerToWaters.getOrDefaultToSource(it) }
                        .let { allMappings.soilToFertilizers.getOrDefaultToSource(it) }
                        .let { allMappings.seedToSoils.getOrDefaultToSource(it) }
                        .let { seeds.contains(it) }
                if (existSeed) {
                    return@solve location
                }
                location++
            }
        }

    (part1() to 51752125L).verify()
    (part2() to 12634632L).verify()
}
