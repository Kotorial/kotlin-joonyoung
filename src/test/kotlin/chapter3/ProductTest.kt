package chapter3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class ProductTest {

    @Test
    @DisplayName("data class의 equals 메서드의 자동 구현 확인")
    fun `data class equals`() {
        val figure = Product("figure", 10000.0)
        val figure2 = Product("figure", 10000.0)

        // equals역할을 하는 ==, equals 메서드가 구현되어 있기 때문에 두 객체의 동등성 확인 가능
        assertEquals(figure, figure2)
        assertEquals(figure.hashCode(), figure2.hashCode())
    }

    @Test
    @DisplayName("data class의 equals, hashCode 자동 구현 -> Set에 해당 객체 넣을 때 사이즈 한개")
    fun `create set to check equals and hashCode`() {
        val figure1 = Product("figure", 1000.0)
        val figure2 = Product("figure", 1000.0)

        // Set에 넣어 확인
        val set = setOf(figure1, figure2)

        assertEquals(set.size, 1)
    }

    @Test
    @DisplayName("가격만 바꾸는 copy 메서드")
    fun `change product price using copy`() {
        val figure = Product("maplestory", 1000.0)
        val copyFigure = figure.copy(price = 12000.0)

        // 복사된 product의 이름은 동일
        assertEquals(figure.name, copyFigure.name)
        assertFalse(figure.price == copyFigure.price)
    }

    @Test
    @DisplayName("copy 메서드는 얕은 복사를 수행")
    fun `data class copy function is shallow`() {
        val orderItem = OrderItem(Product("figure", 10000.0), 5)
        val copyOrderItem = orderItem.copy()

        // 복사된 객체와 원본 객체의 Product는 공유되는 객체
        assertTrue(orderItem.product === copyOrderItem.product)
        assertTrue(orderItem.product == copyOrderItem.product)

        // 원본 객체의 가격을 변경했으나, 복사된 객체도 가격이 변경됨 확인
        orderItem.product.changePrice(15000.0)
        assertTrue(orderItem.product.price == copyOrderItem.product.price)
    }

    @Test
    @DisplayName("copy 메서드를 깊은 복사로 사용")
    fun `data class copy function for deep copy`() {
        val orderItem = OrderItem(Product("figure", 10000.0), 5)
        val copyOrderItem = orderItem.copy(orderItem.product.copy())

        // 원본 객체의 가격을 변경해도 복사된 객체의 가격이 변하지 않음
        orderItem.product.changePrice(15000.0)
        assertNotEquals(orderItem.product.price, copyOrderItem.product.price)
        assertEquals(orderItem.product.price, 15000.0)
        assertEquals(copyOrderItem.product.price, 10000.0)
    }

    @Test
    @DisplayName("데이터 클래스의 구조 분해")
    fun `destructure using component function`() {
        val product = Product("figure", 15000.0)
        val (name, price, onSale) = product // 구조 분해

        assertEquals(name, product.name)
        assertEquals(price, product.price)
        assertEquals(onSale, product.onSale)
    }
}