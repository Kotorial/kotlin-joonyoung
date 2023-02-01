package chapter7

fun main() {
    // null이 들어가지 않는 경우
    println("processString : ${processString("zayson")}")
    println("processNullable : ${processNullableString("zayson")}")

    // null을 넣어주는 경우
//    println("if processString's input is null, it make compile error! ${processString(null)}")
    println("processNullable's input is null -> return ${processNullableString(null)}")
}

fun processString(string: String) = string.let {
    when {
        it.isEmpty() -> "Empty"
        it.isBlank() -> "Blank"
        else -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

fun processNullableString(string: String?) =
    string?.let {   // 파라미터 input인 string이 null이 아닌 경우 실행 (safe-call)
        when {
            it.isEmpty() -> "Empty"
            it.isBlank() -> "Blank"
            else -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } // 첫글자 대문자 만들기
        }
    } ?: "Default String" // 파라미터 input인 string이 null인 경우 Default return 값 사용
