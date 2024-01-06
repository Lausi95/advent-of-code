package day11

import solve
import kotlin.math.abs
import kotlin.math.max

private data class Galaxy(var x: Long, var y: Long) {

    fun distanceTo(galaxy2: Galaxy): Long =
        abs(x - galaxy2.x) + abs(y - galaxy2.y)
}

private typealias Galaxies = List<Galaxy>

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

private fun Galaxies.expandSpace(d: Long = 1): Galaxies {
    val maxX = maxOf { it.x }
    val maxY = maxOf { it.y }

    val range = 0..<max(maxX, maxY)

    for (xy in range.reversed()) {
        if (xy < maxX && none { it.x == xy }) {
            filter { it.x > xy }.forEach { it.x += d }
        }
        if (xy < maxY && none { it.y == xy }) {
            filter { it.y > xy }.forEach { it.y += d }
        }
    }

    return this
}

private fun Galaxies.sumDistances(): Long =
    sumOf { g1 -> sumOf { g2 -> g1.distanceTo(g2) } } / 2

private fun solvePart1(input: List<String>) = parseGalaxies(input)
    .expandSpace()
    .sumDistances()

private fun solvePart2(input: List<String>) = parseGalaxies(input)
    .expandSpace(1_000_000 - 1)
    .sumDistances()

private fun main() = solve("11", ::solvePart1, ::solvePart2)
