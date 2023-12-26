package day07

import Inputs
import measure
import kotlin.streams.toList

val symbolToValue = mapOf(
  Pair('T', 10L),
  Pair('J', 1L),
  Pair('Q', 12L),
  Pair('K', 13L),
  Pair('A', 14L)
)

fun Char.toCardValue(): Long = if (isDigit()) "$this".toLong() else symbolToValue[this] ?: throw Exception("Illegal Card: $this")

data class Hand(val cards: List<Long>, val bid: Long): Comparable<Hand> {
  companion object {
    fun parse(input: String): Hand {
      val parts = input.split(" ")
      val cards: List<Long> = parts[0].chars().mapToLong{ it.toChar().toCardValue() }.toList()
      val bid = parts[1].toLong()
      return Hand(cards, bid)
    }
  }

  private fun strength(): Int {
    if (cards.contains(symbolToValue['J'])) {
      return (2L..14L)
        .map { i -> cards.map { c -> if (c == symbolToValue['J']) i else c } }
        .maxOfOrNull { Hand(it, 0).strength() } ?: 0
    }

    val groupSizes = cards.groupBy { it }.map { it.value.size }
    val maxGroupSize = groupSizes.max()
    val twoPair = groupSizes.count { it == 2 } == 2
    val fullHouse = groupSizes.contains(3) && groupSizes.contains(2)

    return if (maxGroupSize == 5) 7
      else if (maxGroupSize == 4) 6
      else if (fullHouse) 5
      else if (maxGroupSize == 3) 4
      else if (twoPair) 3
      else if (maxGroupSize == 2) 2
      else if (maxGroupSize == 1) 1
      else -1
  }

  override fun compareTo(other: Hand): Int {
    return Comparator.comparing<Hand, Int> { it.strength() }
      .thenComparing( Comparator.comparing { it.cards[0] })
      .thenComparing( Comparator.comparing { it.cards[1] })
      .thenComparing( Comparator.comparing { it.cards[2] })
      .thenComparing( Comparator.comparing { it.cards[3] })
      .thenComparing( Comparator.comparing { it.cards[4] })
      .compare(this, other)
  }
}

fun solvePart1(input: List<String>): Long {
  return input.map { Hand.parse(it) }
    .sorted()
    .mapIndexed { index, it -> it.bid * (index + 1L)}
    .sum()
}

fun solvePart2(input: List<String>): Long = 0L

fun main() {
  val inputs = Inputs.load("07")

  measure("Part 1, Test") { solvePart1(inputs.testInput) }
  measure("Part 1      ") { solvePart1(inputs.actualInput) }
  measure("Part 2, Test") { solvePart2(inputs.testInput) }
  measure("Part 2      ") { solvePart2(inputs.actualInput) }
}
