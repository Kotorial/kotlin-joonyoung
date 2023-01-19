package chapter5

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.LinkedList
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionTest {
    @Test
    @DisplayName("Recipe 5.2 기본 컬렉션 다루기 테스트")
    fun `basic collection function test`() {
        val intSet = setOf(1, 2, 3, 1, 2, 3)
        assertThat(intSet.size).isEqualTo(3)

        // linkedList
        val list = LinkedList<Int>()
        list.add(3)

        val addList = listOf(100,200,300)
        list.addAll(addList)

        // 리스트
        list[0] = 10
        assertThat(list.size).isEqualTo(4)
        assertEquals(list[0], 10)
    }
}