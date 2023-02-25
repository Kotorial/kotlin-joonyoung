
## Recipe 11.5 바이너리 문자열로 변환하고 되돌리기

> 🛫 **숫자를 바이너리 문자열로 변환하거나 바이너리 문자열을 정수로 파싱하고 싶다.**
>
> **`radix`를 인자로 받는  `toString` 또는 `toInt` 함수 오버로딩을 사용하자**

- `Stringskt` 클래스에는 `Int`에 `radix`를 파라미터로 받는 인라인 확장 함수 `toString`이 존재
- `radix`를 파라미터로 받아 `String` → `Int`로 변환하는 확장함수도 `String`에 존재

    ```kotlin
    // Int -> String타입으로 변환해 Binary를 표현
    val str = 42.toString(radix = 2)
    println(str)  //101010
    
    // String -> Int 타입으로 변환해 10진수로 변경
    val num = "101010".toInt(radix = 2)
    println(num)  // 42
    ```

- `toString(radix: Int)`의 경우 `toString` 이후 앞에 나오는 0을 잘라버리므로 `padStart` 함수를 이용해 후처리할 수 있다.

    ```java
    4.toString(raidx = 2).padStart(4,'0') // 0100
    ```


## Recipe 11.6 실행 가능한 클래스 만들기

> 🛫 **클래스에서 단일 함수를 간단하게 호출하고 싶다.**
>
> **함수를 호출할 클래스에서 `invoke` 연산자 함수를 오버로딩 한다.**


- 코틀린은 연산자 오버로딩 지원
    - **연산자 구현을 위해 멤버 함수 또는 올바른 이름과 인자를 가진 확장 함수 제공이 필요**
    - 연산자 오버로딩 위해 `operator` 변경자를 포함해야한다.
- `invoke` 함수를 통해 클래스의 인스턴스를 함수처럼 호출 가능

    ```kotlin
    class AstroRequest {
        companion object {
            private const val ASTRO_URL = "http://api.open-notify.org/astros.json"
        }
    
        // 인스턴스화된 클래스를 함수처럼 사용할 수 있음
        operator fun invoke(): String {
            return URL(ASTRO_URL).readText()
        }
    }
    
    val request = AstroRequest()
    val response = request() // invoke 함수를 overloading해서 인스턴스를 함수처럼 사용 가능
    ```


## Recipe 11.7 경과 시간 측정하기

> 🛫 **코드 블록이 실행되는 데 걸린 시간을 알고 싶다.**
>
> **코틀린 표준 라이브러리의 `measureTimeMillis` 또는 `measureNanoTime` 함수를 사용**

- `measureTimeMillis`, `measureNanoTime`을 사용하면 코드 블럭 시간을 측정하기 쉽다.

    ![image](https://user-images.githubusercontent.com/52314663/221348739-4a9f6e61-b8a0-4630-b9e2-8b0cbafd4179.png)

    - 단순하게 start 시점을 체크하고 `block`을 실행 후 return 하는 시점에서 start 시점을 빼주는 방식으로 동작
- `measureTimeNano` 또한 `measureTimeMillis`와 구현은 동일하다.

    ![image](https://user-images.githubusercontent.com/52314663/221348744-3057945e-6145-4c4f-ada7-abd412427fc2.png)

    - 차이점은 `System.nanoTime`을 호출해서 시간을 측정한다.
- `measureNanoTime`, `measureTimeMillis`  모두 람다를 인자로 받는 고차함수이다.
    - 효율성을위해 `inline` 키워드를 이용해 인라인 함수로 만들어져 있다.

## Recipe 11.8 스레드 시작하기

> 🛫 **코드 블록을 동시적 스레드에서 실행하고 싶다.**
> 
> **`kotlin.concurent` 패키지의 `thread` 함수를 사용한다.**

- `thread` 함수를 이용해서 스레드를 사용할 수 있다.

    ![image](https://user-images.githubusercontent.com/52314663/221348763-d9488886-c778-4143-8772-3e059ea1433f.png)

- `thread` 함수는 default parameter로 설정되어 있고, 실행할 코드 블럭만 넘겨주면 된다.

    ```kotlin
    // 스레드 함수에 코드 블럭을 넘겨서 스레드 사용
    thread {
        Thread.sleep(sleepTime)
        println("${Thread.currentThread().name} for $n after ${sleepTime}ms")
    }
    
    // 스레드의 디폴트 파라미터를 정의한 후 스레드 사용
    thread (isDaemon = true) {
        Thread.sleep(sleepTime)
        println("${Thread.currentThread().name} for $n after ${sleepTime}ms")
    }
    ```

- `thread` 확장 함수는 `Thread` 타입의 객체를 생성하고 `run` 함수를 오버라이딩해 `block` 실행

    ![image](https://user-images.githubusercontent.com/52314663/221348766-b13c8cbc-dfe1-489d-a638-4999b88f92e6.png)

- `thread` 확장 함수는 파라미터들을 설정하고, block을 실행 시킨 뒤 실행된 스레드를 반환한다.
    - 반환된 스레드를 이용해 실행중인 스레드에 대해 추가적인 함수를 실행시키는 것이 가능하다.