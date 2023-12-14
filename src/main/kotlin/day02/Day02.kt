package day02

import java.nio.file.Files
import java.nio.file.Path
import java.util.Collections.max
import java.util.regex.Matcher
import java.util.regex.Pattern

data class Game(val gameNumber: Int, val rounds: List<Round>)

data class Round(val blues: Int, val reds: Int, val greens: Int)

val INPUT_FILE: Path = Path.of("./input/02/input.txt")

val GAME_NUMBER_PATTERN: Pattern = Pattern.compile("Game (?<gameNumber>\\d+)")
val REDS_PATTERN: Pattern = Pattern.compile("(?<amount>\\d+) red")
val GREEN_PATTERN: Pattern = Pattern.compile("(?<amount>\\d+) green")
val BLUE_PATTERN: Pattern = Pattern.compile("(?<amount>\\d+) blue")

fun <T> ifMatch(matcher: Matcher, default: T, onMatch: (Matcher) -> T): T {
  return if (matcher.find())
    onMatch(matcher)
  else
    default
}

fun parseInt(input: String, pattern: Pattern, groupName: String, default: Int = 0): Int =
  ifMatch(pattern.matcher(input), default) { it.group(groupName).toInt() }

fun parseGameNumber(gameDefinition: String): Int = parseInt(gameDefinition, GAME_NUMBER_PATTERN, "gameNumber")

fun parseReds(round: String): Int = parseInt(round, REDS_PATTERN, "amount")

fun parseGreens(round: String): Int = parseInt(round, GREEN_PATTERN, "amount")

fun parseBlues(round: String): Int = parseInt(round, BLUE_PATTERN, "amount")

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
  .mapNotNull { parseGame(it) }
  .sumOf { game -> max(game.rounds.map { it.blues }) * max(game.rounds.map { it.reds }) * max(game.rounds.map { it.greens }) }

fun main() {
  println("Part1: ${solvePart1(Files.readAllLines(INPUT_FILE))}")
  println("Part2: ${solvePart2(Files.readAllLines(INPUT_FILE))}")
}
