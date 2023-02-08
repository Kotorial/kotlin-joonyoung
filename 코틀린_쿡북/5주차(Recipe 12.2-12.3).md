## Recipe 12.2 코틀린 data 클래스로 퍼시스턴스 구현하기

> ❓ **코틀린 data 클래스로 JPA를  사용하고 싶다.**
> 
> **kotlin-jpa 플러그인을 빌드 파일에 추가한다.**
>
- **코틀린의 data class**

    ```kotlin
    // 1. val 속성을 이용해서 불변 객체로 생성
    // 2. 기본 생성자가 없음
    data class Person(val name: String, val dob: LocalDate)
    ```

- **JPA의 Entity**
    - **엔티티 내 필드에 기본값을 지원하지 않는 경우 기본 생성자가 필수적이다.**
    - **엔티티의 필드는 `private final`로 선언하지 못한다.**

    ```java
    @Entity
    @NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 필수
    public class Person {
        @Id
        @GeneratedValue
        private Long id;  // 불변 필드 불가
    
        @Column(name = "name")
        private String name;
        
        @Column(name = "dob")
        private LocalDate dob;
    }
    ```


### JPA와 data class 차이로 인한 문제 해결법

1. **기본 생성자**
    - `no-arg` 컴파일러 플러그인, `kotlin-jpa` 플러그인 사용 시 해결 가능
    - 일반적으로 `kotlin-jpa` 플러그인 사용
    - `@Entity`, `@Embeddable`, `@MappedSuperclass` 애노테이션에 자동으로 기본생성자 추가
2. **불변 클래스**
    - **엔티티로 사용하고자 하는 클래스를 일반 class로 생성**
    - **각 프로퍼티는 `var` 타입을 사용**

    ```kotlin
    @Entity
    class Foo {
        var name: String,
        var dob: LocalDate,
        @MayToOne var Bar, // 연관관계 매핑 가능
        @Id @GeneratedValue var id: Long? = null // 기본값 설정 가능
    }
    ```

- 문제의 해결이 가능하지만 `var`사용과 `toString`, `equals`, `hashCode` 함수의 부재로 인해 불편함이 생길수는 있다.
    > data class를 이용해 `toString`, `equals` 를 이용하는 경우 순환참조 발생 가능

- Spring Data MongoDB, JDBC 같은 스프링 데이터 기반 API는 data class를 사용 가능

---
## Recipe 12.3 의존성 주입하기
> **❓ Autowiring이 필요한 빈과 필요하지 않은 빈을 선언하고 DI를 하고 싶다.**
>
> **기본적으로 생성자 주입을 제공, 필드 주입의 경우 `lateinit var` 구조 사용**
> 
> **선택적인 빈은 널 허용 타입으로 선언**

- 코프링도 `@Autowired`를 이용해서 의존성 주입
- **코프링의 의존성 주입 방법**

    ```kotlin
    // 1. 단일 생성자인 클래스의 경우
    class GreetingController(val service: GreetingService) { ... }
    
    // 2. @Autowired 명시
    class GreetingController(@Autowired val service: GreetingService) { ... }
    
    // 3. 오토와이어링 생성자 호출, 주로 다수의 의존성을 갖는 클래스의 경우
    class GreetingController @Autowired contstructor (val service: GreetingService) { ... }
    
    // 4. 필드 주입 (지양)
    class GreetingController {
        @Autowired
        lateinit var service: GreetingService
        
       // Api 로직...
    }
    ```

- **필드 주입의 경우 `var`를 이용하므로 추후에 주입된 값이 변경될 가능성이 존재 → 생성자 주입 사용**
- **클래스의 속성이 필수가 아닌 경우 해당 속성을 널 허용 타입으로 선언 가능**