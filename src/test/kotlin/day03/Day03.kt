package day03

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04Test {

  @Test
  fun testSingleLine_NoNumbers() {
    val lines = listOf(
      "....."
    )

    val solution = solvePart1(lines)

    assertEquals(0, solution)
  }

  @Test
  fun testSingleLine_OneDigitNumberNoSymbols() {
    val lines = listOf(
      "..1.."
    )

    val solution = solvePart1(lines)

    assertEquals(0, solution)
  }

  @Test
  fun testSingleLine_MultiDigitNumberNoSymbols() {
    val lines = listOf(
      ".213."
    )

    val solution = solvePart1(lines)

    assertEquals(0, solution)
  }

  @Test
  fun testSingleLine_TwoNumberNoSymbols() {
    val lines = listOf(
      "12.34"
    )

    val solution = solvePart1(lines)

    assertEquals(0, solution)
  }

  @Test
  fun testSingleLine_OneDigitNumberWithSymbol() {
    val lines = listOf(
      "..x5.."
    )

    val solution = solvePart1(lines)

    assertEquals(5, solution)
  }
}
