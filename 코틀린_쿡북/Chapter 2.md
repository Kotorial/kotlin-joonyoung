### Recipe 2.1 코틀린에서 널 허용 타입 사용하기

> 💡 변수가 절대 null값을 갖지 못하게 하고 싶다.
>
> 물음표를 사용하지 않고 변수 타입 정의
> 
> `null` 허용 타입은 `safe-call(?.)` 이나 엘비스 연산자(`?:`)와 함께 사용

- `null`값을 허용하지 않는 타입과 허용하는 타입 선언 방식
    - `null` 허용 타입 : 타입에 뒤에 물음표를 붙힌다.

    ```kotlin
    var notNullName: String
    notNullName = "zayson"
    //    notNullName = null  // 컴파일 에러
    
    var nullableName: String?  // 널 허용 타입
    nullableName = "zayson"
    nullableName = null // 컴파일 에러 안남
    ```

- `val` 로 선언된 변수 : `final`의 성격을 갖기 때문에 변경 불가
    - `val`로 선언된 변수의 `null` 허용 → `null` 허용 타입임에도 `null`에 대한 체크가
      이뤄지면 중간에서 값 변경이 없을 것이라 판단
      ⇒ **스마트 캐스트** (ex. `String?` → `String` 변환)

    ```kotlin
    class Person(val first: String, val middle: String?, val last: String)
    val person1 = Person("joonyoung", null, "maeng")
    
    if (person1.middle != null) {
        // middle이 null아님을 체크 + val 변수 = 중간에 값 변경 X 예상 = 스마트 캐스트
        val middleLength = person1.middle.length  // String? -> String 타입으로 스마트 캐스트
    }
    ```

- `var` 로 선언된 변수 : 값의 변경 가능
    - `var` 로 선언된 변수의 null 허용 → null에 대한 체크가 이뤄져도 중간에서 값 변경 가능
      → **스마트 캐스트 발생 X, 개발자가 해당 변수가 null 아님을 단언 가능**
    - **널 아님 단언 (`!!`)**

    ```kotlin
    var person2 = Person("joonyoung", null, "maeng")
    
    if (person2.middle != null) {
        // person2의 경우 var이기 때문에 중간에 값이 변경될 가능성 존재 = 스마트 캐스트 발생 X
        val middleLength = person2.middle!!.length  // 개발자가 null이 들어오지 않음을 확신 -> !!을 이용한 null 아님 단언
    }
    ```

  >  💡 널 아님 단언
  >
  > 널 아닌 값이라고 개발자가 판단 = **코드 스멜(잠재적 버그 가능성)**
  > 
  > `NPE` 발생 가능한 코드 = **널 아님이 확실할 때만 사용, null 허용하지 않는 타입으로 변환, 메서드 호출 시 safe call 사용**
  

- **safe-call** (`?.`) : **메서드를 호출한 결과가 값이 null이면 null을 리턴**

    ```kotlin
    // safe-call을 통한 null 허용 타입에 대한 안전한 호출
    val middleLength = person2.middle?.length
    ```

- **엘비스 연산자 (`?:`) : 연산 결과 값이 null인 경우 엘비스 연산자 뒤의 식을 사용**
    - **엘비스 연산자의 오른쪽 부분은 “식 (Expression)”** 이다.

    ```kotlin
    // elvis 연산자 - 값이 null인 경우 후자의 식을 사용
    val middleLength1 = person2.middle?.length ?: 0  // 0을 반환
    val middleLength2 = person2.middle?.length ?: throw IllegalArgumentException("엘비스 연산자는 식 = throw 가능")
    ```

- **안전한 타입 변환 연산자 (`as?`)**
    - 캐스팅 하는 경우 캐스팅 대상이 `null`인 경우 `ClassCastException` 방지하기 위함
    - 올바른 경우 → 캐스팅, 올바르지 않은 경우 → `null` 리턴

    ```kotlin
    // 안전한 타입 캐스팅 (safe cast)
    val person3 = person1 as? Person
    ```

---
### Recipe 2.2 자바에 널 허용성 지시자 추가하기

> 💡 코틀린 코드, 자바 코드 사이 상호 작용 필요 + `@Nullable` 강제하고자 하는 경우
> 
> 컴파일 타임 파라미터 `Xjsr305=strict` 를 사용

- 코틀린은 컴파일타임에 `타입?`를 이용한 널 허용 타입 지원
- 코틀린은 JSR-305 호환 애노테이션을 사용해 자바 코드와의 호환성 강제 가능

![image](https://user-images.githubusercontent.com/52314663/211238332-27f59fed-fcf0-4c23-ab93-060948cc06f2.png)


- JSR-305의 `@Nonnull`은 when 속성을 가짐
  - `When.ALWAYS` : 널 비허용 타입
  - `When.MAYBE`, `When.NEVER` : 널 허용 타입
  - `When.UNKNOWN` : 널 허용을 알 수 없는 **플랫폼 타입**