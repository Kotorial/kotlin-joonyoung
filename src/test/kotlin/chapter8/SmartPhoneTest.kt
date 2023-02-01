package chapter8

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class SmartPhoneTest {
    private val smartPhone = SmartPhone()

    @Test
    @DisplayName("by를 이용한 클래스 메서드 호출 위임")
    fun `delegate class with using by`() {
        val number = "1234"
        assertEquals("Dialing 1234", smartPhone.dial(number))
    }
}