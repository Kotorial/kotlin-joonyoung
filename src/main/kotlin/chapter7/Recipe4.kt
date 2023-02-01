package chapter7

fun main() {
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    val resultList = numbers.map { it.length }.filter { it > 3 } // resultList라는 변수를 생성
    println(resultList)

    // 리팩토링 이후
    // 별도의 변수 할당 없이 로직 처리
    numbers.map { it.length }.filter { it > 3 }.let { println(it) }
}