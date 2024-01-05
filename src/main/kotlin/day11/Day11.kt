package day11

import solve
import kotlin.math.abs

data class Galaxy(var x: Long, var y: Long) {
  fun distance(galaxy2: Galaxy): Long =
    abs(x - galaxy2.x) + abs(y - galaxy2.y)
}

typealias Galaxies = List<Galaxy>

private fun parseGalaxies(input: List<String>): Galaxies {
  val galaxies = mutableListOf<Galaxy>()
  input.forEachIndexed { y, line ->
    line.forEachIndexed { x, ch ->
      if (ch == '#') {
        galaxies.add(Galaxy(x.toLong(), y.toLong()))
      }
    }
  }
  return galaxies
}

private fun Galaxies.expandSpace(d: Long = 1) {
  for (x in (0..<maxOf { it.x }).reversed()) {
    if (none { it.x == x }) {
      filter { it.x > x }.forEach { it.x += d }
    }
  }

  for (y in (0..<maxOf { it.y }).reversed()) {
    if (none { it.y == y }) {
      filter { it.y > y }.forEach { it.y += d }
    }
  }
}

private fun Galaxies.sumDistances(): Long {
  return mapIndexed { i, g1 ->
    mapIndexed { j, g2 ->
      if (i < j) g1.distance(g2)
      else 0
    }.sum()
  }.sum()
}

private fun solvePart1(input: List<String>): Long {
  val galaxies = parseGalaxies(input)
  galaxies.expandSpace()
  return galaxies.sumDistances()
}

private fun solvePart2(input: List<String>): Long {
  val galaxies = parseGalaxies(input)
  galaxies.expandSpace(1_000_000L - 1)
  return galaxies.sumDistances()
}

private fun main() = solve("11", ::solvePart1, ::solvePart2)
