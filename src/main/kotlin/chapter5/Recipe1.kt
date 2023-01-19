package chapter5

fun main() {
    val stringArray = arrayOf("1", "2", "3")    // 타입 추론을 통해 String 배열임을 파악 가능
    val arrayOfNulls = arrayOfNulls<String>(5)  // 영원히 null이 아닐 것이라 판단해 null 배열의 경우 타입 지정 필요
    val array = Array(5) { (it * 2).toString() }


}