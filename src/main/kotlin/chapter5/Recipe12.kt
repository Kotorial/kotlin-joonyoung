package chapter5

import java.time.LocalDate

fun main() {
    val startDate = LocalDate.now()
    val midDate = startDate.plusDays(3)
    val endDate = startDate.plusDays(5)

    val dateRange = LocalDate.now()..LocalDate.now().plusDays(5)
    for (date in dateRange) {
        println(date)
    }
}