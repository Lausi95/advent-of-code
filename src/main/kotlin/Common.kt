import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern
import kotlin.system.measureTimeMillis

data class Inputs(val testInput: List<String>, val actualInput: List<String>) {
  companion object {
    fun load(day: String): Inputs {
      val testData = Files.readAllLines(Path.of("./input/$day/input.test.txt"))
      val data = Files.readAllLines(Path.of("./input/$day/input.txt"))

      return Inputs(testData, data)
    }
  }
}

fun <T> measure(name: String, fn: () -> T): T {
  var solution: T
  val time = measureTimeMillis {
    solution = fn()
  }
  println("$name took ${time}ms: $solution")
  return solution
}

val numberPattern: Pattern = Pattern.compile("-?\\d+")

fun getAllLongs(line: String): List<Long> {
  val numbers = mutableListOf<Long>()
  val numberMatcher = numberPattern.matcher(line)
  while (numberMatcher.find()) {
    numbers.add(numberMatcher.group().toLong())
  }
  return numbers
}

fun getAllInts(line: String): List<Int> {
  val numbers = mutableListOf<Int>()
  val numberMatcher = numberPattern.matcher(line)
  while (numberMatcher.find()) {
    numbers.add(numberMatcher.group().toInt())
  }
  return numbers
}

fun <T1, T2> solve(day: String, part1: (List<String>) -> T1, part2: (List<String>) -> T2) {
  val inputs = Inputs.load(day)

  measure("Part 1, Test") { part1(inputs.testInput) }
  measure("Part 1      ") { part1(inputs.actualInput) }
  measure("Part 2, Test") { part2(inputs.testInput) }
  measure("Part 2      ") { part2(inputs.actualInput) }
}
