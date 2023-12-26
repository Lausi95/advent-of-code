package day05

import Inputs
import measure
import java.util.regex.Pattern
import kotlin.math.min

val numberPattern: Pattern = Pattern.compile("\\d+")

fun getAllNumbers(line: String): List<Long> {
  val numbers = mutableListOf<Long>()
  val numberMatcher = numberPattern.matcher(line)
  while (numberMatcher.find()) {
    numbers.add(numberMatcher.group().toLong())
  }
  return numbers
}

fun getSeeds(input: List<String>): List<Long> {
  return input.find { it.startsWith("seeds:") }?.let { getAllNumbers(it) } ?: emptyList()
}

class TranslationRow(val target: Long, val source: Long, val range: Long) {

  companion object {
    fun parse(line: String): TranslationRow {
      val numbers = getAllNumbers(line)
      return TranslationRow(numbers[0], numbers[1], numbers[2])
    }
  }
}

class TranslationTable(val translationRows: List<TranslationRow>) {

  fun translate(input: Long): Long {
    return translationRows.find { input >= it.source && input < it.source + it.range }?.let {
      it.target + (input - it.source)
    } ?: input
  }
}

fun solvePart1(input: List<String>): Long {
  val seeds = getSeeds(input)
  val tables = parseTranslationTables(input)
  return seeds.minOfOrNull { tables.process(it) } ?: 0
}

fun transformSeeds(seeds: List<Long>): List<Long> {
  val newSeeds = mutableListOf<Long>()
  for (k in 0..<seeds.size / 2) {
    val start = seeds[k * 2]
    val range = seeds[k * 2 + 1]
    for (j in start..<start+range) {
      newSeeds.add(j)
    }
  }
  return newSeeds
}

fun solvePart2(input: List<String>): Long {
  val seedRanges = getSeeds(input)
  val tables = parseTranslationTables(input)

  var minSeed = Long.MAX_VALUE
  for (k in 0..<seedRanges.size / 2) {
    val start = seedRanges[k * 2]
    val range = seedRanges[k * 2 + 1]
    println("Range ${k + 1} / ${seedRanges.size / 2}: $range Seeds")
    for (j in start..<start+range) {
      if (j % 10_000_000 == 0L) {
        println("${(100 * (j - start)) / range}%")
      }
      minSeed = min(minSeed, tables.process(j))
    }
  }

  return minSeed
}

fun List<TranslationTable>.process(seed: Long): Long {
  var result = seed
  forEach {
    result = it.translate(result)
  }
  return result
}

private fun parseTranslationTables(input: List<String>): List<TranslationTable> {
  val tables = mutableListOf<TranslationTable>()
  var rows = mutableListOf<TranslationRow>()
  var start = false
  input.forEach {
    if (start && !it.contains("map")) {
      if (it.isNotBlank()) {
        rows.add(TranslationRow.parse(it))
      } else {
        tables.add(TranslationTable(rows))
        rows = mutableListOf()
      }
    }
    if (it.contains("map")) {
      start = true
    }
  }
  return tables
}

fun main() {
  val inputs = Inputs.load("05")

  measure("Part1 Test") { solvePart1(inputs.testInput) }
  measure("Part1     ") { solvePart1(inputs.actualInput) }
  measure("Part2 Test") { solvePart2(inputs.testInput) }
  measure("Part2     ") { solvePart2(inputs.actualInput) }
}
