package chapter11

fun main() {
    // Int -> String타입으로 변환해 Binary를 표현
    val str = 42.toString(radix = 2)
    println(str)

    // String -> Int 타입으로 변환해 10진수로 변경
    val num = "101010".toInt(radix = 2)
    println(num)

    // padStart
    val four = 4.toString(2).padStart(4,'0')
    println(four)
}

