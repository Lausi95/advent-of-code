package day02

import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Matcher
import java.util.regex.Pattern

data class Game(val gameNumber: Int, val rounds: List<Round>) {

  fun maxReds(): Int? = rounds.maxOfOrNull { it.reds }

  fun maxGreens(): Int? = rounds.maxOfOrNull { it.greens }

  fun maxBlues(): Int? = rounds.maxOfOrNull { it.blues }
}

data class Round(val blues: Int, val reds: Int, val greens: Int)

val INPUT_FILE: Path = Path.of("./input/02/input.txt")

val GAME_NUMBER_REGEX: Regex = "Game \\d+".toRegex()
val REDS_REGEX = "(\\d+) red".toRegex()
val BLUES_REGEX = "(\\d+) blue".toRegex()
val GREENS_REGEX = "(\\d+) green".toRegex()

fun parseInt(input: String, pattern: Regex, default: Int = 0): Int =
  pattern.matchEntire(input)?.groupValues?.get(1)?.toInt() ?: default

fun parseGameNumber(gameDefinition: String): Int = parseInt(gameDefinition, GAME_NUMBER_REGEX)

fun parseReds(round: String): Int = parseInt(round, REDS_REGEX)

fun parseGreens(round: String): Int = parseInt(round, GREENS_REGEX)

fun parseBlues(round: String): Int = parseInt(round, BLUES_REGEX)

fun parseRound(round: String): Round =
  Round(parseBlues(round), parseReds(round), parseGreens(round))

fun isPossible(game: Game, maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean =
  game.rounds.all { it.reds <= maxRed && it.greens <= maxGreen && it.blues <= maxBlue }

fun parseRounds(roundsDefinition: String): List<Round> =
  roundsDefinition.split(";")
    .map { round -> parseRound(round) }
    .toList()

fun <T> splitIntoGameDefinitionAndRounds(line: String, onPossible: (gameDefinition: String, roundsDefinition: String) -> T?): T? {
  val parts = line.split(":")
  if (parts.size != 2) {
    return null
  }
  return onPossible(parts[0], parts[1])
}

fun parseGame(gameDefinition: String, roundsDefinition: String): Game =
  Game(parseGameNumber(gameDefinition), parseRounds(roundsDefinition))

fun parseGame(line: String): Game? = splitIntoGameDefinitionAndRounds(line, ::parseGame)

fun solvePart1(lines: List<String>): Int = lines
  .mapNotNull { parseGame(it) }
  .filter { isPossible(it, 12, 13, 14) }
  .sumOf { it.gameNumber }

fun solvePart2(lines: List<String>): Int = lines
  .asSequence()
  .mapNotNull { parseGame(it) }
  .map { listOfNotNull(it.maxReds(), it.maxGreens(), it.maxBlues()) }
  .mapNotNull { it.reduceOrNull { acc, i -> acc * i } }
  .sum()

fun main() {
  println("Part1: ${solvePart1(Files.readAllLines(INPUT_FILE))}")
  println("Part2: ${solvePart2(Files.readAllLines(INPUT_FILE))}")
  println("Part2: ${solvePart2(Files.readAllLines(INPUT_FILE))}")
}
