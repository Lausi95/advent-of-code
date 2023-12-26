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

val numberPattern: Pattern = Pattern.compile("\\d+")

fun getAllNumbers(line: String): List<Long> {
  val numbers = mutableListOf<Long>()
  val numberMatcher = numberPattern.matcher(line)
  while (numberMatcher.find()) {
    numbers.add(numberMatcher.group().toLong())
  }
  return numbers
}
