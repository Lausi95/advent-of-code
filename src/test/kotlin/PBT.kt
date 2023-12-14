package aoc

import java.util.*
import kotlin.math.abs

var RANDOM: Random? = null

fun <T> withRandom(exec: (Random) -> T): T {
  if (RANDOM == null) {
    val seed = Random().nextLong()
    RANDOM = Random(seed)
    print("RANDOM SEED: $seed")
  }
  return exec(RANDOM!!)
}

fun someInt(from: Int = 0, to: Int = 100): Int = withRandom { it.nextInt(from, to) }
