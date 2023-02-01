package chapter8

// 핸드폰 기능 정의
interface OriginalPhone {
    fun dial(number: String)
    fun takePicture()
}

// 간단하게 수행되는 핸드폰 기능 (OriginalPhone 구현)
class SimplePhone : OriginalPhone {
    override fun dial(number: String) = println("Dialing $number")

    override fun takePicture() = println("Taking Picture")
}

// OriginalPhone을 구현한 추상 클래스 (데코레이터)
abstract class DecoratorPhone(private val originalPhone: OriginalPhone) : OriginalPhone {
    override fun dial(number: String) = originalPhone.dial(number)
    override fun takePicture() = originalPhone.takePicture()
}

// 각각의 구체 클래스
class IPhone(private val originalPhone: OriginalPhone) : DecoratorPhone(originalPhone) {
    override fun dial(number: String) {
        println("Dialing Iphone")
        super.dial(number)
    }

    override fun takePicture() {
        println("Take Pciture By Iphone")
        super.takePicture()
    }

    // 새로운 기능 추가 가능
}

// 각각의 구현체에서 메서드들을 모두 오버라이딩해야하는 문제 발생
class Galaxy(private val originalPhone: OriginalPhone) : DecoratorPhone(originalPhone) {
    override fun dial(number: String): Unit {
        println("Dialing Galaxy")
        super.dial(number)
    }

    override fun takePicture() {
        println("Take Picture By Galaxy")
        super.takePicture()
    }

    // 새로운 기능 추가 가능
}

fun main() {
    val galaxyPhone = Galaxy(SimplePhone())
    galaxyPhone.dial("123")

    println("===")
    val iPhone = IPhone(SimplePhone())
    iPhone.dial("123")

    println("===")
    // 기능들을 상속 없이 컴포지션을 통해 사용 가능
    val combinationPhone = IPhone(Galaxy(SimplePhone()))
    combinationPhone.dial("123")
}