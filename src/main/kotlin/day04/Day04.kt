package day04

import measure
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.pow
import kotlin.system.measureTimeMillis

data class Card(val cardNumber: Int, val winningNumbers: List<Int>, val numbers: List<Int>, var instances: Int = 1) {
  companion object {
    fun parse(str: String): Card {
      val split1 = str.split(":")
      val cardNumber = split1[0].split(" ").filter { it.isNotBlank() }[1].toInt()

      val split2 = split1[1].split("|")
      val winningNumbers = split2[0].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
      val numbers = split2[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }

      return Card(cardNumber, winningNumbers, numbers)
    }
  }

  fun score(): Int {
    val amount = matches()
    if (amount == 0)
      return 0
    return 2.0.pow(amount.toDouble() - 1).toInt()
  }

  fun matches(): Int = winningNumbers.count { numbers.contains(it) }
}

fun solvePart1(input: List<String>): Int {
  return input.map(Card::parse).sumOf(Card::score)
}

fun solvePart2(input: List<String>): Int {
  val cards = input.map(Card::parse)
  for (card in cards) {
    for (x in 1..card.matches()) {
      cards.find { it.cardNumber == card.cardNumber + x }?.also {
        it.instances += 1 * card.instances
      }
    }
  }
  return cards.sumOf { it.instances }
}

fun main() {
  val testData = Files.readAllLines(Path.of("./input/04/input.test.txt"))
  val data = Files.readAllLines(Path.of("./input/04/input.txt"))

  measure("Warmup") { solvePart1(testData) }

  measure("Part 1 Test") { solvePart1(testData) }
  measure("Part 1     ") { solvePart1(data) }
  measure("Part 2 Test") { solvePart2(testData) }
  measure("Part 2     ") { solvePart2(data) }
}
