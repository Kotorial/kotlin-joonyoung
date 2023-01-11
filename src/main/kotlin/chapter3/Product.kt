package chapter3

data class Product(
    val name: String,
    var price: Double,
    var onSale: Boolean = false,
) {
    // 얕은 복사 수행 확인을 위한 가격 변경 메서드
    fun changePrice(newPrice: Double) {
        this.price = newPrice
    }
}
