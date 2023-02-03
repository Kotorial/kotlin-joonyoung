## Recipe 7.3 let 함수와 엘비스 연산자 사용하기

> **🎈 널인 경우 기본값을 리턴하고, 아닌 경우 레퍼런스의 코드 블록을 실행하고 싶은 경우**
>
> **엘비스 연산자를 결합한 Safe-Call과 함께 `let` 영역 함수 사용**

- `let` 영역함수는 **코드 블록을 전달받고 해당 블록에 대한 결과를 리턴**하는 방식으로 동작
- Nullable할 수 있는 데이터를 처리할 때 `let`을 사용하는 경우 `Safe-Call`과 `엘비스 연산자 (?:)`를 통해 편리하게 `null`데이터를 다룰 수 있다.
  ```kotlin
    fun processNullableString(string: String?) =
    string?.let {   // 파라미터 input인 string이 null이 아닌 경우 실행 (safe-call)
        when {
            it.isEmpty() -> "Empty"
            it.isBlank() -> "Blank"
            else -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } // 첫글자 대문자 만들기
        }
    } ?: "Default String" // 파라미터 input인 string이 null인 경우 Default return 값 사용
  ```
- `null`을 리턴하는 많은 API에 대해 `Safe-Call`, `엘비스 연산자(?:)`, `let`을 통해 편리하게 다루는 것을 적용 가능

---
## Recipe 7.4 임시 변수로 let 사용하기
> **🎈 연산 결과를 임시 변수에 할당하지 않고 처리하고 싶다.**
> 
> **처리한 연산에 `let`호출을 연쇄하고, 파라미터로 넘긴 함수나 래퍼런스 내에서 결과 처리**

- 일반적으로 연산 처리 결과를 변수에 담아두고 담아놓은 변수를 이후 로직에서 사용
- `let`을 사용하면 **임의의 변수에 저장없이 바로 처리가 가능**
  ```kotlin
  // 기존 코드
  val numbers = mutableListOf("one", "two", "three", "four", "five")
  val resultList = numbers.map { it.length }.filter { it > 3 } // resultList라는 변수를 생성

  // let을 이용한 리팩토링
  numbers.map { it.length }.filter { it > 3 }.let { println(it) } // 메서드 참조 가능
  ```
  - 함수의 결과를 리턴받지 않고 위처럼 출력하는 경우 `also` 사용이 코틀린스러움!
- `let`은 연산의 결과를 함수나 레퍼런스로 처리하고 반환받을 때 사용하는 것이 의미전달이 잘된다고 생각
  <details>
    <summary>personList 테스트 데이터 생성</summary>
    
    ```kotlin
    private fun createPersons() = listOf(
        Person(
            "zayson", 29, listOf(
                Education("graduate", "Colleage", "A"),
                Education("graduate", "HighSchool", "B")
            )
        ),
        Person(
            "Maeng", 30, listOf(
                Education("graduate", "Colleage", "C"),
                Education("graduate", "HighSchool", "B")
            )
        ),
        Person(
            "Yun", 27, listOf(
                Education("graduate", "Colleage", "A"),
                Education("graduate", "HighSchool", "D")
            )
        )
    )  
  ```
  </details>
- ```kotlin
  personList.flatMap { it.educations }
        .filter { it.name == "A" && it.type == "Colleage" && it.status == "graduate" }
        .count()
        .let {
            // let을 통해 count의 결과를 함수 내에서 처리
            println("2023 졸업생 : ${it}")
            if (it > 2) "많은 졸업생"
            else "적당한 졸업생"
        }
        .also(::println)  // also를 이용해 리턴받지 않는 경우에 출력
  ```
---
## Recipe 8.1 대리자를 사용해서 합성 구현하기
> **클래스 대리자 : 클래스의 상속을 합성(컴포지션)으로 대체 가능**
>
> **속성 대리자 : 어떤 속성의 getter/setter를 다른 클래스에 있는 속성의 getter/setter로 대체**

> 🎈 **다른 클래스의 인스턴스가 포함된 클래스를 만들고 그 클래스에 연산을 위임하고 싶다.**
> 
> **1. 연산을 위임할 메서드가 포함된 인터페이스 생성**
> 
> **2. 해당 인터페이스를 클래스로 구현**
> 
> **3. `by`키워드를 이용해 바깥쪽에 래퍼 클래스를 만듬**
- 코틀린은 **상속에서 오는 취약점(상위 클래스에 새로운 기능 추가)** 을 해결하기 위해 **클래스를 기본적으로 `final`로 지정, `open`을 통해서 상속을 가능하게 만듬(상속 보다 합성 선호)**
- 상속을 하지 않는 클래스에서 **새로운 기능을 추가하는 경우 데코레이터 패턴을 이용**
### 데코레이터 패턴을 이용
<details>
<summary>데코레이터패턴 예제 코드</summary>

```kotlin
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
    override fun dial(number: String) {
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
    // 각각의 데코레이터를 조합
    val combinationPhone = IPhone(Galaxy(SimplePhone()))
    combinationPhone.dial("123")
}
```
</details>

- 새로운 기능이 필요한 경우 데코레이터를 상속해서 구현한 뒤 새로운 기능을 정의
- **각각의 데코레이터를 조합해서 기능의 확장이 가능**
- **데코레이터 패턴은 상속이 아닌 컴포지션을 지원하지만 준비코드가 많다!**

### 위임 패턴
- 코틀린은 클래스 위임을 위해 위임 패턴(Delegator Pattern)을 이용
    - 상위 인터페이스를 생성하고 해당 인터페이스의 구현체를 정의
    - **상위 인터페이스를 구현하는 위임 클래스를 생성**하고, **오버라이딩한 메서드들은 구현체의 메서드들을 호출하게한다.**
    - <details>
        <summary>위임 패턴 예제 코드</summary>
        
        ```kotlin
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
        
        // 위임 패턴 적용 (구현체를 생성자로 받아서 구현체 메서드들은 위임받아 처리, 새로운 기능 호출 가능)
        class DelegatorPattern(private val phone: DelegatorPhone) : DelegatorPhone {
            //  기존 함수들을 호출
            override fun dial(number: String) = phone.dial(number)
        
            // 기존 함수들을 호출
            override fun takePicture() = phone.takePicture()
        
            // 새로운 기능 정의 가능
            fun clickButton() = println("Click Button")
        }
        
        fun main() {
            val iPhone = DelegatorPattern(DelegatorIphone())
            iPhone.dial("123")
            iPhone.clickButton()
        }
        ```
  </details>
- 위임 패턴도 **위임 클래스에서 상위 인터페이스를 모두 오버라이딩해야 하는 문제 존재**
  - **인터페이스에 정의된 메서드가 많은 경우 보일러 플레이트 코드의 증가!**
- 코틀린은 이러한 문제를 `by`키워드를 통해 보일러 플레이트 코드의 감소를 이끔
  ```kotlin
  class DelegatorBy(private val phone: DelegatorPhone) : DelegatorPhone by phone {
    // dial, takePciture와 같은 기존 인터페이스를 구현한 메서드를 다시 오버라이딩 하지 않아도 됨

    // 새로운 기능 추가도 가능
    fun clickButton() = println("Click Button")
  }
  ```
- `by`키워드는 컴파일러가 위임 패턴 코드를 작성한다.
  ![image](https://user-images.githubusercontent.com/52314663/216518238-e66e5ac0-e175-4a37-8bf8-f1cb0f377277.png)
- `by`키워드를 이용한 코틀린 파일을 디컴파일 하면 위임 패턴이 자동으로 작성된 자바 코드를 확인할 수 있다.