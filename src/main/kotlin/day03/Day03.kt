package day03

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.IntStream

data class Digit(val i: Int, val j: Int, val digit: Char)

fun Char.isSymbol(): Boolean = !isDigit() && this != '.'

fun <T> Array<Array<T>>.isInBound(i: Int, j: Int) = i >= 0 && i < this.size && j >= 0 && j < this[i].size

fun <T, U> Array<Array<T>>.ifInBound(i: Int, j: Int, default: U, consumer: (value: T) -> U): U =
  if (this.isInBound(i, j)) consumer(this[i][j]) else default

fun Array<Array<Char>>.isSymbol(i: Int, j: Int) = ifInBound(i, j, false) { it.isSymbol() }

val CHECK_SYMBOL_OFFSETS = listOf(
  Pair(+1,  0),
  Pair(-1,  0),
  Pair( 0, +1),
  Pair( 0, -1),
  Pair(+1, -1),
  Pair(-1, +1),
  Pair(+1, +1),
  Pair(-1, -1),
)

fun Array<Array<Char>>.isSymbolAround(i: Int, j: Int): Boolean {
  return CHECK_SYMBOL_OFFSETS.any { (di, dj) -> isSymbol(i + di, j + dj) }
}

fun Array<Array<Char>>.ifSymbolAround(i: Int, j: Int, onSymbolAround: () -> Unit) {
  if (isSymbolAround(i, j)) {
    onSymbolAround()
  }
}

fun Array<Array<Char>>.expandDigits(i: Int, j: Int): List<Digit> {
  if (!this[i][j].isDigit()) {
    return emptyList()
  }

  val result = mutableListOf<Digit>()

  result.addAll(collectAdjacentDigits(i, j) { it + 1 })
  result.addAll(collectAdjacentDigits(i, j) { it - 1 })
  result.add(Digit(i, j, this[i][j]))

  return result
}

fun Array<Array<Char>>.collectAdjacentDigits(i: Int, j: Int, nextInt: (Int) -> Int): List<Digit> {
  return IntStream.iterate(nextInt(j), nextInt)
    .takeWhile { isInBound(i, it) }
    .filter { this[i][it].isDigit() }
    .mapToObj { Digit(i, it, this[i][it]) }
    .toList()
}

fun solvePart1(lines: List<String>): Int {
  val numbers: MutableList<String> = mutableListOf()
  val matrix: Array<Array<Char>> = Array(lines.size) { i -> Array(lines[i].length) { j -> lines[i][j] } }


  val digits: MutableList<Char> = mutableListOf()
  var digitsHasSurroundingSymbol = false

  matrix.forEachIndexed { i, line ->
    line.forEachIndexed { j, char ->
      if (char.isDigit()) {
        digits.add(matrix[i][j])
        matrix.ifSymbolAround(i, j) {
          digitsHasSurroundingSymbol = true
        }
      }

      if ((!char.isDigit() || j == line.size - 1)) {
        if (digitsHasSurroundingSymbol) {
          numbers.add(String(digits.toCharArray()))
        }
        digits.clear()
        digitsHasSurroundingSymbol = false
      }
    }
  }

  return numbers.sumOf { it.toInt() }
}

fun solvePart2(lines: List<String>): Int {
  return 0
}

fun main() {
  val testData = Files.readAllLines(Path.of("./input/03/input.test.txt"))
  val data = Files.readAllLines(Path.of("./input/03/input.txt"))

  println("Part1 [TEST] : ${solvePart1(testData)}")
  println("Part1        : ${solvePart1(data)}")

  println("Part2 [TEST] : ${solvePart2(testData)}")
  println("Part2        : ${solvePart2(data)}")
}
