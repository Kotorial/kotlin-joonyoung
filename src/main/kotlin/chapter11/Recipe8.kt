package chapter11

import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextLong

fun main() {
    (0..5).forEach { n ->
        val sleepTime = Random.nextLong(range = 0..1000L)

        // 스레드 함수에 코드 블럭을 넘겨서 스레드 사용
        thread {
            Thread.sleep(sleepTime)
            println("${Thread.currentThread().name} for $n after ${sleepTime}ms")
        }
    }

    (0..5).forEach { n ->
        val sleepTime = Random.nextLong(range = 0..1000L)

        // 스레드 함수에 파라미터를 주고 블럭을 넘길 수 있다.
        thread (isDaemon = true) {
            Thread.sleep(sleepTime)
            println("${Thread.currentThread().name} for $n after ${sleepTime}ms")
        }
    }
}