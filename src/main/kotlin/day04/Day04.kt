package day04

import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.measureTimeMillis

fun solvePart1(input: List<String>): Int {
  return 0
}

fun solvePart2(input: List<String>): Int {
  return 0
}

fun <T> measure(name: String, fn: () -> T): T {
  var solution: T
  val time = measureTimeMillis {
    solution = fn()
  }
  println("$name took ${time}ms: $solution")
  return solution
}

fun main() {
  val testData = Files.readAllLines(Path.of("./input/04/input.test.txt"))
  val data = Files.readAllLines(Path.of("./input/04/input.txt"))

  measure("Part 1 Test") { solvePart1(testData) }
  measure("Part 1     ") { solvePart1(data) }
  measure("Part 2 Test") { solvePart2(testData) }
  measure("Part 2     ") { solvePart2(data) }
}
