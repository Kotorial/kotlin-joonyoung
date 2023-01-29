package chapter5

import java.time.LocalDate
import javax.print.attribute.standard.MediaSize.Other

class LocalDateProgression (
    // ClosedRange 프로퍼티 재정의 (start, endInclusive)
    override val start: LocalDate,
    override val endInclusive: LocalDate,
    // (Option) step
    val step: Long = 1
) : Iterable<LocalDate>, ClosedRange<LocalDate> {
    // Iterable 인터페이스의 오버라이딩 메서드는 iterator 한개
    override fun iterator(): Iterator<LocalDate> = LocalDateProgressionIterator(start, endInclusive, step)

    // step 중위 함수 만들기
    infix fun step(days: Long) = LocalDateProgression(start, endInclusive, days)

}

// 확장 함수 생성
operator fun LocalDate.rangeTo(other: LocalDate) = LocalDateProgression(this, other)
