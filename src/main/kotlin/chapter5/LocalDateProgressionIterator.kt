package chapter5

import java.time.LocalDate

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