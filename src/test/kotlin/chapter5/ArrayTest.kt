package chapter5

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertContains

class ArrayTest {
    @Test
    @DisplayName("기본 배열 선언 테스트")
    fun `기본 배열 선언 테스트`() {
        val stringArray = arrayOf("a", "b", "c", "d")
        val nullArray = arrayOfNulls<String>(5)
        val emptyArray = emptyArray<String>()
        val array = Array(5) { it * 2 }

        assertEquals(stringArray.size, 4)
        assertContains(nullArray, null)
        assertEquals(array[0], 0)
        assertEquals(array[2], 4)
        assertEquals(array[4], 8)
    }

    @Test
    @DisplayName("Array 클래스의 확장 함수 : indices")
    fun `indices function test`() {
        val strings = arrayOf("my", "name", "is", "zayson")
        val indices = strings.indices

        assertThat(indices.first).isEqualTo(0)
        assertThat(indices.last).isEqualTo(3)
    }

    @Test
    @DisplayName("Array 클래스의 확장 함수 : withIndex")
    fun `withIndex function test`() {
        val strings = arrayOf("my", "name", "is", "zayson")
        for ((index, value) in strings.withIndex()) {
            println("${index} 's string is ${value}")
            assertTrue(index in 0..5)
        }
    }
}