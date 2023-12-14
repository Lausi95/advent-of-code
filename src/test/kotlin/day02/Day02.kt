package day02

import aoc.someInt
import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertEquals

class Day02Test {

  @RepeatedTest(10)
  fun testParseReds() {
    val someReds = someInt()

    val parsedReds = parseReds("$someReds red")

    assertEquals(someReds, parsedReds)
  }

  @RepeatedTest(10)
  fun testParseGreens() {
    val someReds = someInt()

    val parsedReds = parseGreens("$someReds green")

    assertEquals(someReds, parsedReds)
  }

  @RepeatedTest(10)
  fun testParseBlues() {
    val someReds = someInt()

    val parsedReds = parseBlues("$someReds blue")

    assertEquals(someReds, parsedReds)
  }

  @RepeatedTest(10)
  fun testParseRound() {
    val someReds = someInt()
    val someBlues = someInt()
    val someGreens = someInt()
    val someRound = "$someReds red $someGreens green $someBlues blue"

    val round = parseRound(someRound)

    assertEquals(Round(someBlues, someReds, someGreens), round)
  }

  @RepeatedTest(10)
  fun testParseGameNumber() {
    val someGameNumber = someInt()
    val someGameDefinition = "Game $someGameNumber"

    val gameNumber = parseGameNumber(someGameDefinition)

    assertEquals(someGameNumber, gameNumber)
  }
}


