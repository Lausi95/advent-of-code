package day04

import java.nio.file.Files
import java.nio.file.Path

fun solvePart1(input: List<String>): Int {
  return 0
}

fun solvePart2(input: List<String>): Int {
  return 0
}

fun main() {
  val testData = Files.readAllLines(Path.of("./input/04/input.test.txt"))
  val data = Files.readAllLines(Path.of("./input/04/input.txt"))

  println("Part1 [TEST] : ${day03.solvePart1(testData)}")
  println("Part1        : ${day03.solvePart1(data)}")

  println("Part2 [TEST] : ${day03.solvePart2(testData)}")
  println("Part2        : ${day03.solvePart2(data)}")
}
