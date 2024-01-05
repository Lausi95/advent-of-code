package day09

import getAllLongs
import solve

fun solvePart1(input: List<String>): Long {
  return input.map { getAllLongs(it) }.sumOf { extrapolateToFuture(it) }
}

fun extrapolateToFuture(sequence: List<Long>): Long {
  return if (sequence.all { it == 0L }) 0
  else sequence.last() + extrapolateToFuture(sequence.zipWithNext(::subtract))
}

fun solvePart2(input: List<String>): Long {
  return input.map { getAllLongs(it) }.sumOf { extrapolateToPast(it) }
}

fun extrapolateToPast(sequence: List<Long>): Long {
  return if (sequence.all { it == 0L }) 0
  else sequence.first() - extrapolateToPast(sequence.zipWithNext(::subtract))
}

private fun subtract(e1: Long, e2: Long) = e2 - e1

fun main() = solve("09", ::solvePart1, ::solvePart2)
