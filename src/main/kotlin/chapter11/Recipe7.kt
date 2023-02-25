package chapter11

import java.util.stream.IntStream
import kotlin.system.measureTimeMillis

fun doubleIt(x: Int): Int {
    Thread.sleep(100L)
    println("doubling $x with on thread ${Thread.currentThread().name}")
    return x * 2
}

fun main() {
    println("${Runtime.getRuntime().availableProcessors()} processors")
    var time = measureTimeMillis {
        IntStream.rangeClosed(1,6)
            .map{ doubleIt(it) }
            .sum()
    }

    println("Sequential steam took ${time}ms")

    time = measureTimeMillis {
        IntStream.rangeClosed(1, 6)
            .parallel()
            .map{ doubleIt(it) }
            .sum()
    }

    println("Parallel stream took ${time}ms")
}