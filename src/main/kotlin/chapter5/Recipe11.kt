package chapter5

import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

fun main() {
    val list = listOf("zayson", 10, LocalDate.now())
    val strings = list.filter { it is String }  // filter를 이용해 Any -> String 으로 필터링 햇으나 타입은 그대로 List<Any>

    val list2 = listOf("zayson", 10, LocalDate.now())
    val strings2 = list.filterIsInstance<String>() // String 타입으로 필터링한 리스트

    val listAdler32 = listOf("zayson", 10, LocalDate.now())
    val strings3 = list.filterIsInstanceTo(mutableListOf<String>())


    checkType("message", String::class)
    checkType("message")
}

inline fun <T : Any> checkType(data: T, type: KClass<T>) {
//    when(T::class) // 컴파일 에러
    when(type) {
        String::class -> println("${data} is string")
        else -> println("${data} is not string")
    }
}

inline fun <reified T> checkType(data: T) {
    when (T::class) {
        String::class -> println("${data} is string")
        else -> println("${data} is not string")
    }
}