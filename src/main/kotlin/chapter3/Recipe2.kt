package chapter3

class Person(val name: String) {
    // 생성자 프로퍼티가 아닌 최상위 멤버로 변수를 선언한 경우
    var age: Int = 29

    // val 변수의 Custom Getter
    val isAdult: Boolean // 타입 생략 가능
        get() = this.age >= 20 // Boolean 타입으로 타입 추론 가능

    // var 변수의 경우 Custom Setter 가능
    var height: Int = 180
        get() = field + 5 // getter 지정도 가능은 함
        set(value) {    // Custom Setter 지정
            field += value
        }

    fun getNextYearAge() = this.age + 1
}

fun main() {
    // 인스턴스화 시점에 age에 값을 할당 블기
    val person = Person("zayson")
    println("person.age = ${person.age}")

    // 인스턴스화 시점에 값을 할당하기 위해 apply를 사용
    val newPerson = Person("zayson").apply { age = 25 }
    println("newPerson.age = ${newPerson.age}")

    println("newPerson.isAdult = ${newPerson.isAdult}")
    println("newPerson.height = ${newPerson.height}")

    newPerson.height = 100
    println("newPerson.height = ${newPerson.height}")
}