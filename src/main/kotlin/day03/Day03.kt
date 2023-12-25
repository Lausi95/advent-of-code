package day03

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.IntStream

data class Digit(val i: Int, val j: Int, val digit: Char)

typealias Digits = Set<Digit>

typealias Matrix = Array<Array<Char>>

fun Char.isSymbol(): Boolean = !isDigit() && this != '.'

fun Char.isGear(): Boolean = this == '*'

fun Matrix.isInBound(i: Int, j: Int) = i >= 0 && i < this.size && j >= 0 && j < this[i].size

fun <U> Matrix.ifInBound(i: Int, j: Int, default: U, consumer: (value: Char) -> U): U =
  if (this.isInBound(i, j)) consumer(this[i][j]) else default

fun Matrix.isSymbol(i: Int, j: Int) = ifInBound(i, j, false) { it.isSymbol() }

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

fun Digits.toInt(): Int {
  return this.sortedBy { it.j }
    .map { it.digit }
    .joinToString("")
    .toInt()
}

fun Digits.group(): Set<Digits> {
  val sets = mutableSetOf<MutableSet<Digit>>()
  forEach { digit ->
    val set = sets.find { it.any { d -> d.j + 1 == digit.j || d.j - 1 == digit.j }}
    if (set == null) {
      sets.add(mutableSetOf(digit))
    } else {
      set.add(digit)
    }
  }
  return sets
}

fun Matrix.around(i: Int, j: Int): List<Pair<Int, Int>> {
  return CHECK_SYMBOL_OFFSETS
    .map { (di, dj) -> Pair(i + di, j + dj) }
    .filter { (ii, jj) -> isInBound(ii, jj) }
}

fun Matrix.isSymbolAround(i: Int, j: Int): Boolean {
  return CHECK_SYMBOL_OFFSETS.any { (di, dj) -> isSymbol(i + di, j + dj) }
}

fun Matrix.ifSymbolAround(i: Int, j: Int, onSymbolAround: () -> Unit) {
  if (isSymbolAround(i, j)) {
    onSymbolAround()
  }
}

fun Matrix.expandDigits(i: Int, j: Int): Set<Digit> {
  if (!this[i][j].isDigit()) {
    return emptySet()
  }

  val result = mutableSetOf<Digit>()

  result.addAll(collectAdjacentDigits(i, j) { it + 1 })
  result.addAll(collectAdjacentDigits(i, j) { it - 1 })
  result.add(Digit(i, j, this[i][j]))

  return result
}

fun Matrix.collectAdjacentDigits(i: Int, j: Int, nextInt: (Int) -> Int): List<Digit> {
  return IntStream.iterate(nextInt(j), nextInt)
    .takeWhile { isInBound(i, it) }
    .filter { this[i][it].isDigit() }
    .mapToObj { Digit(i, it, this[i][it]) }
    .toList()
}

fun List<String>.toMatrix(): Array<Array<Char>> = Array(this.size) { i -> Array(this[i].length) { j -> this[i][j] } }

fun <T> Matrix.iterate(step: (i: Int, j: Int, c: Char) -> T) : List<T> {
  val lst = mutableListOf<T>()
  this.forEachIndexed { i, line ->
    line.forEachIndexed { j, char ->
      lst.add(step(i, j, char))
    }
  }
  return lst
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

data class Vec(val i: Int, val j: Int, val value: Char)



fun solvePart2(lines: List<String>): Int {
  val matrix = lines.toMatrix()
  return matrix.iterate { i, j, c ->
    if (!c.isGear()) {
      return@iterate emptyList()
    }

    val numbersAroundGear = matrix.around(i, j)
      .filter { (ii, jj) -> matrix[ii][jj].isDigit() }
      .flatMap { (ii, jj) -> matrix.expandDigits(ii, jj) }
      .distinct()
      .toSet()
      .group()
      .map { it.toInt() }

    if (numbersAroundGear.size != 2) {
      return@iterate emptyList()
    }

    return@iterate listOf(Pair(numbersAroundGear[0], numbersAroundGear[1]))
  }.flatten().sumOf { (a, b) -> a * b }
}

fun main() {
  val testData = Files.readAllLines(Path.of("./input/03/input.test.txt"))
  val data = Files.readAllLines(Path.of("./input/03/input.txt"))

  println("Part1 [TEST] : ${solvePart1(testData)}")
  println("Part1        : ${solvePart1(data)}")

  println("Part2 [TEST] : ${solvePart2(testData)}")
  println("Part2        : ${solvePart2(data)}")
}
