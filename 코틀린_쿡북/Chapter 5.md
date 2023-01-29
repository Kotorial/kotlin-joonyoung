## Recipe 5.11 타입으로 컬렉션을 필터링하기

> 
> 🔥 **여러 타입이 섞여 있는 컬렉션에서 다른 특정 타입의 원소로 구성된 새 컬렉션을 생성하고 싶다.**
>
> **`filterIsInstance`, `filterIsInstanceTo` 확장 함수를 사용**


- 코틀린 컬렉션은 컬렉션의 필터링을 위해 `Predicate`를 인자로 받는 `filter` 확장함수 포함
    - `filter` 확장함수의 경우 필터링 이후 들어온 타입 그대로 반환하는 것을 확인 가능

        ![image](https://user-images.githubusercontent.com/52314663/215254995-044d1180-caf9-49fe-b377-354a4d6af84a.png)

        ```kotlin
        val list = listOf("zayson", 10, LocalDate.now())
        val strings = list.filter {it is String} // List<Any>로 그래도 리턴
        
        // strings의 타입은 String원소만 필터링 했음에도 타입은 List<Any> 
        ```

- `filterIsInstance` 함수는 **필터링 시 구체적인 타입을 명시** → 컴파일러가 구체적인 타입 추론 가능
    - `inline` 함수에 적용하는 `reified` 키워드를 이용해 런타임 시에도 타입을 보존
    - `filterIsInstance` 함수는 내부에서 `filterIsInstanceTo` 함수를 호출

      ![image](https://user-images.githubusercontent.com/52314663/215254987-96ed9623-454a-4cf9-abb6-6e33a3cf5650.png)

        ```kotlin
        val list2 = listOf("zayson", 10, LocalDate.now())
        val strings2 = list.filterIsInstance<String>() // String 타입으로 필터링한 리스트
        ```

- `filterIsInstanceTo` 함수는 **구체적인 가변 컬렉션 타입을 명시**
    - 가변 컬렉션 타입을 명시해야만 새로운 타입의 컬렉션으로 데이터 원소를 넣는 것이 가능
    - 파라미터로 들어오는 destination의 타입인 C가 `MutableCollection<in R>`
      ![image](https://user-images.githubusercontent.com/52314663/215255050-7b33c8f5-4da8-48d3-a2c2-c6aad36e4b0b.png)

        ```kotlin
        val listAdler32 = listOf("zayson", 10, LocalDate.now())
        val strings3 = list.filterIsInstanceTo(mutableListOf<String>())
        val strings3 = list.filterIsInstanceTo(listOf<String>()) // 컴파일 에러
        ```


### ❓ reified 키워드

- **제네릭으로서 `inline` 함수에 함께 사용되고, 런타임 시에 타입정보를 알고 싶을 때 사용하는 키워드**
- **제네릭은 일반적으로 런타임 시에 타입이 소거**되기 때문에 타입 정보를 알 수 없음
- `reified`를 사용하지 않는 경우 `KClass<T>`와 같이 클래스 리터럴을 넘겨줘야 런타임 시에 타입 판단 가능

    ```kotlin
    // KClass<T>를 파라미터로 넘겨 런타임 시에 타입 체크후 출력
    inline fun <T : Any> checkType(data: T, type: KClass<T>) {
    //  when(T::class) // 컴파일 에러
        when(type) {
            String::class -> println("${data} is string")
            else -> println("${data} is not string")
        }
    }
    
    // reified 키워드를 이용해 데이터 타입을 런타임시에도 보존
    inline fun <reified T> checkType(data: T) {
        when (T::class) {
            String::class -> println("${data} is string")
            else -> println("${data} is not string")
        }
    }
    ```
---  

## Recipe 5.12 범위를 수열로 만들기
> **🔥 범위를 순회하고 싶지만 간단한 정수 또는 문자로 구성되지 않았다.**
> 
> **사용자 정의 수열을 생성한다.**

- 표준 라이브러리에는 `Comparable` 인터페이스를 구현하는 모든 제너릭 타입 T에 `rangeTo`라는 이름의 확장함수 제공
- `ComparableRange` 클래스의 `rangeTo` 메서드 구현 방법
    - `Comparable` 인터페이스 → `ClosedRange` 인터페이스 → `ComparableRange` 클래스
    - `ClosedRange`의 `start`, `endInclusive` 프로퍼티 정의
    - `equlas`, `hashCode`, `toString` 재정의
    - `rangeTo`의 리턴타입은 `ClosedRange`

  ![image](https://user-images.githubusercontent.com/52314663/215298853-78efbf0b-11f9-4ea0-adff-b23ca7d3ab06.png)
### 사용자 정의 수열

- **범위가 생성되더라도 수열(순서 있는 값의 연속)이 아니라면 순차적인 순회가 불가능**

    ![image](https://user-images.githubusercontent.com/52314663/215298856-0d32ca7e-a33f-4e32-9f5e-7c04ce727a98.png)
- **순차적인 순회를 위해서 사용자 정의 수열 생성 필요**
1. **`Iterable` 인터페이스의 `iterator` 함수 오버라이딩**
    - **사용자 정의 Iterator 클래스**를 하나 생성한 후 `next`, `hasNext` 함수 오버라이딩
    - 생성한 iterator 클래스를 **사용자 정의 수열 클래스 내 오버라이딩한 iterator 함수에 지정**

    ```kotlin
    internal class LocalDateProgressionIterator(
        start: LocalDate,
        val endInclusive: LocalDate,
        val step: Long
    ) : Iterator<LocalDate> {
        private var current = start
        
        // 범위 체크
        override fun hasNext(): Boolean = current <= endInclusive
    
        // 순회를 위한 next
        override fun next(): LocalDate {
            val next = current
            current = current.plusDays(step)
            return next
        }
    }
    
    // LocalDateProgression (사용자 정의 수열 클래스 내 정의)
    override fun iterator(): Iterator<LocalDate> = LocalDateProgressionIterator(start, endInclusive, step)
    ```

2. **ClosedRange 상속을 통해 start, endInclusive 프로퍼티 재정의**

    ```kotlin
    class LocalDateProgression (
        // ClosedRange 프로퍼티 재정의 (start, endInclusive)
        override val start: LocalDate,
        override val endInclusive: LocalDate,
        val step: Long = 1    // (opt) step 정의
    ) : Iterable<LocalDate>, ClosedRange<LocalDate> {
    		
        // Iterable 인터페이스의 오버라이딩 메서드는 iterator 한개
        override fun iterator(): Iterator<LocalDate> = LocalDateProgressionIterator(start, endInclusive, step)
    
        // step 중위 함수 만들기
        infix fun step(days: Long) = LocalDateProgression(start, endInclusive, days)
    }
    ```

3. 순회 범위를 만들기 위해 `LocalDate` 클래스의 `rangeTo` 확장함수 생성

    ```kotlin
    // 확장 함수 생성
    operator fun LocalDate.rangeTo(other: LocalDate) = LocalDateProgression(this, other)
    ```

- 사용자 정의 수열을 생성하고 범위 연산을 진행하는 경우 컴파일 에러 발생 X
  ![image](https://user-images.githubusercontent.com/52314663/215298858-75caea6b-c92e-4b04-a61c-7e59131bedd5.png)
    
- **다른 클래스에도 해당 형태로 순회 범위 구현이 가능**