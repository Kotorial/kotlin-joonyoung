package chapter5

fun main() {
    // 불변 컬렉션
    val intList = listOf(1, 2, 3, 4, 5)
    val intSet = setOf(1, 2, 3, 4, 1, 2, 3, 2, 4)
    val map = mapOf(1 to "zayson", 2 to "maeng")

    // 가변 컬렉션
    val mutableIntList = mutableListOf(1, 2, 3, 4, 5)
    val mutableIntSet = mutableSetOf(1, 2, 3, 1, 2, 3, 1, 2, 3)
    val mutableMap = mutableMapOf(1 to "zayson", 2 to "maeng")

    mutableIntList.add(6)
    mutableIntSet.add(10)
    mutableMap[3] = "young" // 배열 원소 넣는 것처럼 넣음
}