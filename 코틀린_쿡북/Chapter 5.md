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