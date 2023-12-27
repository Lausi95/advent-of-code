package day08

import solve
import java.math.BigInteger

val nodeRegex = """(\w{3}) = \((\w{3}), (\w{3})\)""".toRegex()

class Node(val position: String, left: String, right: String) {
  val directionMap = hashMapOf(
    'L' to left,
    'R' to right,
  )

  companion object {
    fun parse(input: String): Node {
      val (pos, left, right) = nodeRegex.matchEntire(input)?.destructured ?: error("Malformed input")
      return Node(pos, left, right)
    }
  }
}

fun solvePart1(input: List<String>): Long {
  var steps = 0L
  val instructions = input[0]
  val nodes = input.filter { it.contains("=") }.map { Node.parse(it) }.associateBy { it.position }
  var currentNode = nodes["AAA"] ?: error("No starting node")
  while (true) {
    for (c in instructions) {
      currentNode = nodes[currentNode.directionMap[c]] ?: error("Cannot find next node")
      steps++

      if (currentNode.position == "ZZZ") {
        return steps
      }
    }
  }
}

fun solvePart2(input: List<String>): BigInteger {
  val instructions = input[0]
  val nodes = input.filter { it.contains("=") }.map { Node.parse(it) }.associateBy { it.position }
  val startingNodes = nodes.values.filter { it.position.endsWith("A") }
  val loopLengths = startingNodes.map { loopLength(it, nodes, instructions) }

  return loopLengths.lowestCommonMultiple()
}

fun loopLength(node: Node, nodes: Map<String, Node>, instructions: String): Long {
  var steps = 0L
  var currentNode = node
  while (true) {
    for (i in instructions) {
      currentNode = nodes[currentNode.directionMap[i]]!!
      steps++

      if (currentNode.position.endsWith("Z")) {
        return steps
      }
    }
  }
}

fun List<Long>.lowestCommonMultiple(): BigInteger = map { BigInteger.valueOf(it) }.reduce { acc, i -> acc * i / acc.gcd(i) }

fun main() = solve("08", ::solvePart1, ::solvePart2)
