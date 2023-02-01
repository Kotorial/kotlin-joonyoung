package chapter8

// 동일한 기능을 정의한 인터페이스
interface DelegatorPhone {
    fun dial(number: String)
    fun takePicture()
}

// 각각의 구현체
class DelegatorIphone : DelegatorPhone {
    override fun dial(number: String) = println("Dialing Iphone $number")
    override fun takePicture() = println("Taking Picture By Iphone")
}

class DelegatorGalaxy : DelegatorPhone {
    override fun dial(number: String) = println("Dialing Galaxy $number")
    override fun takePicture() = println("Taking Picture By Galaxy")
}

class DelegatorSimplePhone : DelegatorPhone {
    override fun dial(number: String) = println("Dialing $number")
    override fun takePicture() = println("Taking Picture")
}

//// 위임 패턴 적용 (구현체를 생성자로 받아서 구현체 메서드들은 위임받아 처리, 새로운 기능 호출 가능)
//class DelegatorPattern(private val phone: DelegatorPhone) : DelegatorPhone {
//    //  기존 함수들을 호출
//    override fun dial(number: String) = phone.dial(number)
//
//    // 기존 함수들을 호출
//    override fun takePicture() = phone.takePicture()
//
//    // 새로운 기능 정의 가능
//    fun clickButton() = println("Click Button")
//}

class DelegatorBy(private val phone: DelegatorPhone) : DelegatorPhone by phone {
    // dial, takePciture와 같은 기존 인터페이스를 구현한 메서드를 다시 오버라이딩 하지 않아도 됨

    // 새로운 기능 추가도 가능
    fun clickButton() = println("Click Button")
}

fun main() {
//    val iPhone = DelegatorPattern(DelegatorIphone())
//    iPhone.dial("123")
//    iPhone.clickButton()

    val iphone2 = DelegatorBy(DelegatorIphone())
    iphone2.dial("123")
    iphone2.takePicture()
}