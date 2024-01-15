package day13

import solve
import kotlin.math.min

fun parseFields(input: List<String>): List<List<String>> {
  val result = mutableListOf<List<String>>()
  var current = mutableListOf<String>()

  (input + "").forEach { line ->
    if (line.isBlank()) {
      result.add(current)
      current = mutableListOf()
    } else {
      current.add(line)
    }
  }

  return result.filter { it.isNotEmpty() }
}

fun findReflection(field: List<String>): Long {
  for (row in field.indices) {
    val left = field.slice(0..<row).reversed()
    val right = field.slice(row..<field.size)

    val min = min(left.size, right.size)
    if (min == 0) {
      continue
    }

    val trimmedLeft = left.slice(0..<min)
    val trimmedRight = right.slice(0..<min)

    if (trimmedLeft == trimmedRight) {
      return row.toLong()
    }
  }
  return 0
}

fun transpose(field: List<String>): List<String> {
  val result = mutableListOf<String>()
  for (i in field[0].indices) {
    var current = ""
    for (row in field.indices) {
      current += field[row][i]
    }
    result.add(current)
  }
  return result
}

fun findReflectionValue(field: List<String>): Long {
  val verticalReflection = findReflection(field)
  if (verticalReflection > 0) {
    return verticalReflection * 100
  }

  val horizontalReflection = findReflection(transpose(field))
  return horizontalReflection
}

fun fieldsWithPossibleSmudgeRemoved(field: List<String>): List<List<String>> {
  val result = mutableListOf<List<String>>()
  field.forEachIndexed { idr, row ->
    row.forEachIndexed { idc, char ->
      if (char == '#') {
        result.add(field.mapIndexed { i, row ->
          if (i == idr) {
            row.replaceRange(idc, idc + 1, ".")
          } else {
            row
          }
        })
      }
    }
  }
  return result
}

fun solvePart1(input: List<String>): Long {
  return parseFields(input)
    .sumOf { findReflectionValue(it) }
}

fun solvePart2(input: List<String>): Long {
  val fields = parseFields(input)
  val baseValues: Map<List<String>, Long> = fields.associateWith { findReflectionValue(it) }

  val reflectionValues =  parseFields(input)
    .map { it to fieldsWithPossibleSmudgeRemoved(it) }
    .map { (field, fieldsWithoutSmudges) -> field to fieldsWithoutSmudges.map(::findReflectionValue) }
    .map { (field, reflectionValues) -> reflectionValues.firstOrNull { k -> k > 0 && k != baseValues[field] } ?: baseValues[field] ?: 0L }

  return reflectionValues.sum()
}

fun main() = solve("13", ::solvePart1, ::solvePart2)
