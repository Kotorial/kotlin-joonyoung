package chapter2

import java.lang.IllegalArgumentException

/**
 * 레시피 2.1 코틀린에서 널 허용 타입 사용하기
 */
fun main() {
    var notNullName: String
    notNullName = "zayson"
//    notNullName = null  // 컴파일 에러

    var nullableName: String?
    nullableName = "zayson"
    nullableName = null // 컴파일 에러 안남

    val person1 = Person("joonyoung", null, "maeng")
    var person2 = Person("joonyoung", null, "maeng")

    if (person1.middle != null) {
        // middle이 null아님을 체크 + val 변수 = 중간에 값 변경 X 예상 = 스마트 캐스트
        val middleLength = person1.middle.length  // String? -> String 타입으로 스마트 캐스트\
    }

    if (person2.middle != null) {
        // person2의 경우 var이기 때문에 중간에 값이 변경될 가능성 존재 = 스마트 캐스트 발생 X
        val middleLength = person2.middle!!.length  // 개발자가 null이 들어오지 않음을 확신 -> !!을 이용한 null 아님 단언
    }

    val middleLength = person2.middle?.length // safe-call을 통한 null 허용 타입에 대한 안전한 호출
    println(middleLength) // null

    val middleLength1 = person2.middle?.length ?: 0 // elvis 연산자 - 값이 null인 경우 후자의 식을 사용
    val middleLength2 = person2.middle?.length ?: throw IllegalArgumentException("엘비스 연산자는 식 = throw 가능")

    // 안전한 타입 캐스팅 (safe cast)
    val person3 = person1 as? Person
}

class Person(
    val first: String,
    val middle: String?,
    val last: String
)