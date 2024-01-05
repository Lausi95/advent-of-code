package day06

import Inputs
import getAllLongs
import measure

fun solvePart1(input: List<String>): Long {
  val times = getAllLongs(input[0])
  val distances = getAllLongs(input[1])
  var result = 1L

  for (i in times.indices) {
    val time = times[i]
    val distance = distances[i]

    result *= waysToWin(time, distance)
  }

  return result
}

fun solvePart2(input: List<String>): Int {
  val time = getAllLongs(input[0]).joinToString("") { it.toString() }.toLong()
  val distance = getAllLongs(input[1]).joinToString("") { it.toString() }.toLong()
  return waysToWin(time, distance)
}

private fun waysToWin(time: Long, distance: Long): Int {
  var count = 0
  for (c in 1..<time) {
    if (-c * c + c * time > distance) {
      count += 1
    }
  }
  return count
}

fun main() {
  val input = Inputs.load("06")

  measure("Part 1 Test") { solvePart1(input.testInput) }
  measure("Part 1     ") { solvePart1(input.actualInput) }
  measure("Part 2 Test") { solvePart2(input.testInput) }
  measure("Part 2     ") { solvePart2(input.actualInput) }
}
