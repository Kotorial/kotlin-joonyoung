## Recipe 3.1 const와 val의 차이 이해하기
> 💡 런타임 보다는 컴파일 타임에 변수가 상수임을 나타내고 싶다.
>
> 컴파일 타임 상수에 const를 사용, val의 경우 런타임에 할당이 일어남

- 컴파일타임 상수 (`const`)
    - **객체나 동반객체 선언의 최상위 속성 또는 멤버**
    - 문자열 또는 기본 타입의 래퍼 클래스를 타입으로 가짐
    - Custom Getter를 가질 수 없음
    - **컴파일 시점에 값을 사용할 수 있도록 모든 함수 바깥에서 할당 필요**
```kotlin
// 클래스 및 모든 함수 바깥 최상위 멤버
const val DEFAULT_PRIORITY = 3

class Task(
    val name: String,
    _priority: Int = DEFAULT_PRIORITY,  // 클래스 내에서 접근 가능
) {
    companion object {
        // 클래스 내에서 동반객체 선언 최상위
        const val MAX_PRIORITY = 5
        private const val MIN_PRIORITY = 1 // 클래스 내부에서만 사용하고자 하는 경우 private으로 막을 수 있음
    }
}
```
- `val` 은 키워드, `const` 는 변경자 (ex. private, inline 등) → 함께 쓰임
- `const val 변수` 를 자바 코드로 대치하면 `static final 변수`형식

<hr> 

## Recipe 3.2 사용자 정의 획득자와 설정자 생성하기
> ✅ **사용자 정의 획득자, 설정자 번역을 일반적으로 사용하는 Custom Getter/Setter로 통일**
>

> 💡 **값을 할당하거나 리턴하는 방법을 사용자 정의하고 싶다.**
>
> **코틀린 클래스 속성(프로퍼티)에 get과 set 함수를 추가 → Custom Getter/Setter**

- 생성자 프로퍼티가 아닌 최상위 멤버로 변수를 선언 → **클래스 인스턴스화 시점에 값을 할당하고자 하는 경우 apply를 사용**

    ```kotlin
    class Person(val name: String) {
        var age = 29
    }
    
    // 인스턴스화 시점에 값을 할당하기 위해 apply 사용
    Person("zayson").apply { age = 25 } 
    println("person.age = ${person.age}")  // Output : 25
    ```

- **최상위 멤버 변수 선언 시 Custom Getter/Setter를 이용한 프로퍼티 정의 가능**
    - 타입 추론 가능한 경우 타입 생략 가능
    - 아래 예시에서 []부분은 생략 가능

    ```kotlin
    var 프로퍼티명[: 타입] [= 초기값]
    	[getter]
    	[setter]
    
    var height: Int = 180
            get() = field + 5 // getter 지정도 가능은 함
            set(value) {    // Custom Setter 지정
                field += value 
            }
    ```

- **Backing Field (지원필드) : `field` 를 이용해 프로퍼티 참조**
    - 코틀린의 프로퍼티는 기본적으로 getter/setter 생성

  > **Custom Getter에서 field가 아닌 프로퍼티로 받을 때의 문제점**
  >
  >  1. 프로퍼티의 값을 얻기 위해 정의한 getter를 호출
  >  2. 호출된 getter에 프로퍼티 자체가 정의되어 있어 반복적으로 프로퍼티 getter 호출
  >  3. StackOverflowError 발생
  >
  > ✅ **Custom Getter/Setter에서 프로퍼티 참조 시 `field` 키워드를 이용해 접근하자!**
  >
  >
  > ```kotlin
  >   var height: Int = 180
  >   	get() = height + 5 // StacOverflowError 발생
  >   	get() = field + 5 // 올바르게 동작
  >  ```

<hr>

## Recipe 3.3 데이터 클래스 정의하기

> 💡 **`equals`, `hashCode`, `toString` 등이 완벽하게 갖춰진 엔티티를 나타내는 클래스를 생성하고 싶다.**
> 
> **클래스 정의 시 data 키워드를 이용해 data class를 정의**

- data class는 데이터를 담기 위한 목적으로 사용
- data class 정의 시 자동으로 `equals`, `hashCode`, `toString`, `copy`, `componentN` 메서드 제공
- equals, hashCode가 구현되어 있어 동일한 값을 갖는 객체는 동등함

    ```kotlin
    val figure = Product("figure", 10000.0)
    val figure2 = Product("figure", 10000.0)
    
    assertEquals(figure, figure2)  // true
    assertEquals(figure.hashCode(), figure2.hashCode()) // true
    
    // Set에 넣어 확인
    val set = setOf(figure1, figure2)
    assertEquals(set.size, 1)  // true
    ```

- `copy` : 파라미터로 제공된 속성 값만 변경해 새로운 객체를 생성하는 인스턴스 메서드
    - **`copy` 는 얕은 복사 수행 → 복사된 객체의 값을 변경하면 원본 객체도 함께 변경**

    ```kotlin
    // 얕은 복사 수행 확인을 위한 가격 변경 메서드 (Product Data Class)
    fun changePrice(newPrice: Double) {
        this.price = newPrice
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
    ```

  > **copy()를 깊은 복사롤 사용하고 싶다면?**
  >
  > **copy()를 통해 가져오는 래퍼런스 타입이 있다면 해당 래퍼런스도 copy를 통해 가져옴**
  >
  >
  > ```kotlin
  > @Test
  > @DisplayName("copy 메서드를 깊은 복사로 사용")
  > fun `data class copy function for deep copy`() {
  >     val orderItem = OrderItem(Product("figure", 10000.0), 5)
  >     val copyOrderItem = orderItem.copy(orderItem.product.copy())  // Prodcut 인스턴스도 copy를 통한 복사
  > 
  >     // 원본 객체의 가격을 변경해도 복사된 객체의 가격이 변하지 않음
  >     orderItem.product.changePrice(15000.0)
  >     assertNotEquals(orderItem.product.price, copyOrderItem.product.price)
  >     assertEquals(orderItem.product.price, 15000.0)
  >     assertEquals(copyOrderItem.product.price, 10000.0)
  > } 

- 구조분해 : 복합적인 값을 `componentN` 메서드를 통해 내부적으로 분해해서 값을 초기화

    ```kotlin
    product.component1() // name
    product.component2() // price
    val (name, price, onSale) = product // 구조 분해
    ```

    ![image](https://user-images.githubusercontent.com/52314663/211732588-afb2a1c6-08df-4315-8705-19662e559bd3.png)

- **data class를 통해 자동으로 정의되는 메서드들은 모두 커스텀하게 오버라이딩 가능**
- 코틀린은 2개, 3개의 타입을 담아주는 `Pair`, `Tripple` 클래스 지원
  